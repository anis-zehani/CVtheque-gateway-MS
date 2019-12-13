package com.odix.fr.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.odix.fr.model.CandidatsFavoris;
import com.odix.fr.repository.CandidatsFavorisRepository;


@Service
public class CandidatsFavorisServiceImpl implements CandidatsFavorisService {
	
	private final CandidatsFavorisRepository candidatsFavorisRepository;
	
	public CandidatsFavorisServiceImpl(CandidatsFavorisRepository candidatsFavorisRepository) {
		super();
		this.candidatsFavorisRepository = candidatsFavorisRepository;
	}

	// Lister les Candidats Favoris pour un Utilisateur
	public List<CandidatsFavoris> getAllCandidatsFavorisForUtilisateur(UUID idUtilisateur) {
		
		List<CandidatsFavoris> liste = candidatsFavorisRepository.findByIdUtilisateur(idUtilisateur);
		
		return liste;
	}
	
	// Vérifie si un Candidat existe dèja dans la liste des favoris d'un Utilisateur
	public boolean checkIfCandidatExistsDansFavorisUtilisateur(UUID idUtilisateur, UUID idCandidat) {
		
		if(candidatsFavorisRepository.findByIdUtilisateurAndIdCandidat(idUtilisateur, idCandidat) != null)
			return true;
		return false;
	}
	
	// Ajouter un Candidat Favoris à un Utilisateur
	public CandidatsFavoris addCandidatToFavorisToUtilisateur(CandidatsFavoris candidatsFavoris) {
		
		return candidatsFavorisRepository.save(candidatsFavoris);
	}

	// Supprimer un Candidat Favoris pour un Utilisateur
	public void deleteCandidatFromFavorisToUtilisateur(UUID idCandidatFavori) {
		
		candidatsFavorisRepository.deleteById(idCandidatFavori);
	}
}
