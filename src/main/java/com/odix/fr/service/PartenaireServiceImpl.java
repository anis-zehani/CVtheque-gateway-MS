package com.odix.fr.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.odix.fr.messaging.PartenaireProducers;
import com.odix.fr.model.Entreprise;
import com.odix.fr.model.Etat;
import com.odix.fr.model.Partenaire;
import com.odix.fr.repository.PartenaireRepository;
import com.odix.fr.util.Consts;
import com.odix.fr.util.LocalStorageService;

@Service
public class PartenaireServiceImpl implements PartenaireService{

	private final PartenaireRepository partenaireRepository;
	private final LocalStorageService storageService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Autowired
	PartenaireProducers partenaireProducers;

	PartenaireServiceImpl(PartenaireRepository partenaireRepository, LocalStorageService storageService) {
		super();
		this.partenaireRepository = partenaireRepository;
		this.storageService = storageService;
	}

	public List<Partenaire> getAllPartenaires(String etatPartenaire) {
		
		//On filtre selon l'état : Actif / Inactif
		if(etatPartenaire.equals("True"))
		{
			return partenaireRepository.findByEtatPartenaire(Etat.True);
		}
		else 
		{
			return partenaireRepository.findByEtatPartenaire(Etat.False);
		}

	}

	public List<Partenaire> getAllPartenairesByEntreprise(UUID idEntreprise){
		
			Entreprise entreprise = new Entreprise();
			entreprise.setIdEntreprise(idEntreprise);
			
			return partenaireRepository.findAllByEntreprise(entreprise);
	}
	public Partenaire getPartenaire(UUID id) {
		return partenaireRepository.getOne(id);
	}
	
	public Long getCountPartenaires() {
		return partenaireRepository.count();
	}

	//Ajouter un partenaire
	@Transactional
	public Partenaire addPartenaire(Partenaire partenaire) {
		
		if(partenaireRepository.findByIdentite(partenaire.getIdentite()) == null &&
		   partenaireRepository.findByUsername(partenaire.getUsername()) == null &&
		   partenaireRepository.findByEmail(partenaire.getEmail()) == null) {
			
			//Par défaut, le partenaire est activé
			partenaire.setEtatPartenaire(Etat.True);
	
			if(partenaire.getEntreprise().getIdEntreprise() == null) {
				partenaire.setEntreprise(null);
			}
			
			//On met l'image par défaut à tout le monde : elle pourra être écrasée plus tard
			partenaire.setUrlPhoto("");
			
			partenaire.setDateAjout(LocalDateTime.now());
			
			if(partenaire.getPassword() != null) {
				// Encoder le Password avant de l'insérer dans la base
				partenaire.setPassword(bcryptEncoder.encode(partenaire.getPassword()));
			}
			
			Partenaire toAddPartenaire =  partenaireRepository.save(partenaire);
			//Consistency avec les autres MS
			this.partenaireProducers.addPartenaireProducer(toAddPartenaire);
			return toAddPartenaire;
			}
		return null;

	}
	
	//Affecter une photo à un partenaire (fonction appelée dans Ajout + Update)
	public Partenaire addPhotoToPartenaire(UUID id, String urlPhoto) {
		
		if(partenaireRepository.existsById(id))
		{
			Partenaire partenaire = partenaireRepository.getOne(id);
			
			//delete ancienne photo : si elle existe dans le cas d'un Update
			if(partenaire.getUrlPhoto() != null)
			{
				storageService.deletePhoto(partenaire.getUrlPhoto());
			}

			//update URL photo avec nouveau nom
			partenaire.setUrlPhoto(urlPhoto);

			return partenaireRepository.save(partenaire);
		}
		return null;
	}

	//Modifier un partenaire
	@Transactional
	public Partenaire editPartenaire(Partenaire partenaire) {
		
		//L'Update url photo se fait en haut dans la fonction addPhotoToPartenaire
		if(partenaireRepository.existsById(partenaire.getId()) && 
		   partenaire.getIdentite() != "" &&
		   partenaire.getUsername() != "" &&
		   partenaire.getEmail() != "") {
			
			if(partenaire.getEntreprise().getIdEntreprise() == null) {
				partenaire.setEntreprise(null);
			}
			//Récupérer le password affiché sur le formulaire
			String passwordFormulaire = partenaire.getPassword();
			//Récupérer le password actuel dans la BDD
			String passwordBDD = partenaireRepository.findByUsername(partenaire.getUsername()).getPassword();
			
			// Si le Password récupéré est différent de celui qui est stocké : on change le password
			if(!passwordFormulaire.equals(passwordBDD)) {
				partenaire.setPassword(bcryptEncoder.encode(partenaire.getPassword()));
			}
			// Sinon on réinsére l'ancien password
			else {
				partenaire.setPassword(passwordBDD);
			}
			
			Partenaire toEditPartenaire =  partenaireRepository.save(partenaire);
			//Consistency avec les autres MS
			this.partenaireProducers.addPartenaireProducer(toEditPartenaire);
			return toEditPartenaire;
		}
		return null;
	}
	
