package com.odix.fr.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.odix.fr.model.Candidat;
import com.odix.fr.model.Curriculum;
import com.odix.fr.model.Etat;
import com.odix.fr.model.POJONotification;
import com.odix.fr.model.Utilisateur;
import com.odix.fr.repository.CandidatRepository;
import com.odix.fr.util.Consts;
import com.odix.fr.util.LocalStorageService;
import com.odix.fr.webClients.NotificationClient;

@Service
public class CandidatServiceImpl implements CandidatService {
	
	private final CandidatRepository candidatRepository;
	private final LocalStorageService storageService;
	
	NotificationClient notificationClient;
	
	@Autowired
	UtilisateurService utilisateurService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	CandidatServiceImpl(CandidatRepository candidatRepository, LocalStorageService storageService, NotificationClient notificationClient) 
	{
		super();
		this.candidatRepository = candidatRepository;
		this.storageService = storageService;
		this.notificationClient = notificationClient;
	}


	public List<Candidat> getAllCandidats(String etatCandidat) {
		
		//On filtre selon l'état : Actif / Inactif
		if(etatCandidat.equals("True"))
		{
			return candidatRepository.findByEtatCandidat(Etat.True);
		}
		else 
		{
			return candidatRepository.findByEtatCandidat(Etat.False);
		}

	}
	
	
	//INNER JOIN : retourne les candidats par Opportunité
	public List<Candidat> getAllCandidatsByOpportunite(UUID idOpportunite){
		
		return candidatRepository.findAllCandidatsByOpportunite(idOpportunite);
	}
	
	//INNER JOIN : retourne les candidats par Technologie
	public List<Candidat> getAllCandidatsByTechnologie(UUID idTechnologie){
			
		return candidatRepository.findAllCandidatsByTechnologie(idTechnologie);
	}
	
	//La liste des candidats qui ont une Technologie au moins dans la liste fournie
	public List<Candidat> getAllCandidatsByListTechnologies(ArrayList<UUID> listTechnologies){
		
		List<Candidat> listeCandidats = candidatRepository.findAllCandidatsByListTechnologies(listTechnologies);
		
		return listeCandidats;
	}
		
	//INNER JOIN : retourne les candidats par Certification
	public List<Candidat> getAllCandidatsByCertification(UUID idCertification){
			
		return candidatRepository.findAllCandidatsByCertification(idCertification);
	}
	
	//retourne les candidats par Entreprise
	public List<Candidat> getAllCandidatsByEntreprise(UUID idEntreprise){
			
		return candidatRepository.findAllCandidatsByEntreprise(idEntreprise);
	}
	
	//Supprimer le lien entre un candidat et une opportunité
	public void deleteLinkCandidatOpportunite(UUID idCandidat, UUID idOpportunite) {
		 	   candidatRepository.deleteLinkCandidatOpportunite(idCandidat, idOpportunite);
	}
	
	//Supprimer le lien entre un candidat et une technologie
	public void deleteLinkCandidatTechnologie(UUID idCandidat, UUID idTechnologie) {
			 	candidatRepository.deleteLinkCandidatTechnologie(idCandidat, idTechnologie);
	}
	
	//Supprimer le lien entre un candidat et une certification
	public void deleteLinkCandidatCertification(UUID idCandidat, UUID idCertification) {
				candidatRepository.deleteLinkCandidatCertification(idCandidat, idCertification);
	}

	public Candidat getCandidat(UUID id) {
		return candidatRepository.getOne(id);
	}
	
	public Long getCountCandidats() {
		return candidatRepository.count();
	}
	
	//Cherche le candidat via son idLinkedin
	public Candidat getCandidatByIdLinkedin(String idLinkedin) {
		return candidatRepository.findByIdLinkedin(idLinkedin);
	}
	
	//Créer un lien entre des candidats et une opportunité
	public void addCandidatsToOpportunite(UUID idOpportunite, ArrayList<Candidat> listeCandidats, boolean withDeletion) {
		
		if(withDeletion) {
			candidatRepository.deleteAllCandidatsByOpportunite(idOpportunite);
		}
		if(idOpportunite != null && !listeCandidats.isEmpty()) {
			for(int i=0;i<listeCandidats.size();i++) {
				candidatRepository.addCandidatToOpportunite(listeCandidats.get(i).getId(), idOpportunite);
			}
		}
	}

