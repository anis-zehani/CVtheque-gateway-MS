package com.odix.fr.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odix.fr.model.PartenaireTemporaire;
import com.odix.fr.service.PartenaireTemporaireService;

@CrossOrigin
@RestController
@RequestMapping("/api/gateway/partenaire-temporaire-controller")
public class PartenaireTemporaireController {
	
	@Autowired
	private final PartenaireTemporaireService partenaireTemporaireService;
	
	public PartenaireTemporaireController(PartenaireTemporaireService partenaireTemporaireService) {
		super();
		this.partenaireTemporaireService = partenaireTemporaireService;
	}
	
	@GetMapping()
	public List<PartenaireTemporaire> getAllPartenairesTemporaires() {
	    return partenaireTemporaireService.getAllPartenairesTemporaires();
	}

	//Ajouter un Partenaire Temporaire
	@PostMapping()
	public PartenaireTemporaire addPartenaireTemporaire(@Valid @RequestBody PartenaireTemporaire partenaireTemporaire) {
		return partenaireTemporaireService.addPartenaireTemporaire(partenaireTemporaire);
	}
	
	@PutMapping("{email}/{idEntreprise}")
	public Boolean activatePartenaireTemporaire(@Valid @PathVariable String email, @Valid @PathVariable UUID idEntreprise) {
		return partenaireTemporaireService.activatePartenaireTemporaire(email, idEntreprise);
	}
}
