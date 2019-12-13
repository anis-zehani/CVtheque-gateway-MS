package com.odix.fr.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class Ecole implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -858562105980175964L;

	@Id
	@GeneratedValue
	@Column(name = "idEcole", updatable = false, nullable = false, unique=true)
	private UUID idEcole;

	@Version
	private int version;
	
    @NotEmpty(message="Odix - école ne peut pas être vide")
    @Column(unique=true)
	private String nomEcole;
    
	@Column(length = 4096)
	private String descriptionDetaillee;
    
	// Pour regrouper les écoles par IdUtilisateur : qui a inséré cette école
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Utilisateur utilisateur;

    public Ecole() {
		super();
	}

    public Ecole(UUID idEcole, String nomEcole) {
		super();
		this.idEcole = idEcole;
		this.nomEcole = nomEcole;
	}

	public UUID getIdEcole() {
		return idEcole;
	}

	public int getVersion() {
		return version;
	}

	public void setIdEcole(UUID idEcole) {
		this.idEcole = idEcole;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getNomEcole() {
		return nomEcole;
	}

	public void setNomEcole(String nomEcole) {
		this.nomEcole = nomEcole;
	}
	
	public String getDescriptionDetaillee() {
		return descriptionDetaillee;
	}

	public void setDescriptionDetaillee(String descriptionDetaillee) {
		this.descriptionDetaillee = descriptionDetaillee;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	} 
}
