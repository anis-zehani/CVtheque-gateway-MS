package com.odix.fr.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odix.fr.model.Collaborateur;
import com.odix.fr.service.CollaborateurService;

@CrossOrigin
@RestController
@RequestMapping("/api/collaborateur")
public class CollaborateurController {
	
	@Autowired
	private final CollaborateurService collaborateurService;
	
	CollaborateurController(CollaborateurService collaborateurService) {
		this.collaborateurService = collaborateurService;
	}

	@GetMapping()
	public List<Collaborateur> getAllCollaborateurs() {
	    return collaborateurService.getAllCollaborateurs();
	}
	
	@GetMapping("{id}")
	public Collaborateur getCollaborateur(@PathVariable UUID id) {
		return collaborateurService.getCollaborateur(id);
	}
	
	//Ajouter un Collaborateur pour un utilisateur : (idUtilisateur existe dans l'objet Utilisateur envoyé à l'intérieur de l'objet Collaborateur)
	@PostMapping()
	public Collaborateur addCollaborateur(@Valid @RequestBody Collaborateur collaborateur) {
		return collaborateurService.addCollaborateur(collaborateur);
	}
	
	//Modifier un Collaborateur pour un utilisateur : (idUtilisateur existe dans l'objet Utilisateur envoyé à l'intérieur de l'objet Collaborateur)
	@PutMapping()
	public Collaborateur editCollaborateur(@Valid @RequestBody Collaborateur collaborateur) {
		return collaborateurService.editCollaborateur(collaborateur);
	}
	
	@DeleteMapping("{id}")
	public void deleteCollaborateur(@PathVariable UUID id) {
		collaborateurService.deleteCollaborateur(id);
	}

}
