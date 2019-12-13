package com.odix.fr.service;

import java.util.List;
import java.util.UUID;

import com.odix.fr.model.PartenaireTemporaire;

public interface PartenaireTemporaireService {
	
	public List<PartenaireTemporaire> getAllPartenairesTemporaires();

	public PartenaireTemporaire addPartenaireTemporaire(PartenaireTemporaire partenaireTemporaire);
	
	public Boolean activatePartenaireTemporaire(String email, UUID idEntreprise);
}
