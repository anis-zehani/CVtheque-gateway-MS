package com.odix.fr.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.odix.fr.model.Collaborateur;

public interface CollaborateurService {
	
	public List<Collaborateur> getAllCollaborateurs();
	
	public Optional<Collaborateur> getCollaborateur(UUID id);
	
	public Collaborateur addCollaborateur(Collaborateur collaborateur);
	
	public Collaborateur editCollaborateur(Collaborateur collaborateur);
	
	public void deleteCollaborateur(UUID id);

}
