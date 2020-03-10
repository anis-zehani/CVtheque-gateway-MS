package com.odix.fr.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odix.fr.model.Candidat;
import com.odix.fr.model.Diplome;
import com.odix.fr.model.Disponibilite;
import com.odix.fr.model.Ecole;
import com.odix.fr.model.Entreprise;
import com.odix.fr.model.Etat;
import com.odix.fr.model.Note;
import com.odix.fr.model.POJONotification;
import com.odix.fr.model.SituationFamiliale;
import com.odix.fr.model.TypeDiplome;
import com.odix.fr.model.TypeVisa;
import com.odix.fr.model.Utilisateur;
import com.odix.fr.model.Visa;
import com.odix.fr.service.CandidatService;
import com.odix.fr.service.UtilisateurService;
import com.odix.fr.util.Consts;
import com.odix.fr.util.Linkedin;
import com.odix.fr.webClients.NotificationClient;

import net.minidev.json.JSONObject;

@CrossOrigin
@RestController
@RequestMapping("/api/gateway/utilisateur")
public class UtilisateurController {
	
	@Autowired
	Linkedin linkedInUtil;
	
	@Autowired
	CandidatService candidatService;
	
	NotificationClient notificationClient;
	
	@Autowired
	UtilisateurService utilisateurService;
	

	public UtilisateurController(NotificationClient notificationClient) {
		super();
		this.notificationClient = notificationClient;
	}

	@GetMapping(value = "/code-linkedin")
	public JSONObject codeLinkedin() {
		
		return linkedInUtil.codeLinkedin();
	}
	
	@PostMapping("/redirect-linkedin/{code}/{state}")
	public ResponseEntity<?> redirectLinkedin(@PathVariable String code, @PathVariable String state) throws Exception {
		
		JSONObject profileLinkedIn = linkedInUtil.redirectLinkedin(code, state);
		
		//Si idLinkedin existe : donc profil a été bien reçu de la part de  Linkedin
		if(profileLinkedIn.get("idLinkedin") != "") {
			
			String idLinkedin = profileLinkedIn.get("idLinkedin").toString();
			
			// On cherche si ce candidat existe --> on le retourne à la UI
			if(candidatService.getCandidatByIdLinkedin(idLinkedin)!= null) {
				
				Candidat ancienCandidat = candidatService.getCandidatByIdLinkedin(idLinkedin);
				
				ResponseEntity<?> response =  linkedInUtil.createAuthenticationToken(ancienCandidat.getUsername(), ancienCandidat.getPassword());
				
				return response;
				
			}
			// idLinkedin n'a pas été trouvé -> candidat n'existe pas --> on le créé et on le retourne à la UI
			else 
			{
				Candidat nouveauCandidat = new Candidat();
				nouveauCandidat.setIdLinkedin(profileLinkedIn.get("idLinkedin").toString());
				nouveauCandidat.setIdentite(profileLinkedIn.get("firstName").toString() + " " + profileLinkedIn.get("lastName").toString());
				nouveauCandidat.setUsername(profileLinkedIn.get("emailAddress").toString()); //username est l'adresse email en fait
				nouveauCandidat.setPassword(profileLinkedIn.get("idLinkedin").toString());
				nouveauCandidat.setEmail(profileLinkedIn.get("emailAddress").toString());

				Diplome diplome = new Diplome();
				diplome.setEcole(new Ecole());
				diplome.setTypeDiplome(TypeDiplome.Non_Mentionee);
				
				Visa visa = new Visa();
				visa.setTypeVisa(TypeVisa.Non_Mentionee);
				nouveauCandidat.setDiplome(diplome);
				nouveauCandidat.setVisa(visa);
				nouveauCandidat.setEntreprise(new Entreprise());

				nouveauCandidat.setDisponibilite(Disponibilite.Non_Mentionee);
				nouveauCandidat.setEtatCandidat(Etat.True);
				nouveauCandidat.setNiveauEnAnglais(Note.Non_Mentionee);
				nouveauCandidat.setNiveauEnFrancais(Note.Non_Mentionee);
				nouveauCandidat.setNoteGlobale(Note.Non_Mentionee);
				nouveauCandidat.setSituationFamiliale(SituationFamiliale.Non_Mentionee);
				
				// Mettre en place la photo de profil Linkedin
				nouveauCandidat.setUrlPhoto(profileLinkedIn.get("profilePicture").toString()); 
				nouveauCandidat.setDateAjout(LocalDateTime.now());
				
				Candidat persistedCandidat = candidatService.addCandidat(nouveauCandidat);
				
				// Génération d'une Notification Destinée à l'Administrateur
				Utilisateur admin = utilisateurService.getUtilisateurByRole("ROLE_ADMINISTRATEUR");
				List<Utilisateur> listeDestinatairesNotification = new ArrayList<Utilisateur>();
				listeDestinatairesNotification.add(admin);
		
				ResponseEntity<?> response = linkedInUtil.createAuthenticationToken(persistedCandidat.getUsername(), persistedCandidat.getPassword());
				
				// Génération de la Notification si pas d'erreur
				if (response != null) {
					// Notification générée par le système (ou bien disons par l'Admin) vers lui même (l'Admin)
					// Feign
					POJONotification pojoNotification = new POJONotification(
							   Consts.objetMsgNotificationCandidatAjoute, 
							   Consts.corpsMsgNotificationCandidatAjouteLinkedin, 
							   listeDestinatairesNotification, 
							   admin,
							   persistedCandidat,
							   null,
							   null);
					notificationClient.generateSimpleNotification(pojoNotification);
				}
				
				return response;
			}
		}
		
		return null;
	}

	// Vérifier si un utilisateur existe via son Email
	@GetMapping("/password-forgotten/{email}")
	public Utilisateur getUtilisateurByEmail(@PathVariable String email) {
		return utilisateurService.getUtilisateurByEmail(email);
	}
	
	// Envoi du mail à l'utilisateur avec un lien de réinitialisation
	@GetMapping("/password-send-email-reset/{email}")
	public Boolean envoiEmailResetPassword(@PathVariable String email) {
		return utilisateurService.sendEmailResetPassword(email);
	}
	
	// L'utilisateur modifie son mot de passe oublié
	@PutMapping("/password-reset/{email}/{password}")
	public Utilisateur resetPasswordUtilisateur(@PathVariable String email, @PathVariable String password) {
		return utilisateurService.resetPasswordUtilisateur(email, password);
	}
	
	@GetMapping("/getUtilisateurByRole/{role}")
	public Utilisateur getUtilisateurByRole(@PathVariable String role) {
		return utilisateurService.getUtilisateurByRole(role);
	}
	
	@GetMapping("/getUtilisateurById/{id}")
	public Utilisateur getUtilisateurById(@PathVariable UUID id) {
		return utilisateurService.getUtilisateurById(id);
	}
}
