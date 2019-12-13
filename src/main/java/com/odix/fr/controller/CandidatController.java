package com.odix.fr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.odix.fr.model.Candidat;
import com.odix.fr.service.CandidatService;
import com.odix.fr.util.LocalStorageService;

@CrossOrigin
@RestController
@RequestMapping("/api/candidat")
public class CandidatController {
	
	@Autowired
	LocalStorageService storageService;
	
	@Autowired
	private final CandidatService candidatService;
	 
	List<String> files = new ArrayList<String>();
	  
	CandidatController(CandidatService candidatService) {
		this.candidatService = candidatService;
	}
	
	@GetMapping("/all/{etatCandidat}")
	public List<Candidat> getAllCandidats(@PathVariable String etatCandidat) {
	    return candidatService.getAllCandidats(etatCandidat);
	}
	
	//Lister les candidats par ID Opportunité
	@GetMapping("/allCandidatsByOpportunite/{id}")
	public List<Candidat> getAllCandidatsByOpportunite(@PathVariable UUID id) {
	    return candidatService.getAllCandidatsByOpportunite(id);
	}
	
	//Lister les candidats par ID Technologie
	@GetMapping("/allCandidatsByTechnologie/{id}")
	public List<Candidat> getAllCandidatsByTechnologie(@PathVariable UUID id) {
	    return candidatService.getAllCandidatsByTechnologie(id);
	}
	
	@GetMapping("/allCandidatsByListTechnologies/{listTechnologies}")
	//La liste des candidats qui ont une Technologie au moins dans la liste fournie
	public List<Candidat> getAllCandidatsByListTechnologies(@PathVariable ArrayList<UUID> listTechnologies){
		return candidatService.getAllCandidatsByListTechnologies(listTechnologies);
	}
	
	//Lister les candidats par ID Certification
	@GetMapping("/allCandidatsByCertification/{id}")
	public List<Candidat> getAllCandidatsByCertification(@PathVariable UUID id) {
	    return candidatService.getAllCandidatsByCertification(id);
	}
	
	//Lister les candidats par ID Entreprise
	@GetMapping("/allCandidatsByEntreprise/{id}")
	public List<Candidat> getAllCandidatsByEntreprise(@PathVariable UUID id) {
	    return candidatService.getAllCandidatsByEntreprise(id);
	}
	
	@GetMapping("/oneCandidat/{id}")
	public Candidat getCandidat(@PathVariable UUID id) {
		return candidatService.getCandidat(id);
	}
	
	//Ajouter un candidat
	@PostMapping()
	public Candidat addCandidat(@Valid @RequestBody Candidat candidat) {
		return candidatService.addCandidat(candidat);
	}
	
	@PostMapping("/addPhoto/{id}")
	public Candidat addPhoto(@PathVariable UUID id, @RequestParam("photo") MultipartFile photo) {
		//la photo est placée sur le serveur
	    String urlPhoto =  storageService.addPhoto(photo);
	    //la photo est affectée au candidat via son id
	    return candidatService.addPhotoToCandidat(id, urlPhoto);
	}
	
	@PostMapping("/addPhotoCandidatAutoFill/{id}")
	public Candidat addPhotoCandidatAutoFill(@PathVariable UUID id, @RequestParam("photoAutoFill") MultipartFile photoAutoFill) {
		//la photo est placée sur le serveur
	    String urlPhoto =  storageService.addPhoto(photoAutoFill);
	    //la photo est affectée au candidat via son id (mode Auto Fill)
	    return candidatService.addPhotoToCandidatAutoFill(id, urlPhoto);
	}
	
	@PostMapping("/addCvOdix/{id}")
	public Candidat addCvOdix(@PathVariable UUID id, @RequestParam("cvOdix") MultipartFile cvOdix) {	
		//le CvOdix est placé sur le serveur
	    String urlCvOdix =  storageService.addCvOdix(cvOdix);
	    //le CvOdix est affecté au candidat via son id
	    return candidatService.addCvOdixToCandidat(id, urlCvOdix);
	}
	
