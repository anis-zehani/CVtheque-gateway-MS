package com.odix.fr.repository;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.odix.fr.model.PartenaireTemporaire;

public interface PartenaireTemporaireRepository extends JpaRepository<PartenaireTemporaire, UUID> {
	
	// Retourne le DISTINCT des emails présents dans la table PartenaireTemporaire
	@Query(value = "SELECT DISTINCT email FROM Partenaire_temporaire", nativeQuery = true)
	List<String> getListEmailsDistinct(); 

	// Retourne le PartenaireTemporaire par email : dernière tentative si le mail existe plus qu'une fois
	@Query(value = "SELECT * FROM Partenaire_temporaire pt WHERE pt.email LIKE CONCAT('%',?1,'%') ORDER BY pt.date_ajout DESC LIMIT 1", nativeQuery = true)
	PartenaireTemporaire getLastAttemptedPartenaireTemporaireByDateAndEmail(@Param("email") String email); 
	
	// Supprime tous les partenaires Temporaires ayant l'adresse email venant d'être activée
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Partenaire_temporaire pt WHERE pt.email LIKE CONCAT('%',?1,'%')", nativeQuery = true)
	void deleteAllPartenairesTemporairesByEmailAdresse(@Param("email") String email);
}
