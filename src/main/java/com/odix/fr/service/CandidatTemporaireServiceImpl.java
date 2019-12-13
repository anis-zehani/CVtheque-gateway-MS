package com.odix.fr.service;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.odix.fr.model.Candidat;
import com.odix.fr.model.CandidatTemporaire;
import com.odix.fr.model.Diplome;
import com.odix.fr.model.Disponibilite;
import com.odix.fr.model.Ecole;
import com.odix.fr.model.Entreprise;
import com.odix.fr.model.Etat;
import com.odix.fr.model.Note;
import com.odix.fr.model.SituationFamiliale;
import com.odix.fr.model.TypeDiplome;
import com.odix.fr.model.TypeVisa;
import com.odix.fr.model.Visa;
import com.odix.fr.repository.CandidatTemporaireRepository;
import com.odix.fr.util.Consts;
import com.odix.fr.util.JavaMailSenderService;

@Service
public class CandidatTemporaireServiceImpl implements CandidatTemporaireService {
	
	@Autowired
	CandidatTemporaireRepository candidatTemporaireRepository;

	@Autowired
	JavaMailSenderService mailService;
	
	@Autowired
	CandidatService candidatService;
	
	
	private static final String urlPlatformeActivateCandidat = Consts.urlPlatformeActivateCandidat;
	
	private static final String urlPlatformeLoginPage = Consts.urlPlatformeLoginPage;
	
	@Override
	public CandidatTemporaire addCandidatTemporaire(CandidatTemporaire candidatTemporaire) {
		
		candidatTemporaire.setDateAjout(LocalDateTime.now());
		
		return candidatTemporaireRepository.save(candidatTemporaire);
	}

	@Override
	public Boolean envoiEmailActivationCompteCandidat(String email) {
		
			// Cherche un Candidat Temporaire par Email et retourne le tout nouveau dans le cas de plusieurs tentatives via le même email
			CandidatTemporaire lastAttemptedCandidat = candidatTemporaireRepository.getLastAttemptedCandidatTemporaireByDate(email);
			
				if(lastAttemptedCandidat != null) {
					// Envoi du mail avec lien d'activation du compte
					String contenu = 
							"Bonjour,"
							+ "<br><br>"
							+ "Afin de finaliser la création de votre compte sur notre Plateforme Odix, merci de suivre le lien ci-dessous :"
							+ "<br><br>"
							+ "<a href=\"" + urlPlatformeActivateCandidat + "?email=" + email + "\" target=\"_blank\">" + urlPlatformeActivateCandidat +"</a>"
							+ "<br><br>"
							+ "Cordialement - Odix";
					try {
						mailService.sendSimpleHtmlMessage(email, "Odix : activation de votre compte", contenu);
						return true;
					} catch (MessagingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
			return false;
	}

	/*
	 * -Chercher le candidat dans la talble Candidat Temporaire
	   -Insérer le nouveau candidat dans la table Candidat
	   -Supprimer tous les candidats via email de la table Candidat Temporaire
	   -Envoyer un email de confirmation : Authentification incluse par défaut
	 */
	@Override
	public Boolean activationCompteCandidatTemporaire(String email) {
		
		// Cherche un Candidat Temporaire par Email et retourne le tout nouveau dans le cas de plusieurs tentatives via le même email
		CandidatTemporaire lastAttemptedCandidat = candidatTemporaireRepository.getLastAttemptedCandidatTemporaireByDate(email);
		
		// Créer et Sauvegarder un nouveau ojbect Candidat
		Candidat candidat = new Candidat();
		candidat.setIdentite(lastAttemptedCandidat.getIdentite());
		candidat.setUsername(lastAttemptedCandidat.getUsername());
		candidat.setPassword(lastAttemptedCandidat.getPassword());
		candidat.setEmail(lastAttemptedCandidat.getEmail());
		
		Diplome diplome = new Diplome();
		diplome.setEcole(new Ecole());
		diplome.setTypeDiplome(TypeDiplome.Non_Mentionee);
		
		Visa visa = new Visa();
		visa.setTypeVisa(TypeVisa.Non_Mentionee);
		candidat.setDiplome(diplome);
		candidat.setVisa(visa);
		candidat.setEntreprise(new Entreprise());

		candidat.setDisponibilite(Disponibilite.Non_Mentionee);
		candidat.setEtatCandidat(Etat.True);
		candidat.setNiveauEnAnglais(Note.Non_Mentionee);
		candidat.setNiveauEnFrancais(Note.Non_Mentionee);
		candidat.setNoteGlobale(Note.Non_Mentionee);
		candidat.setSituationFamiliale(SituationFamiliale.Non_Mentionee);
		
		candidatService.addCandidat(candidat);
		
		// Supprimer tous les candidats Temporaires ayant l'adresse email venant d'être activée
		candidatTemporaireRepository.deleteAllCandidatsTemporairesByEmailAdresse(email);
		
		// Envoi du mail de confirmation d'activation avec lien de connection (pas besoin de s'authentifier de nouveau)
		String contenu = 
				"Bonjour,"
				+ "<br><br>"
				+ "Votre compte est activé avec succès. Vos paramètres d'accès sont les suivants : "
				+ "<br><br>"
				+ "-Nom d'utilisateur : <b>" + lastAttemptedCandidat.getUsername() + "</b>"
				+ "<br>"
				+ "-Mot de passe : <b>" + lastAttemptedCandidat.getPassword() + "</b>"
				+ "<br><br>"
				+ "Pour accéder à la Plateforme, veuillez suivre ce lien : "
				+ "<a href=\""+ urlPlatformeLoginPage + "\" target=\"_blank\">" + urlPlatformeLoginPage + "</a>"
				+ "<br><br>"
				+ "Cordialement - Odix";
		try {
			mailService.sendSimpleHtmlMessage(email, "Odix : votre compte a été activé", contenu);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
