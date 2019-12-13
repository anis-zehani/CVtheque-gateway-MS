package com.odix.fr.service;

import java.util.List;
import java.util.UUID;

import com.odix.fr.model.Partenaire;

public interface PartenaireService {
	
	public List<Partenaire> getAllPartenaires(String etat);
	
	public List<Partenaire> getAllPartenairesByEntreprise(UUID idEntreprise);
	
	public Partenaire getPartenaire(UUID id);
	
	public Partenaire addPartenaire(Partenaire partenaire);
	
	public Partenaire editPartenaire(Partenaire partenaire);
	
	public Partenaire editPartenaireAutoFill(Partenaire partenaire);
	
	public Partenaire editEtatPartenaire(Partenaire partenaire);
	
	public void updateLinkPartenaireEntreprise(UUID idPartenaire);
	
	public Boolean deletePartenaire(UUID id);

	public Partenaire addPhotoToPartenaire(UUID id, String urlPhoto);
	
	public Partenaire addPhotoToPartenaireAutoFill(UUID id, String urlPhoto);

}
