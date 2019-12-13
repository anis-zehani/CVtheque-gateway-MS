package com.odix.fr.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odix.fr.model.CandidatTemporaire;
import com.odix.fr.service.CandidatTemporaireService;

@CrossOrigin
@RestController
@RequestMapping("/api/candidat-temporaire-controller")
public class CandidatTemporaireController {
	
	@Autowired
	private final CandidatTemporaireService candidatTemporaireService;
	
	public CandidatTemporaireController(CandidatTemporaireService candidatTemporaireService) {
		super();
		this.candidatTemporaireService = candidatTemporaireService;
	}

	//Ajouter un Candidat Temporaire
	@PostMapping()
	public CandidatTemporaire addCandidatTemporaire(@Valid @RequestBody CandidatTemporaire candidatTemporaire) {
		return candidatTemporaireService.addCandidatTemporaire(candidatTemporaire);
	}
	
	// Envoyer le lien d'activation du compte par email au nouveau Candidat Temporaire
	@PostMapping("/mail-activation-candidat-temporaire/{email}")
	public Boolean envoiEmailActivationCompteCandidat(@PathVariable String email) {
		return candidatTemporaireService.envoiEmailActivationCompteCandidat(email);
	}
	
	// Activation réelle du nouveau Candidat Temporaire : clic sur le lien dèja envoyé par email
	@PostMapping("/activation-compte-candidat-temporaire/{email}")
	public Boolean activationCompteCandidatTemporaire(@PathVariable String email) {
		return candidatTemporaireService.activationCompteCandidatTemporaire(email);
	}
}
