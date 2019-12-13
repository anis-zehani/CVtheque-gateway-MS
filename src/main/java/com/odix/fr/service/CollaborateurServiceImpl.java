package com.odix.fr.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.odix.fr.model.Collaborateur;
import com.odix.fr.repository.CollaborateurRepository;

@Service
public class CollaborateurServiceImpl implements CollaborateurService{
	
	private final CollaborateurRepository collaborateurRepository;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	CollaborateurServiceImpl(CollaborateurRepository collaborateurRepository) {
		super();
		this.collaborateurRepository = collaborateurRepository;
	}

	public List<Collaborateur> getAllCollaborateurs() {
		return collaborateurRepository.findAll();
	}

	public Optional<Collaborateur> getCollaborateur(UUID id) {
		return collaborateurRepository.findById(id);
	}

	//Ajouter un collaborateur
	public Collaborateur addCollaborateur(Collaborateur collaborateur) {
		//Si l'identité et le username n'existent pas
		if(collaborateurRepository.findByIdentite(collaborateur.getIdentite()) == null && 
				collaborateurRepository.findByUsername(collaborateur.getUsername()) == null){
			if(collaborateur.getPassword() != null)
			{
				//Encoder le Password avant de l'insérer dans la base
				collaborateur.setPassword(bcryptEncoder.encode(collaborateur.getPassword()));
			}

			return collaborateurRepository.save(collaborateur);
		}
		return null;
	}

	//Modifier un collaborateur
	public Collaborateur editCollaborateur(Collaborateur collaborateur) {
		
		if(collaborateurRepository.existsById(collaborateur.getId()) &&
				collaborateurRepository.findByIdentite(collaborateur.getIdentite()) == null && 
				collaborateurRepository.findByUsername(collaborateur.getUsername()) == null)
		{
			//Récupérer le password affiché sur le formulaire
			String passwordFormulaire = collaborateur.getPassword();
			//Récupérer le password actuel dans la BDD
			String passwordBDD = collaborateurRepository.findByUsername(collaborateur.getUsername()).getPassword();
			
			// Si le Password récupéré est différent de celui qui est stocké : on change le password
			if(!passwordFormulaire.equals(passwordBDD))
			{
				collaborateur.setPassword(bcryptEncoder.encode(collaborateur.getPassword()));
			}
			// Sinon on réinsére l'ancien password
			else
			{
				collaborateur.setPassword(passwordBDD);
			}
			
			return collaborateurRepository.save(collaborateur);
		}
		return null;
	}

	//Supprimer un collaborateur
	public void deleteCollaborateur(UUID id) {
		
		if(collaborateurRepository.existsById(id))
		{
			collaborateurRepository.deleteById(id);
		}
	}

}