	//Ajouter un candidat
	public Candidat addCandidat(Candidat candidat) {
		
		if(candidatRepository.findByIdentite(candidat.getIdentite()) == null &&
		   candidatRepository.findByUsername(candidat.getUsername()) == null &&
		   candidatRepository.findByEmail(candidat.getEmail()) == null) {
		//Par défaut, le candidat est activé
		candidat.setEtatCandidat(Etat.True);
		
		//Indispensable afin de créer un Objet CV au démarrage : util pour l'ajout des PJs
		Curriculum cv = new Curriculum();
		candidat.setCurriculum(cv);
			
		//Entreprise : Si le user n'a pas ajouté une Entreprise
		if(candidat.getEntreprise().getIdEntreprise() == null) {
			//Obligatoire pour @ManyToOne
			candidat.setEntreprise(null);
		}
		//Ecole : Si le user n'a pas ajouté une Ecole pour un Diplome
		if(candidat.getDiplome().getEcole().getIdEcole() == null) {
			//Obligatoire pour @ManyToOne
			candidat.getDiplome().setEcole(null);
		}
		
		candidat.setDateAjout(LocalDateTime.now());
		
		//Encoder le Password avant de l'insérer dans la base
		candidat.setPassword(bcryptEncoder.encode(candidat.getPassword()));
		
		Candidat savedCandidat = candidatRepository.save(candidat);
		
		// Si c'est un Candidat via Formulaire alors on envoi la notification ici, sinon ça sera dans UtilisateurController
		if(candidat.getIdLinkedin() == "") 
		{	
			// Génération d'une Notification Destinée à l'Administrateur
			Utilisateur admin = utilisateurService.getUtilisateurByRole("ROLE_ADMINISTRATEUR");
			List<Utilisateur> listeDestinatairesNotification = new ArrayList<Utilisateur>();
			listeDestinatairesNotification.add(admin);
			
			// Notification générée par le système (ou bien disons par l'Admin) vers lui même (l'Admin)
			// Feign
			POJONotification pojoNotification = new POJONotification(
					   Consts.objetMsgNotificationCandidatAjoute, 
					   Consts.corpsMsgNotificationCandidatAjouteFormulaire,
					   listeDestinatairesNotification, 
					   admin,
					   savedCandidat,
					   null,
					   null);
			notificationClient.generateSimpleNotification(pojoNotification);
		}
		return  savedCandidat;
		}
	return null;
	}
	
	//Affecter une photo à un candidat (fonction appelée dans Ajout + Update)
	public Candidat addPhotoToCandidat(UUID id, String urlPhoto) {
		
		if(candidatRepository.existsById(id))
		{
			Candidat candidat = candidatRepository.getOne(id);
			
			//delete ancienne photo : si elle existe dans le cas d'un Update
			if(candidat.getUrlPhoto() != null)
			{
				storageService.deletePhoto(candidat.getUrlPhoto());
			}

			//update URL photo avec nouveau nom
			candidat.setUrlPhoto(urlPhoto);

			return candidatRepository.saveAndFlush(candidat);
		}
		
		return null;
	}
	
	//Affecter une photo à un candidat (fonction appelée en mode AutoFill dans espace Candidat)
	public Candidat addPhotoToCandidatAutoFill(UUID id, String urlPhoto) {
			
		if(candidatRepository.existsById(id))
			{
			Candidat candidat = candidatRepository.getOne(id);
				
			//delete ancienne photo AutoFill : si elle existe dans le cas d'un Update
			if(candidat.getUrlPhotoAutoFill() != null)
			{
				storageService.deletePhoto(candidat.getUrlPhotoAutoFill());
			}

			//update URL photo avec nouveau nom
			candidat.setUrlPhotoAutoFill(urlPhoto);

			return candidatRepository.saveAndFlush(candidat);
		}
			
		return null;
	}	
	
