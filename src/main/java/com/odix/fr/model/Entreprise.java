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
public class Entreprise implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 621044971711896472L;

	@Id
	@GeneratedValue
	@Column(name = "idEntreprise", updatable = false, nullable = false, unique=true)
	private UUID idEntreprise;

	@Version
	private int version;
	
	@NotEmpty(message="Odix - entreprise ne peut pas être vide")
    @Column(unique=true)
	private String nomEntreprise;
	
	@Column(length = 4096)
	private String descriptionDetaillee;
	
	@Column
	private Integer statNombreCandidatsLies;
	
	@Column
	private Integer statNombrePartenairesLies;
	
	// Pour regrouper les entreprises par IdUtilisateur : qui a inséré cette entreprise 
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Utilisateur utilisateur;

	public Entreprise() {
		super();
	}

	public Entreprise(UUID idEntreprise, String nomEntreprise) {
		super();
		this.idEntreprise = idEntreprise;
		this.nomEntreprise = nomEntreprise;
	}

	public UUID getIdEntreprise() {
		return idEntreprise;
	}

	public int getVersion() {
		return version;
	}

	public void setIdEntreprise(UUID idEntreprise) {
		this.idEntreprise = idEntreprise;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getNomEntreprise() {
		return nomEntreprise;
	}

	public void setNomEntreprise(String nomEntreprise) {
		this.nomEntreprise = nomEntreprise;
	}

	public Integer getStatNombreCandidatsLies() {
		return statNombreCandidatsLies;
	}

	public void setStatNombreCandidatsLies(Integer statNombreCandidatsLies) {
		this.statNombreCandidatsLies = statNombreCandidatsLies;
	}

	public Integer getStatNombrePartenairesLies() {
		return statNombrePartenairesLies;
	}

	public void setStatNombrePartenairesLies(Integer statNombrePartenairesLies) {
		this.statNombrePartenairesLies = statNombrePartenairesLies;
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
