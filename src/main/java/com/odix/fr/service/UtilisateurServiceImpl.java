package com.odix.fr.service;

import java.io.IOException;
import java.util.UUID;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.odix.fr.model.Utilisateur;
import com.odix.fr.repository.UtilisateurRepository;
import com.odix.fr.util.Consts;
import com.odix.fr.util.JavaMailSenderService;

@Service
public class UtilisateurServiceImpl implements UtilisateurService{
	
	private final UtilisateurRepository utilisateurRepository;
	
	private static final String urlPlatformeResetPassword = Consts.urlPlatformeResetPassword;
	
	private static final String urlPlatformeLoginPage = Consts.urlPlatformeLoginPage;
	
	@Autowired
	JavaMailSenderService mailService;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
		super();
		this.utilisateurRepository = utilisateurRepository;
	}

	
	public Utilisateur getUtilisateurById(UUID id) {
		return utilisateurRepository.findUtilisateurById(id);
	}

	public Utilisateur getUtilisateurByUsername(String username) {
		return utilisateurRepository.findUtilisateurByUsername(username);
	}
	
	public Utilisateur getUtilisateurByEmail(String email) {
		return utilisateurRepository.findUtilisateurByEmail(email);
	}
	

	@Override
	public Utilisateur getUtilisateurByRole(String role) {
		return utilisateurRepository.findUtilisateurByDtype(role);
	}


	// Envoi du mail avec lien de réinitialisation à l'utilisateur
	public Boolean sendEmailResetPassword(String email) {
		String contenu = 
				"Bonjour,"
				+ "<br><br>"
				+ "Vous avez demandé la réinitialisation de votre mot de passe sur notre Plateforme Odix. "
				+ "Pour ce faire, merci de suivre le lien ci-dessous : <br><br>"
				+ "<a href=\"" + urlPlatformeResetPassword + "?email=" + email + "\" target=\"_blank\">" + urlPlatformeResetPassword +"</a>"
				+ "<br><br>"
				+ "Cordialement - Odix";
		try {
			mailService.sendSimpleHtmlMessage(email, "Odix : réinitialisation de votre mot de passe", contenu);
			return true;
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	// Réinitialisation du Password
	public Utilisateur resetPasswordUtilisateur(String email, String password) {
		
		Utilisateur utilisateur = utilisateurRepository.findUtilisateurByEmail(email);
		//Mise à jour du Password
		utilisateur.setPassword(bcryptEncoder.encode(password));
		
		Utilisateur utilisateurModified = utilisateurRepository.save(utilisateur);
		//Envoi du nouveau Password par email
		if (utilisateurModified != null) {
			String contenu = 
					"Bonjour,"
					+ "<br><br>"
					+ "Votre mot de passe a été mis à jour avec succès. Vos paramètres d'accès sont les suivants : "
					+ "<br><br>"
					+ "-Nom d'utilisateur : <b>" + utilisateur.getUsername() + "</b>"
					+ "<br>"
					+ "-Mot de passe : <b>" + password + "</b>"
					+ "<br><br>"
					+ "Pour accéder à la Plateforme, veuillez suivre ce lien : "
					+ "<a href=\""+ urlPlatformeLoginPage + "\" target=\"_blank\">" + urlPlatformeLoginPage + "</a>"
					+ "<br><br>"
					+ "Cordialement - Odix";
			try {
				mailService.sendSimpleHtmlMessage(email, "Odix : votre mot de passe a été mis à jour", contenu);
			} catch (MessagingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return utilisateurModified;
	}
	
	public String getUtilisateurRoleByUsername(String username) {
		return utilisateurRepository.findUtilisateurRoleByUsername(username);
	}
}