	//Ajout du CV Odix
	public Candidat addCvOdixToCandidat(UUID idCandidat, String urlCvOdix) {
		
		if(candidatRepository.existsById(idCandidat))
		{
			Candidat candidat = candidatRepository.getOne(idCandidat);
			
			try 
			{
				//delete ancien CvOdix : s'il existe dans le cas d'un Update
				if(candidat.getCurriculum() != null && candidat.getCurriculum().getUrlCvOdix()!= null)
				{
					storageService.deleteCvOdix(candidat.getCurriculum().getUrlCvOdix());
				}
				
				candidat.getCurriculum().setUrlCvOdix(urlCvOdix);
			}
			catch(Exception e) 
			{
				System.out.print("Erreur durant deleteCvOdix :"+e);
			}
			
			return candidatRepository.saveAndFlush(candidat);
		}
		
			return null;
		}
		
	//Ajout du CV Original
	public Candidat addCvOriginalToCandidat(UUID idCandidat, String urlCvOriginal) {
			
		if(candidatRepository.existsById(idCandidat))
		{
			Candidat candidat = candidatRepository.getOne(idCandidat);
			
			try 
			{
				//delete ancien CvOriginal : s'il existe dans le cas d'un Update
				if(candidat.getCurriculum() != null && candidat.getCurriculum().getUrlCvOriginal()!= null)
				{
					storageService.deleteCvOriginal(candidat.getCurriculum().getUrlCvOriginal());
					
				}
				
				candidat.getCurriculum().setUrlCvOriginal(urlCvOriginal);
			}
			catch(Exception e) 
			{
				System.out.print("Erreur durant CvOriginal :"+e);
			}
			
			return candidatRepository.saveAndFlush(candidat);
		}
		
			return null;
		}
	
	//Ajout du CV Original en mode AutoFill
	public Candidat addCvOriginalToCandidatAutoFill(UUID idCandidat, String urlCvOriginal) {
				
		if(candidatRepository.existsById(idCandidat))
			{
			Candidat candidat = candidatRepository.getOne(idCandidat);
			try 
			{
				//delete ancien CvOriginalAutoFill : s'il existe dans le cas d'un Update
				if(candidat.getCurriculum() != null && candidat.getCurriculum().getUrlCvOriginalAutoFill()!= null)
				{
					storageService.deleteCvOriginal(candidat.getCurriculum().getUrlCvOriginalAutoFill());
				}	
				candidat.getCurriculum().setUrlCvOriginalAutoFill(urlCvOriginal);
			}
			catch(Exception e) 
			{
				System.out.print("Erreur durant CvOriginalAutoFill :"+e);
			}
			return candidatRepository.saveAndFlush(candidat);
		}
		return null;
	}
		
