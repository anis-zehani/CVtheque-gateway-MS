package com.odix.fr.repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.odix.fr.model.Candidat;
import com.odix.fr.model.Etat;

@Repository
public interface CandidatRepository extends JpaRepository<Candidat, UUID> {
	
	Candidat findByIdentite(@Param("identite") String identite);
	
	Candidat findByUsername(@Param("username") String username);
	
	Candidat findByEmail(@Param("email") String email);
	
	Candidat findByIdLinkedin(@Param("idLinkedin") String idLinkedin);

	List<Candidat> findByEtatCandidat(@Param("etatCandidat") Etat etatCandidat);
	
	//INNER JOIN : JPQL : La liste des candidats pour une Opportunité
	@Query("FROM Candidat c INNER JOIN c.listeOpportunites c1 ON c1.id = :idOpportunite")
	List<Candidat> findAllCandidatsByOpportunite(@Param("idOpportunite") UUID idOpportunite);
	
	//Native Query = true : Supprimer le lien entre un candidat et une opportunité
	@Modifying
	@Transactional
	@Query(value = 
			"DELETE FROM candidat_opportunite c WHERE "
			+ "c.id_candidat = ?1 AND c.id_opportunite =?2"
			, nativeQuery = true)
	void deleteLinkCandidatOpportunite(@Param("idCandidat") UUID idCandidat, @Param("idOpportunite") UUID idOpportunite);
	
	//Native Query = true : Supprimer tous les liens entre les candidats et une opportunité
	@Modifying
	@Transactional
	@Query(value = 
			"DELETE FROM candidat_opportunite c WHERE "
			+ "c.id_opportunite =?1"
			, nativeQuery = true)
	void deleteAllCandidatsByOpportunite(@Param("idOpportunite") UUID idOpportunite);
	
	//Native Query = true : Créer un lien entre un candidat et une opportunité
	@Modifying
	@Transactional
	@Query(value = 
			"INSERT INTO candidat_opportunite (id_candidat, id_opportunite) "
			+ "VALUES(?1 , ?2)"
			, nativeQuery = true)
	void addCandidatToOpportunite(@Param("idCandidat") UUID idCandidat, @Param("idOpportunite") UUID idOpportunite);
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//INNER JOIN : JPQL : La liste des candidats pour une Technologie
	@Query("FROM Candidat c INNER JOIN c.listeTechnologies c1 ON c1.id = :idTechnologie")
	List<Candidat> findAllCandidatsByTechnologie(@Param("idTechnologie") UUID idTechnologie);
	
	
	@Query(value = 
			"SELECT * FROM utilisateur u WHERE u.id  IN "
			+ " (SELECT DISTINCT id_candidat FROM candidat_technologie t1"
					+ " INNER JOIN (SELECT * FROM technologie te WHERE te.id IN ?1) t2"
					+ " ON t1.id_technologie = t2.id"
			+ " )", nativeQuery = true)
	List<Candidat> findAllCandidatsByListTechnologies(@Param("listTechnologies") Collection<UUID> listTechnologies);
	
	
	//Native Query = true : Supprimer le lien entre un candidat et une technologie
	@Modifying
	@Transactional
	@Query(value = 
			"DELETE FROM candidat_technologie c WHERE "
			+ "c.id_candidat = ?1 AND c.id_technologie =?2"
			, nativeQuery = true)
	void deleteLinkCandidatTechnologie(@Param("idCandidat") UUID idCandidat, @Param("idTechnologie") UUID idTechnologie);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//INNER JOIN : JPQL : La liste des candidats pour une Certification
	@Query("FROM Candidat c INNER JOIN c.listeCertifications c1 ON c1.id = :idCertification")
	List<Candidat> findAllCandidatsByCertification(@Param("idCertification") UUID idCertification);
	
	//Native Query = true : Supprimer le lien entre un candidat et une certification
	@Modifying
	@Transactional
	@Query(value = 
			"DELETE FROM candidat_certification c WHERE "
			+ "c.id_candidat = ?1 AND c.id_certification =?2"
			, nativeQuery = true)
	void deleteLinkCandidatCertification(@Param("idCandidat") UUID idCandidat, @Param("idCertification") UUID idCertification);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
	//La liste des candidats pour une Entreprise
	@Query("FROM Candidat c WHERE c.entreprise.idEntreprise = :idEntreprise")
	List<Candidat> findAllCandidatsByEntreprise(@Param("idEntreprise") UUID idEntreprise);
	
	//UPDATE le lien entre un candiat et une entreprise : met entreprise à NULL
	@Modifying
	@Transactional
	@Query("UPDATE Candidat c SET c.entreprise = null WHERE c.id = :idCandidat")
	void updateLinkCandidatEntreprise(@Param("idCandidat") UUID idCandidat);
}
