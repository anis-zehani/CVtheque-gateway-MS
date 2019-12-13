package com.odix.fr.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.odix.fr.model.Entreprise;
import com.odix.fr.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements EntrepriseService{
	
	private final EntrepriseRepository entrepriseRepository;

	EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository) {
		super();
		this.entrepriseRepository = entrepriseRepository;
	}
	
	public List<Entreprise> getAllEntreprises(){
		return entrepriseRepository.findAll();
	}
	
	public Entreprise getEntreprise(UUID id){
		return entrepriseRepository.getOne(id);
	}
	
	//Ajouter une entreprise
	public Entreprise addEntreprise(Entreprise entreprise)
	{
		if(entrepriseRepository.findByNomEntreprise(entreprise.getNomEntreprise()) == null)
		{
			return entrepriseRepository.save(entreprise);
		}
		return null;
	}
	
	//Modifier une entreprise
	public Entreprise editEntreprise(Entreprise entreprise)
	{
		if(entrepriseRepository.existsById(entreprise.getIdEntreprise()))
		{
			return entrepriseRepository.save(entreprise);
		}
		return null;
	}
	
	//Supprimer une entreprise
	public boolean deleteEntreprise(UUID id)
	{
		if(entrepriseRepository.existsById(id))
		{
			try 
			{
				entrepriseRepository.deleteById(id);
				return true;
			}
			catch(Exception e) 
			{
				System.out.print("Erreur durant deleteEntreprise :"+e);
			}
		}
		return false;
	}
	
	// Statistiques : UPDATE le nombre des Candidats liés et des Partenaires liés à une Technologie
	@Override
	public void updateNombreCandidatsAndNombrePartenairesStats(UUID idEntreprise, Integer nombreCandidats, Integer nombrePartenaires) {
		
		entrepriseRepository.updateNombreCandidatsAndNombrePartenairesStats(idEntreprise, nombreCandidats, nombrePartenaires);
	}

	// Retourne la liste des 5 premières Entreprises ORDER BY le nombre des Candidats qu'il y a pour elle
	@Override
	public List<Entreprise> candidatsByEntreprise() {
		
		List<Entreprise> listeCandidatsByEntreprise = entrepriseRepository.candidatsByEntreprise();
		
		return listeCandidatsByEntreprise;
	}

	// Retourne la liste des 5 premières Entreprises ORDER BY le nombre des Partenaires qu'il y a pour elle
	@Override
	public List<Entreprise> partenairesByEntreprise() {
		
		List<Entreprise> listePartenairesByEntreprise = entrepriseRepository.partenairesByEntreprise();
		
		return listePartenairesByEntreprise;
	}

	// Retourne la somme des Candidats liés et des Partenaires liés pour toutes les Entreprises
	@Override
	public Map<String, Integer> sumCandiatsAndPartenairesByEntreprises() {
		
		List<Integer> listeSumsCandidats = entrepriseRepository.sumCandiatsByEntreprises();
		List<Integer> listeSumsPartenaires = entrepriseRepository.sumPartenairesByEntreprises();
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("sumCandidatsLies", listeSumsCandidats.get(0));
		map.put("sumPartenairesLies", listeSumsPartenaires.get(0));
		
		return map;
	}
	
	

}