package com.odix.fr.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odix.fr.model.Candidat;
import com.odix.fr.model.Etat;
import com.odix.fr.model.Notification;
import com.odix.fr.model.Opportunite;
import com.odix.fr.model.PartenaireTemporaire;
import com.odix.fr.model.Utilisateur;
import com.odix.fr.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	NotificationRepository notificationRepository;
	
	
	// Lister les Notifications Actives = True et par ID consommateur de Notif
	@Override
	public List<Notification> getAllNotificationsByUtilisateur(UUID idDestinataire, String etatNotification) {
		
		List<Notification> listeNotifications = new ArrayList<Notification>();
		
		//On filtre selon l'état : Actif / Inactif
		if(etatNotification.equals("True")){
			listeNotifications = notificationRepository.findByIdDestinataireAndEtatNotification(idDestinataire, Etat.True);
		}else {
			listeNotifications = notificationRepository.findByIdDestinataireAndEtatNotification(idDestinataire, Etat.False);
		}
		return listeNotifications;
	}

	// Retourne la Notification liée à l'activation d'un Partenaire Temporaire précis
	@Override
	public void deactivateNotificationsByPartenaireTemporaire(PartenaireTemporaire partenaireTemporaire) {
		Notification notification = notificationRepository.findByPartenaireTemporaireNotification(partenaireTemporaire);
		notificationRepository.deactivateNotification(notification.getId());
	}


	// Desactiver une Notification
	@Override
	public Boolean deactivateNotification(UUID idNotification) {
		
		try {
			notificationRepository.deactivateNotification(idNotification);
			return true;
		}catch(Exception e) {
			return false;
		}
	}


	// Simple Notification : sans envoi de mail : juste insertion dans la base
	@Override
	public void generateSimpleNotification(String objetNotification, 
									 	   String corpsNotification,
									 	   List<Utilisateur> listeDestinatairesNotification, 
									 	   Utilisateur generateurNotification,
									 	   Candidat candidatNotification,
									 	   PartenaireTemporaire partenaireNotification,
									 	   Opportunite opportuniteNotification) {

		Notification notification = new Notification();
		
		notification.setObjetNotification(objetNotification);
		notification.setCorpstNotification(corpsNotification);
		notification.setDateAjout(LocalDateTime.now());
		notification.setEtatNotification(Etat.True);
		
		notification.setGenerateurNotification(generateurNotification);
		notification.setListeDestinatairesNotification(listeDestinatairesNotification);
		
		notification.setCandidatNotification(candidatNotification);
		notification.setPartenaireTemporaireNotification(partenaireNotification);
		notification.setOpportuniteNotification(opportuniteNotification);
		
		notificationRepository.save(notification);
		
	}

}
