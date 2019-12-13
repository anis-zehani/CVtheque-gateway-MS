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
public class Technologie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3422261716301379660L;
	
	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false, unique=true)
	private UUID id;

	@Version
	private int version;
	
    @NotEmpty(message="Odix - technologie ne peut pas être vide")
    @Column(unique=true)
    private String nomTechnologie;
    
	@Column(length = 4096)
	private String descriptionDetaillee;
	
	@Column
	private Integer statNombreCandidatsLies;
	
	@Column
	private Integer statNombreOpportunitesLiees;
	
	// Pour regrouper les technologies par IdUtilisateur : qui a inséré cette technologie
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Utilisateur utilisateur;
    
	public Technologie() {
		super();
	}
	
	public Technologie(UUID id, String nomTechnologie) {
		super();
		this.id = id;
		this.nomTechnologie = nomTechnologie;
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

	public String getNomTechnologie() {
		return nomTechnologie;
	}

	public void setNomTechnologie(String nomTechnologie) {
		this.nomTechnologie = nomTechnologie;
	}

	public String getDescriptionDetaillee() {
		return descriptionDetaillee;
	}

	public void setDescriptionDetaillee(String descriptionDetaillee) {
		this.descriptionDetaillee = descriptionDetaillee;
	}

	public Integer getStatNombreCandidatsLies() {
		return statNombreCandidatsLies;
	}

	public void setStatNombreCandidatsLies(Integer statNombreCandidatsLies) {
		this.statNombreCandidatsLies = statNombreCandidatsLies;
	}

	public Integer getStatNombreOpportunitesLiees() {
		return statNombreOpportunitesLiees;
	}

	public void setStatNombreOpportunitesLiees(Integer statNombreOpportunitesLiees) {
		this.statNombreOpportunitesLiees = statNombreOpportunitesLiees;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	
}
