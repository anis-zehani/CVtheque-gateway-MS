package com.odix.fr.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue(value="ROLE_COLLABORATEUR")
public class Collaborateur extends Utilisateur {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6308380666436324353L;
	/**Les champs remplis pour le collaborateur sont uniquement :
	 * 
	 * Identite
	 * email
	 * login
	 * password
	 * utilisateur : @ManyToOne
	 */
	
	// Pour regrouper les collaborateurs par IdUtilisateur
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Utilisateur utilisateur;

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public Collaborateur() {
		super();
	}	
}
