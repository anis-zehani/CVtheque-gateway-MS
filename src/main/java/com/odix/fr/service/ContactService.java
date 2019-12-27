package com.odix.fr.service;

import java.util.List;
import java.util.UUID;

import com.odix.fr.model.Contact;

public interface ContactService {
	
	public List<Contact> getAllContacts(UUID idUtilisateur);
	
	public Contact getContact(UUID id);
	
	public Long getCountContacts();
	
	public Contact addContact(Contact contact);
	
	public Contact editContact(Contact contact);
	
	public void deleteContact(UUID id);

	public Contact addPhotoToContact(UUID id, String urlPhoto);

}
