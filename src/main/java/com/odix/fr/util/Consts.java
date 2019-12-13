package com.odix.fr.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class Consts {
	
	        // Tou mes messages de Notification
			public static final String objetMsgNotificationCandidatAjoute = "Candidat ajouté";
			public static final String corpsMsgNotificationCandidatAjouteLinkedin = "Création d'un nouveau compte candidat via Linkedin";
			public static final String corpsMsgNotificationCandidatAjouteFormulaire = "Création d'un nouveau compte candidat via formulaire";
			
			public static final String objetMsgNotificationDemandeAdhesionPartenaire = "Partenaire en attente";
			public static final String corpsMsgNotificationDemandeAdhesionPartenaire = "Une nouvelle demande d'adhésion d'un partenaire est en attente de validation";
			
			public static final String objetMsgNotificationAjoutOpportunite = "Opportunité ajoutée";
			public static final String corpsMsgNotificationAjoutOpportunite = "Une opportunité vient d'être ajoutée par un partenaire";
			
			public static final String objetMsgNotificationModificationOpportunite = "Opportunité modifiée";
			public static final String corpsMsgNotificationModificationOpportunite = "Une opportunité vient d'être modifiée par un partenaire";
			

			// Pour les urls dans les emails envoyés
			public static final String urlPlatformeResetPassword = "http://plateforme.odix.fr/reset-password/";
			public static final String urlPlatformeActivateCandidat = "http://plateforme.odix.fr/activation-compte/";
			public static final String urlPlatformeLoginPage = "http://plateforme.odix.fr/";
			
			//Emplacement photo sur le serveur physique : container Rest
			public static final Path rootLocation = Paths.get("/uploads");
			//Emplacement photo sur le serveur physique : container Rest
			public static final Path rootLocationPhoto = Paths.get("/uploads");
			//Emplacement cvodix sur le serveur physique : container Rest
			public static final Path rootLocationCvOdix = Paths.get("/uploads");
			//Emplacement cvoriginal sur le serveur physique : container Rest
			public static final Path rootLocationCvOriginal = Paths.get("/uploads");
			//Emplacement fichier sur le serveur physique : container Rest
			public static final Path rootLocationFichierRappel = Paths.get("/uploads");

/*
			// Pour les urls dans les emails envoyés
			public static final String urlPlatformeResetPassword = "http://localhost:4200/reset-password/";
			public static final String urlPlatformeActivateCandidat = "http://localhost:4200/activation-compte/";
			public static final String urlPlatformeLoginPage = "http://localhost:4200/";
			
			//Emplacement photo sur le serveur physique : container Rest
			public static final Path rootLocation = Paths.get("/var/tmp/uploads/");
			//Emplacement photo sur le serveur physique : container Rest
			public static final Path rootLocationPhoto = Paths.get("/var/tmp/uploads/");
			//Emplacement cvodix sur le serveur physique : container Rest
			public static final Path rootLocationCvOdix = Paths.get("/var/tmp/uploads/");
			//Emplacement cvoriginal sur le serveur physique : container Rest
			public static final Path rootLocationCvOriginal = Paths.get("/var/tmp/uploads/");
			//Emplacement fichier sur le serveur physique : container Rest
			public static final Path rootLocationFichierRappel = Paths.get("/var/tmp/uploads/");	
*/
}
