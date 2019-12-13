package com.odix.fr.service;

import com.odix.fr.model.CandidatTemporaire;

public interface CandidatTemporaireService {

	public CandidatTemporaire addCandidatTemporaire(CandidatTemporaire candidatTemporaire);
	
	public Boolean envoiEmailActivationCompteCandidat(String email);
	
	public Boolean activationCompteCandidatTemporaire(String email);
}
