package com.odix.fr.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.odix.fr.model.Entreprise;

public interface EntrepriseService {
	
	public List<Entreprise> getAllEntreprises();
	
	public Entreprise getEntreprise(UUID id);
	
	public Entreprise addEntreprise(Entreprise entreprise);
	
	public Entreprise editEntreprise(Entreprise entreprise);
	
	public boolean deleteEntreprise(UUID id);
	
	public void updateNombreCandidatsAndNombrePartenairesStats(UUID idTechnologie, Integer nombreCandidats, Integer nombrePartenaires);

	public List<Entreprise> candidatsByEntreprise();
	
	public List<Entreprise> partenairesByEntreprise();
	
	public Map<String, Integer> sumCandiatsAndPartenairesByEntreprises();

}