	//Modifier un candidat par L'administrateur
	public Candidat editCandidat(Candidat candidat) {
		
		//L'Update url photo se fait en haut dans la fonction addPhotoToCandidat
		if(candidatRepository.existsById(candidat.getId()) && 
				candidat.getIdentite() != "" &&
				candidat.getUsername() != "" &&
				candidat.getEmail() != "") {
			
			Candidat candidatToUpdate = candidatRepository.getOne(candidat.getId());
			
			candidatToUpdate.setIdentite(candidat.getIdentite());
			candidatToUpdate.setTelephone(candidat.getTelephone());
			candidatToUpdate.setEmail(candidat.getEmail());
			candidatToUpdate.setPosteOccupe(candidat.getPosteOccupe());
			candidatToUpdate.setDescriptionDetaillee(candidat.getDescriptionDetaillee());
			
			/*
			 * entreprise
			 */
			if(candidat.getEntreprise().getIdEntreprise() != null)
			{
				candidatToUpdate.setEntreprise(candidat.getEntreprise());
			}

			candidatToUpdate.setDateDeNaissance(candidat.getDateDeNaissance());
			candidatToUpdate.setAdresse(candidat.getAdresse());
			candidatToUpdate.setSituationFamiliale(candidat.getSituationFamiliale());
			candidatToUpdate.setNombreEnfants(candidat.getNombreEnfants());
			candidatToUpdate.setSalaireActuel(candidat.getSalaireActuel());
			candidatToUpdate.setPretentionSalariale(candidat.getPretentionSalariale());
			candidatToUpdate.setNiveauEnFrancais(candidat.getNiveauEnFrancais());
			candidatToUpdate.setNiveauEnAnglais(candidat.getNiveauEnAnglais());
			candidatToUpdate.setNoteGlobale(candidat.getNoteGlobale());
			candidatToUpdate.setDisponibilite(candidat.getDisponibilite());
			candidatToUpdate.setEtatCandidat(candidat.getEtatCandidat());
			candidatToUpdate.setDateDemarrageCarriere(candidat.getDateDemarrageCarriere());
			candidatToUpdate.setDateEpuisementPasseport(candidat.getDateEpuisementPasseport());
			
			/*
			 * diplome : @OneToOne
			 */
			if(candidat.getDiplome() != null)
			{
				candidatToUpdate.setDiplome(candidat.getDiplome());
			}
			
			/*
			 * visa : @OneToOne
			 */
			if(candidat.getVisa() != null)
			{
				candidatToUpdate.setVisa(candidat.getVisa());
			}
			
			/*
			 * listeTechnologies : @ManyToMany
			 */
			if(candidat.getListeTechnologies() != null)
			{
				candidatToUpdate.setListeTechnologies(candidat.getListeTechnologies());
			}
			
			/*
			 * listeOpportunites : @ManyToMany
			 */
			if(candidat.getListeOpportunites() != null)
			{
				candidatToUpdate.setListeOpportunites(candidat.getListeOpportunites());
			}
			
			/*
			 * listeCertifications : @ManyToMany
			 */
			if(candidat.getListeCertifications() != null)
			{
				candidatToUpdate.setListeCertifications(candidat.getListeCertifications());
			}
			
			//Récupérer le password affiché sur le formulaire
			String passwordFormulaire = candidat.getPassword();
			//Récupérer le password actuel dans la BDD
			String passwordBDD = candidatRepository.findByUsername(candidat.getUsername()).getPassword();
			
			// Si le Password Affiché est différent de celui qui est stocké : on change le password
			if(!passwordFormulaire.equals(passwordBDD))
			{
				candidatToUpdate.setPassword(bcryptEncoder.encode(candidat.getPassword()));
			}
			// Sinon on réinsére l'ancien password
			else
			{
				candidatToUpdate.setPassword(passwordBDD);
			}

			return candidatRepository.save(candidatToUpdate);
		}
		return null;
	}
	
