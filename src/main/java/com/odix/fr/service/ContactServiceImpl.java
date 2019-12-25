package com.odix.fr.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.odix.fr.model.Contact;
import com.odix.fr.model.Utilisateur;
import com.odix.fr.repository.ContactRepository;
import com.odix.fr.util.LocalStorageService;

@Service
public class ContactServiceImpl implements ContactService{
	
	private final ContactRepository contactRepository;
	private final LocalStorageService storageService;
	private final UtilisateurService utilisateurService;
	
	ContactServiceImpl(ContactRepository contactRepository, LocalStorageService storageService, UtilisateurService utilisateurService) {
		super();
		this.contactRepository = contactRepository;
		this.storageService = storageService;
		this.utilisateurService = utilisateurService;
	}

	public List<Contact> getAllContacts(UUID idUtilisateur) {
		
		Utilisateur utilisateur = utilisateurService.getUtilisateurById(idUtilisateur);
		
		return contactRepository.findAllByUtilisateur(utilisateur);
	}

	public Optional<Contact> getContact(UUID id) {
		return contactRepository.findById(id);
	}
	
	public Long getCountContacts() {
		return contactRepository.count();
	}

	//Ajouter un contact
	public Contact addContact(Contact contact) {
		
		if(contactRepository.findByIdentite(contact.getIdentite()) == null &&
		   contactRepository.findByEmail(contact.getEmail()) == null) {
			
			if(contact.getEntreprise().getIdEntreprise() == null) {
				//Obligatoire pour @ManyToOne
				contact.setEntreprise(null);
			}
			//On met l'image par défaut à tout le monde : elle pourra être écrasée plus tard
			contact.setUrlPhoto("");
				
			return contactRepository.save(contact);
		}
		return null;
	}
	
	//Affecter une photo à un contact (fonction appelée dans Ajout + Update)
	public Contact addPhotoToContact(UUID id, String urlPhoto) {
		
		if(contactRepository.existsById(id)) {
			Contact contact = contactRepository.getOne(id);
			
			//delete ancienne photo : si elle existe dans le cas d'un Update
			if(contact.getUrlPhoto() != null)
			{
				storageService.deletePhoto(contact.getUrlPhoto());
			}
		
			//update URL photo avec nouveau nom
			contact.setUrlPhoto(urlPhoto);

			return contactRepository.save(contact);
		}
		
		return null;
	}

	//Modifier un contact
	public Contact editContact(Contact contact) {
		
		//L'Update url photo se fait en haut dans la fonction addPhotoToContact
		if(contactRepository.existsById(contact.getId()) && 
		   contact.getIdentite() != "" && 
		   contact.getEmail() != "") {
			
			if(contact.getEntreprise().getIdEntreprise() == null) {
				contact.setEntreprise(null);
			}
			return contactRepository.save(contact);
		}
		return null;
	}

	//Supprimer un contact
	public void deleteContact(UUID id) {
		
		if(contactRepository.existsById(id))
		{
			Contact contact = contactRepository.getOne(id);

			try
			{
				//On supprime d'abord la photo si ce n'est pas un avatar
				if(contact.getUrlPhoto() != null)
				{
					storageService.deletePhoto(contact.getUrlPhoto());
				}
			}
			catch(NoSuchElementException e) 
			{
				System.out.print("Erreur durant deleteCandidat :"+e);
			}
			
			//On supprime la ligne de la base
			contactRepository.deleteById(id);
		}
	}

}
