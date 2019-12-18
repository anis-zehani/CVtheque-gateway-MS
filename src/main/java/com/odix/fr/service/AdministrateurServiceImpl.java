package com.odix.fr.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odix.fr.messaging.AdministrateurProducers;
import com.odix.fr.model.Administrateur;
import com.odix.fr.repository.AdministrateurRepository;

@Service
public class AdministrateurServiceImpl implements AdministrateurService{

	
	public final AdministrateurRepository administrateurRepository;
	
	@Autowired
    public final AdministrateurProducers administrateurProducers;
	
	public AdministrateurServiceImpl(AdministrateurRepository administrateurRepository, AdministrateurProducers administrateurProducers) {
		super();
		this.administrateurRepository = administrateurRepository;
		this.administrateurProducers = administrateurProducers;
	}

	/*
	 * On vérifie s'il y a un Administrateur dans la base
	 * Si y a pas : on ajout un Super Admin avec un mot de passe dèja Bcrypte que seul Anis Zaheni connait
	 * 
	 */
	@Transactional
	public void verifyOrAddAdmin(){
		
		Administrateur admin =  administrateurRepository.verifyAdmin("ROLE_ADMINISTRATEUR");
		
		if(admin == null)
		{
			Administrateur superAdmin = new Administrateur();
			superAdmin.setUsername("odix");
			superAdmin.setPassword("$2a$10$tWZVsDODx11zOTpm/jdVU.Aw6GV0iHy12KE58boDl6.80eodnqngS");
			superAdmin.setIdentite("Odix");
			superAdmin.setEmail("contact@odix.fr");
			superAdmin.setUrlPhoto("https://media.licdn.com/dms/image/C4D03AQGvl_z8bsx2Ew/profile-displayphoto-shrink_200_200/0?e=1577318400&v=beta&t=Fx9LN6el85eyVDPHiDIBUwPDbDiDa1zVO3bRc0obNt8");
			administrateurRepository.save(superAdmin);
			
			//Consistency avec les autres MS
			this.administrateurProducers.addAdministrateurProducer(superAdmin);

		}
		
	}
}
