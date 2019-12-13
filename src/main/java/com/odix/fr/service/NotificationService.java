package com.odix.fr.service;

import java.util.List;
import java.util.UUID;

import com.odix.fr.model.Candidat;
import com.odix.fr.model.Notification;
import com.odix.fr.model.Opportunite;
import com.odix.fr.model.PartenaireTemporaire;
import com.odix.fr.model.Utilisateur;

public interface NotificationService {
	
	// Lister les Notifications Actives = True et par ID consommateur de Notif
	public List<Notification> getAllNotificationsByUtilisateur(UUID idDestinataire, String etatNotification); 
	
	// Désactive la Notification liée à l'activation d'un Partenaire Temporaire précis
	public void deactivateNotificationsByPartenaireTemporaire(PartenaireTemporaire partenaireTemporaire); 
	
	// Desactiver une Notification
	public Boolean deactivateNotification(UUID idNotification);
	
	// Générer une Notification Simple
	public void generateSimpleNotification(String objetNotification, 
									 	   String corpsNotification, 
									       List<Utilisateur> listeDestinatairesNotification, 
									       Utilisateur generateurNotification,
									       Candidat candidatNotification,
									       PartenaireTemporaire partenaireNotification,
									 	   Opportunite opportuniteNotification);

}
