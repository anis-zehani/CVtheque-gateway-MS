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
public class Certification implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1998921502004317093L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false, unique=true)
	private UUID id;

	@Version
	private int version;
	
    @NotEmpty(message="Odix - certification ne peut pas être vide")
    @Column(unique=true)
	private String nomCertification;
    
	@Column(length = 4096)
	private String descriptionDetaillee;
	
	// Pour regrouper les certifications par IdUtilisateur
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Utilisateur utilisateur;
    
    public Certification() {
		super();
	}

	public Certification(UUID id,
			@NotEmpty(message = "Odix - certification ne peut pas être vide") String nomCertification) {
		super();
		this.id = id;
		this.nomCertification = nomCertification;
	}

	public UUID getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getNomCertification() {
		return nomCertification;
	}

	public void setNomCertification(String nomCertification) {
		this.nomCertification = nomCertification;
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
