package com.odix.fr.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue(value="ROLE_ADMINISTRATEUR")
public class Administrateur extends Utilisateur {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3423459163514959894L;

	public Administrateur() {
		super();
	}
	
	

}
