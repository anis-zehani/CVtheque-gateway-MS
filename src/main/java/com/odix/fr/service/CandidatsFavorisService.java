package com.odix.fr.service;

import java.util.List;
import java.util.UUID;

import com.odix.fr.model.CandidatsFavoris;

public interface CandidatsFavorisService {
	
	public List<CandidatsFavoris> getAllCandidatsFavorisForUtilisateur(UUID idUtilisateur);
	
	public CandidatsFavoris addCandidatToFavorisToUtilisateur(CandidatsFavoris candidatsFavoris);
	
	public void deleteCandidatFromFavorisToUtilisateur(UUID idCandidatFavori);
	
	public boolean checkIfCandidatExistsDansFavorisUtilisateur(UUID idUtilisateur, UUID idUCandidat);
}
