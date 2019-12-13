package com.odix.fr.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odix.fr.model.CandidatsFavoris;
import com.odix.fr.service.CandidatsFavorisService;

@CrossOrigin
@RestController
@RequestMapping("/api/candidatsfavoris")
public class CandidatsFavorisController {

	@Autowired
	CandidatsFavorisService candidatsFavorisService;
	
	// Lister les Candidats Favoris pour un Utilisateur
	@GetMapping("/getAllCandidatsFavorisForUtilisateur/{idUtilisateur}")
	public List<CandidatsFavoris> getAllCandidatsFavorisForUtilisateur(@PathVariable UUID idUtilisateur){
		
		return candidatsFavorisService.getAllCandidatsFavorisForUtilisateur(idUtilisateur);
	}
	
	// Vérifie si un Candidat existe dèja dans la liste des favoris d'un Utilisateur
	@GetMapping("/checkIfCandidatExistsDansFavorisUtilisateur/{idUtilisateur}/{idCandidat}")
	public boolean checkIfCandidatExistsDansFavorisUtilisateur(@PathVariable UUID idUtilisateur, @PathVariable UUID idCandidat) {
		
		return candidatsFavorisService.checkIfCandidatExistsDansFavorisUtilisateur(idUtilisateur, idCandidat);
	}
	
	// Ajouter un Candidat Favoris à un Utilisateur
	@PostMapping("/addCandidatToFavorisToUtilisateur")
	public CandidatsFavoris addCandidatToFavorisToUtilisateur(@RequestBody CandidatsFavoris candidatsFavoris) {
		
		return candidatsFavorisService.addCandidatToFavorisToUtilisateur(candidatsFavoris);
	}
	
	// Supprimer un Candidat Favoris pour un Utilisateur
	@DeleteMapping("/deleteCandidatFromFavorisToUtilisateur/{idCandidatFavori}")
	public void deleteCandidatFromFavorisToUtilisateur(@PathVariable UUID idCandidatFavori) {
		
		candidatsFavorisService.deleteCandidatFromFavorisToUtilisateur(idCandidatFavori);
	}
}
