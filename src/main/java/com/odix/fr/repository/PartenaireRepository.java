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
import com.odix.fr.model.Etat;
import com.odix.fr.model.Partenaire;

@Repository
public interface PartenaireRepository extends JpaRepository<Partenaire, UUID> {
	
	Partenaire findByIdentite(@Param("identite") String identite);
	
	Partenaire findByUsername(@Param("username") String username);
	
	Partenaire findByEmail(@Param("email") String email);
	
	List<Partenaire> findByEtatPartenaire(@Param("etatPartenaire") Etat etatPartenaire);
	
	List<Partenaire> findAllByEntreprise(@Param("entreprise") Entreprise entreprise);
	
	//UPDATE le lien entre un partenaire et une entreprise : met entreprise à NULL
	@Modifying
	@Transactional
	@Query("UPDATE Partenaire p SET p.entreprise = null WHERE p.id = :idPartenaire")
	void updateLinkPartenaireEntreprise(@Param("idPartenaire") UUID idPartenaire);

	
}