	//Modifier l'état d'un Partenaire : Actif/Inactif
	@Transactional
	public Partenaire editEtatPartenaire(Partenaire partenaire) {
		
		if(partenaireRepository.existsById(partenaire.getId()))
		{
			Partenaire partenaireToUpdate = partenaireRepository.getOne(partenaire.getId());

			if(partenaireToUpdate.getEtatPartenaire().equals(Etat.True))
			{
				partenaireToUpdate.setEtatPartenaire(Etat.False);
			}
			else 
			{
				partenaireToUpdate.setEtatPartenaire(Etat.True);
			}
			
			Partenaire toEditPartenaire =  partenaireRepository.save(partenaireToUpdate);
			//Consistency avec les autres MS
			this.partenaireProducers.addPartenaireProducer(toEditPartenaire);
			return toEditPartenaire;
		}
		return null;
	}
	
	// AutoFill Edit Partenaire : à partir de son espace Partenaire 
	@Override
	public Partenaire editPartenaireAutoFill(Partenaire partenaire) {
		//L'Update url photo se fait en haut dans la fonction addPhotoToPartenaire
		if(partenaireRepository.existsById(partenaire.getId()) && 
				partenaire.getIdentite() != "" && partenaire.getEmail() != "") {
			
			Partenaire partenaireToUpdateAutoFill = partenaireRepository.getOne(partenaire.getId());
			
			partenaireToUpdateAutoFill.setEmailPartenaireAutoFill(partenaire.getEmailPartenaireAutoFill());		
			partenaireToUpdateAutoFill.setTelephonePartenaireAutoFill(partenaire.getTelephonePartenaireAutoFill());
			partenaireToUpdateAutoFill.setEntrepriseActuellePartenaireAutoFill(partenaire.getEntrepriseActuellePartenaireAutoFill());
			partenaireToUpdateAutoFill.setPosteOccupePartenaireAutoFill(partenaire.getPosteOccupePartenaireAutoFill());
			partenaireToUpdateAutoFill.setTelephoneEntreprisePartenaireAutoFill(partenaire.getTelephoneEntreprisePartenaireAutoFill());
			partenaireToUpdateAutoFill.setEffectifEntreprisePartenaireAutoFill(partenaire.getEffectifEntreprisePartenaireAutoFill());
			partenaireToUpdateAutoFill.setSiteInternetEntreprisePartenaireAutoFill(partenaire.getSiteInternetEntreprisePartenaireAutoFill());
			partenaireToUpdateAutoFill.setAdresseEntreprisePartenaireAutoFill(partenaire.getAdresseEntreprisePartenaireAutoFill());
			partenaireToUpdateAutoFill.setDescriptionDetailleePartenaireAutoFill(partenaire.getDescriptionDetailleePartenaireAutoFill());
			
			return partenaireRepository.save(partenaireToUpdateAutoFill);
				}
		return null;
	}
	
	

	// AutoFill Edit PHOTO DE PROFIL Partenaire : à partir de son espace Partenaire
	@Override
	public Partenaire addPhotoToPartenaireAutoFill(UUID id, String urlPhoto) {
		
		if(partenaireRepository.existsById(id))
		{
			Partenaire partenaire = partenaireRepository.getOne(id);
			
		//delete ancienne photo AutoFill : si elle existe dans le cas d'un Update
		if(partenaire.getUrlPhotoPartenaireAutoFill() != null)
		{
			storageService.deletePhoto(partenaire.getUrlPhotoPartenaireAutoFill());
		}

		//update URL photo avec nouveau nom
		partenaire.setUrlPhotoPartenaireAutoFill(urlPhoto);

		return partenaireRepository.saveAndFlush(partenaire);
	}
	return null;
	}

	//UPDATE le lien entre un partenaire et une entreprise : met entreprise à NULL
	public void updateLinkPartenaireEntreprise(UUID idPartenaire) {
		if(partenaireRepository.existsById(idPartenaire))
		{
			partenaireRepository.updateLinkPartenaireEntreprise(idPartenaire);
		}
	}
	
	//Supprimer un partenaire
	@Transactional
	public Boolean deletePartenaire(UUID id) {
		
		if(partenaireRepository.existsById(id))
		{
			Partenaire partenaire = partenaireRepository.getOne(id);
			
			try
			{
				//On supprime d'abord la photo si ce n'est pas un avatar
				if(partenaire.getUrlPhoto() != null)
				{
					storageService.deletePhoto(Consts.rootLocation+partenaire.getUrlPhoto());
					//On supprime la ligne de la base
				}
				partenaireRepository.deleteById(id);
				
				//Consistency avec les autres MS
				this.partenaireProducers.deletePartenaireProducer(id);
			}
			catch(Exception e) 
			{
				System.out.print("Erreur durant deletePartenaire :"+e);
				return false;	
			}
		}
		return true;
	}
}
