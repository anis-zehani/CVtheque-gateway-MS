package com.odix.fr.repository;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.odix.fr.model.CandidatTemporaire;

@Repository
public interface CandidatTemporaireRepository extends JpaRepository<CandidatTemporaire, UUID> {

	// Retourne le CandidatTemporaire par email : dernière tentative si le mail existe plus qu'une fois
	@Query(value = "SELECT * FROM Candidat_temporaire ct WHERE ct.email LIKE CONCAT('%',?1,'%') ORDER BY ct.date_ajout DESC LIMIT 1", nativeQuery = true)
	CandidatTemporaire getLastAttemptedCandidatTemporaireByDate(@Param("email") String email);
	
	// Supprime tous les candidats Temporaires ayant l'adresse email venant d'être activée
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Candidat_temporaire ct WHERE ct.email LIKE CONCAT('%',?1,'%')", nativeQuery = true)
	void deleteAllCandidatsTemporairesByEmailAdresse(@Param("email") String email);
}
