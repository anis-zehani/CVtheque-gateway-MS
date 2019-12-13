package com.odix.fr.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.odix.fr.model.CandidatsFavoris;

@Repository
public interface CandidatsFavorisRepository  extends JpaRepository<CandidatsFavoris, UUID> {
	
	List<CandidatsFavoris> findByIdUtilisateur(@Param("idUtilisateur") UUID idUtilisateur);
	
	// Vérifie si un Candidat existe dèja dans la liste des favoris d'un Utilisateur
	CandidatsFavoris findByIdUtilisateurAndIdCandidat(
			@Param("idUtilisateur") UUID idUtilisateur, 
			@Param("idCandidat") UUID idCandidat);
}