	//Modifier un candidat par lui même : AutoFill sur Espace Candidat
	public Candidat editCandidatAutoFill(Candidat candidat) {
		
		//L'Update url photo se fait en haut dans la fonction addPhotoToCandidat
		if(candidatRepository.existsById(candidat.getId()) && 
				candidat.getIdentite() != "" &&
				candidat.getUsername() != "" &&
				candidat.getEmail() != "") {
			
			Candidat candidatToUpdateAutoFill = candidatRepository.getOne(candidat.getId());
			
			candidatToUpdateAutoFill.setTelephoneAutoFill(candidat.getTelephoneAutoFill());
			candidatToUpdateAutoFill.setEmailAutoFill(candidat.getEmailAutoFill());
			candidatToUpdateAutoFill.setPosteOccupeAutoFill(candidat.getPosteOccupeAutoFill());
			candidatToUpdateAutoFill.setDescriptionDetailleeAutoFill(candidat.getDescriptionDetailleeAutoFill());
			
			/*
			 * entreprise
			 */
			candidatToUpdateAutoFill.setEntrepriseAutoFill(candidat.getEntrepriseAutoFill());

			candidatToUpdateAutoFill.setDateDeNaissanceAutoFill(candidat.getDateDeNaissanceAutoFill());
			candidatToUpdateAutoFill.setAdresseAutoFill(candidat.getAdresseAutoFill());
			candidatToUpdateAutoFill.setSituationFamilialeAutoFill(candidat.getSituationFamilialeAutoFill());
			candidatToUpdateAutoFill.setNombreEnfantsAutoFill(candidat.getNombreEnfantsAutoFill());
			candidatToUpdateAutoFill.setSalaireActuelAutoFill(candidat.getSalaireActuelAutoFill());
			candidatToUpdateAutoFill.setPretentionSalarialeAutoFill(candidat.getPretentionSalarialeAutoFill());

			candidatToUpdateAutoFill.setDisponibiliteAutoFill(candidat.getDisponibiliteAutoFill());

			candidatToUpdateAutoFill.setDateDemarrageCarriereAutoFill(candidat.getDateDemarrageCarriereAutoFill());
			candidatToUpdateAutoFill.setDateEpuisementPasseportAutoFill(candidat.getDateEpuisementPasseportAutoFill());
			
			/*
			 * diplome
			 */
			 candidatToUpdateAutoFill.getDiplome().setTypeDiplomeAutoFill(candidat.getDiplome().getTypeDiplomeAutoFill());
			 candidatToUpdateAutoFill.getDiplome().setDateObtentionDiplomeAutoFill(candidat.getDiplome().getDateObtentionDiplomeAutoFill());
			 candidatToUpdateAutoFill.getDiplome().setEcoleAutoFill(candidat.getDiplome().getEcoleAutoFill());
			/*
			 * visa
			 */
			 candidatToUpdateAutoFill.getVisa().setTypeVisaAutoFill(candidat.getVisa().getTypeVisaAutoFill());
			 candidatToUpdateAutoFill.getVisa().setDateDebutVisaAutoFill(candidat.getVisa().getDateDebutVisaAutoFill());
			 candidatToUpdateAutoFill.getVisa().setDateFinVisaAutoFill(candidat.getVisa().getDateFinVisaAutoFill());

			return candidatRepository.save(candidatToUpdateAutoFill);
		}
		return null;
	}
	
	
	//Modifier l'état d'un Candidat : Actif/Inactif
	public Candidat editEtatCandidat(Candidat candidat) {
		
		if(candidatRepository.existsById(candidat.getId()))
		{
			Candidat candidatToUpdate = candidatRepository.getOne(candidat.getId());

			if(candidatToUpdate.getEtatCandidat().equals(Etat.True))
			{
				candidatToUpdate.setEtatCandidat(Etat.False);
			}
			else 
			{
				candidatToUpdate.setEtatCandidat(Etat.True);
			}
			
			return candidatRepository.save(candidatToUpdate);
		}
			
			return null;
	}
	
	//Update le lien entre un candidat et une entreprise : met entreprise à NULL
	public void updateLinkCandidatEntreprise(UUID idCandidat) {
		
		if(candidatRepository.existsById(idCandidat))
		{
			candidatRepository.updateLinkCandidatEntreprise(idCandidat);
		}
		
	}
	
	//Supprimer un candidat
	public void deleteCandidat(UUID id) {
		
		if(candidatRepository.existsById(id))
		{
			Candidat candidat = candidatRepository.getOne(id);

			try
			{
				
				//On supprime d'abord la photo si ce n'est pas un avatar
				if(candidat.getUrlPhoto() != null)
				{
					storageService.deletePhoto(Consts.rootLocation+candidat.getUrlPhoto());
				}
				
				//On supprime le CV Odix du Disque
				if(candidat.getCurriculum() != null && candidat.getCurriculum().getUrlCvOdix() != null)
				{
					storageService.deleteCvOdix(Consts.rootLocation+candidat.getCurriculum().getUrlCvOdix());
				}
				
				//On supprime le CV Original du Disque
				if(candidat.getCurriculum() != null && candidat.getCurriculum().getUrlCvOriginal() != null)
				{
					storageService.deleteCvOriginal(Consts.rootLocation+candidat.getCurriculum().getUrlCvOriginal());
				}
				
			}
			catch(Exception e) 
			{
				System.out.print("Erreur durant deleteCandidat :"+e);
			}
			
				candidatRepository.deleteById(id);
		}
	}
}