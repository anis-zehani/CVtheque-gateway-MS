package com.odix.fr.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue(value="ROLE_CONTACT")
public class Contact extends Utilisateur {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2678206991959419554L;
	
	/**Les champs remplis pour le contact sont uniquement :
	 * 
	 * Identite
	 * telephone
	 * email
	 * poste_occupe
	 * description_detaillee
	 * urlPhoto
	 * entreprise : @ManyToOne
	 * utilisateur : @ManyToOne
	 */
	
	// Pour regrouper les contacts par IdUtilisateur : qui a inséré ce contact
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Utilisateur utilisateur;

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
	public Contact() {
		super();
	}
}
