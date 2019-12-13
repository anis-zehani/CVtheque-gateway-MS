package com.odix.fr.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.odix.fr.model.Entreprise;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, UUID> {
	
	Entreprise findByNomEntreprise(@Param("nomEntreprise") String nomEntreprise);
	
	// UPDATE le nombre des Candidats liés et des Partenaires liés à une Entreprise
	@Modifying
	@Transactional
	@Query("UPDATE Entreprise e SET e.statNombreCandidatsLies = :nombreCandidats, e.statNombrePartenairesLies = :nombrePartenaires WHERE e.id = :idEntreprise")
	void updateNombreCandidatsAndNombrePartenairesStats(@Param("idEntreprise") UUID idEntreprise, @Param("nombreCandidats") Integer nombreCandidats, @Param("nombrePartenaires") Integer nombrePartenaires);

		
	// Retourne la liste des 5 premières Entreprise ORDER BY le nombre des Candidats qu'il y a pour elle
	@Query(value="SELECT * FROM Entreprise e"
			+ " ORDER BY e.stat_nombre_candidats_lies DESC"
			+ " LIMIT 5", nativeQuery = true)
	List<Entreprise> candidatsByEntreprise();
		
		
	// Retourne la liste des 5 premières Entreprise ORDER BY le nombre des Partenaires qu'il y a pour elle
	@Query(value="SELECT * FROM Entreprise e"
			+ " ORDER BY e.stat_nombre_partenaires_lies DESC"
			+ " LIMIT 5", nativeQuery = true)
	List<Entreprise> partenairesByEntreprise();
		
	// Retourne la somme des Candidats liés à toutes les Entreprises
	@Query(value="SELECT SUM(e.stat_nombre_candidats_lies) FROM Entreprise e", nativeQuery = true)
	List<Integer> sumCandiatsByEntreprises();
		
	// Retourne la somme des Partenaires liés à toutes les Entreprises
	@Query(value="SELECT SUM(e.stat_nombre_partenaires_lies) FROM Entreprise e", nativeQuery = true)
	List<Integer> sumPartenairesByEntreprises();

}