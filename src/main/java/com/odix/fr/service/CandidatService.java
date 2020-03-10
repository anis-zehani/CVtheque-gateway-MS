package com.odix.fr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.odix.fr.model.Candidat;

public interface CandidatService {
	
public List<Candidat> getAllCandidats(String etat);
	
	public List<Candidat> getAllCandidatsByOpportunite(UUID idOpportunite);
	
	public List<Candidat> getAllCandidatsByTechnologie(UUID idTechnologie);
	
	public List<Candidat> getAllCandidatsByListTechnologies(ArrayList<UUID> listTechnologies);
	
	public List<Candidat> getAllCandidatsByCertification(UUID idCertification);
	
	public List<Candidat> getAllCandidatsByEntreprise(UUID idEntreprise);
	
	public Candidat getCandidat(UUID id);
	
	public Long getCountCandidats();
	
	public Candidat getCandidatByIdLinkedin(String idLinkedin);
	
	public Candidat addCandidat(Candidat candidat);
	
	public void addCandidatsToOpportunite(UUID idOpportunite, ArrayList<Candidat> listeCandidats, boolean withDeletion);
	
	public Candidat editCandidat(Candidat candidat);
	
	public Candidat editCandidatAutoFill(Candidat candidat);
	
	public Candidat editEtatCandidat(Candidat candidat);
	
	public void updateLinkCandidatEntreprise(UUID idCandidat);
	
	
	public void deleteCandidat(UUID id);
	
	public void deleteLinkCandidatOpportunite(UUID idCandidat, UUID idOpportunite);
	
	public void deleteLinkCandidatTechnologie(UUID idCandidat, UUID idTechnologie);
	
	public void deleteLinkCandidatCertification(UUID idCandidat, UUID idCertification);
	

	public Candidat addPhotoToCandidat(UUID id, String urlPhoto);
	
	public Candidat addPhotoToCandidatAutoFill(UUID id, String urlPhoto);
	
	public Candidat addCvOdixToCandidat(UUID idCandidat, String urlCvOdix);
	
	public Candidat addCvOriginalToCandidat(UUID idCandidat, String urlCvOriginal);
	
	public Candidat addCvOriginalToCandidatAutoFill(UUID idCandidat, String urlCvOriginal);
}