	@PostMapping("/addCvOriginalCandidatAutoFill/{id}")
	public Candidat addCvOriginalCandidatAutoFill(@PathVariable UUID id, @RequestParam("cvOriginalAutoFill") MultipartFile cvOriginalAutoFill) {
		//le CvOriginal est placé sur le serveur
	    String urlCvOriginal =  storageService.addCvOriginal(cvOriginalAutoFill);
	    //le CvOriginal est affecté au candidat via son id (mode Auto Fill)
	    return candidatService.addCvOriginalToCandidatAutoFill(id, urlCvOriginal);
	}
	
	@PostMapping("/addCvOriginal/{id}")
	public Candidat addCvOriginal(@PathVariable UUID id, @RequestParam("cvOriginal") MultipartFile cvOriginal) {
		//le CvOriginal est placé sur le serveur
	    String urlCvOriginal =  storageService.addCvOriginal(cvOriginal);
	    //le CvOriginal est affecté au candidat via son id
	    return candidatService.addCvOriginalToCandidat(id, urlCvOriginal);
	}
	
	//Permet de lier des candidats à une opportunité récement créée
	@PostMapping("/addCandidatsToOpportunite/{idOpportunite}/{withDeletion}")
	public void addCandidatsToOpportunite(@PathVariable UUID idOpportunite, @RequestBody ArrayList<Candidat> listeCandidats, @PathVariable boolean withDeletion) {

		candidatService.addCandidatsToOpportunite(idOpportunite, listeCandidats, withDeletion);
	}
	
	
	@PutMapping()
	public Candidat editCandidat(@Valid @RequestBody Candidat candidat) {
		return candidatService.editCandidat(candidat);
	}
	
	// Le candidat modifie son profil par lui même via son Espace Candidat
	@PutMapping("/editCandidatAutoFill")
	public Candidat editCandidatAutoFill(@Valid @RequestBody Candidat candidat) {
		return candidatService.editCandidatAutoFill(candidat);
	}
	
	@PutMapping("/editEtat")
	public Candidat editEtatCandidat(@Valid @RequestBody Candidat candidat) {
		return candidatService.editEtatCandidat(candidat);
	}
	
	//Update le lien entre un candidat et une entreprise : met entreprise à NULL
	@PutMapping("/updateLinkCandidatEntreprise")
	public void updateLinkCandidatEntreprise(@Valid @RequestBody UUID idCandidat) {
			   candidatService.updateLinkCandidatEntreprise(idCandidat);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCandidat(@PathVariable UUID id) {
			   candidatService.deleteCandidat(id);
	}
	
	//Supprimer le lien entre un candidat et une opportunité
	@DeleteMapping("/deleteLinkCandidatOpportunite/{idCandidat}/{idOpportunite}")
	public void deleteLinkCandidatOpportunite(@PathVariable UUID idCandidat, @PathVariable UUID idOpportunite) {
			   candidatService.deleteLinkCandidatOpportunite(idCandidat, idOpportunite);
	}
	
	//Supprimer le lien entre un candidat et une technologie
	@DeleteMapping("/deleteLinkCandidatTechnologie/{idCandidat}/{idTechnologie}")
	public void deleteLinkCandidatTechnologie(@PathVariable UUID idCandidat, @PathVariable UUID idTechnologie) {
			   candidatService.deleteLinkCandidatTechnologie(idCandidat, idTechnologie);
	}
	
	//Supprimer le lien entre un candidat et une certification
	@DeleteMapping("/deleteLinkCandidatCertification/{idCandidat}/{idCertification}")
	public void deleteLinkCandidatCertification(@PathVariable UUID idCandidat, @PathVariable UUID idCertification) {
			   candidatService.deleteLinkCandidatCertification(idCandidat, idCertification);
	}

}