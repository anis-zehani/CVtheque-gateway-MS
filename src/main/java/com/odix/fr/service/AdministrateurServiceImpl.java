package com.odix.fr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odix.fr.messaging.KafkaProducer;
import com.odix.fr.model.Administrateur;
import com.odix.fr.repository.AdministrateurRepository;

@Service
public class AdministrateurServiceImpl implements AdministrateurService{
	
	public final AdministrateurRepository administrateurRepository;
	
	@Autowired
    public final KafkaProducer kafkaProducer;
	
	public AdministrateurServiceImpl(AdministrateurRepository administrateurRepository, KafkaProducer kafkaProducer) {
		super();
		this.administrateurRepository = administrateurRepository;
		this.kafkaProducer = kafkaProducer;
	}

	/*
	 * On vérifie s'il y a un Administrateur dans la base
	 * Si y a pas : on ajout un Super Admin avec un mot de passe dèja Bcrypte que seul Anis Zaheni connait
	 * 
	 */
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
			
			/*Envoyer un MESSAGE au TOPIC KAFKA pour ajouter cet utilisateur*/
			this.kafkaProducer.produceAdministrateurAdded(
					"ROLE_ADMINISTRATEUR"
					+'#'+superAdmin.getId()
					+'#'+superAdmin.getEmail()
					+'#'+superAdmin.getIdentite()
					+'#'+superAdmin.getUrlPhoto()
					);
			

		}
		
	}
}
