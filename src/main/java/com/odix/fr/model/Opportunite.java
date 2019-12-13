package com.odix.fr.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import lombok.Data;
	
@Data
@Entity
public class Opportunite implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3301405585667329930L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false, unique=true)
	private UUID id;

	@Version
	private int version;
	
	@Column
	private String titreOpportunite;
	
	@Column(length = 1024)
	private String descriptionOpportunite;

	@Column
	private LocalDate dateAjout;
	
	@Column
	private LocalDate dateDemarrageSouhaitee;
	
	@Column
	private String tjmOpportunite;
	
	@Column
	private String urlPhotoOpportunite;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Etat etatOpportunite;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Visibilite visibiliteOpportunite;
	
	//C'est le partenaire
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Utilisateur responsableOpportunite;
	
	//C'est la personne qui a inséré l'opportunité :  : qui a inséré cette opportunité, ça peut être un Administrateur ou Partenaire
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Utilisateur utilisateur;
	
	@ManyToMany
	@JoinTable(name = "opportunite_technologie",
	joinColumns = { @JoinColumn(name = "id_opportunite") },
	inverseJoinColumns = { @JoinColumn(name = "id_technologie") })
	private List<Technologie> listeTechnologies;
	
	@ManyToMany
	@JoinTable(name = "opportunite_certification",
	joinColumns = { @JoinColumn(name = "id_opportunite") },
	inverseJoinColumns = { @JoinColumn(name = "id_certification") })
	private List<Certification> listeCertifications;
	
	
	public Opportunite() {
		super();
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

	public String getTitreOpportunite() {
		return titreOpportunite;
	}

	public void setTitreOpportunite(String titreOpportunite) {
		this.titreOpportunite = titreOpportunite;
	}

	public String getDescriptionOpportunite() {
		return descriptionOpportunite;
	}

	public void setDescriptionOpportunite(String descriptionOpportunite) {
		this.descriptionOpportunite = descriptionOpportunite;
	}

	public LocalDate getDateAjout() {
		return dateAjout;
	}

	public void setDateAjout(LocalDate dateAjout) {
		this.dateAjout = dateAjout;
	}

	public LocalDate getDateDemarrageSouhaitee() {
		return dateDemarrageSouhaitee;
	}

	public void setDateDemarrageSouhaitee(LocalDate dateDemarrageSouhaitee) {
		this.dateDemarrageSouhaitee = dateDemarrageSouhaitee;
	}

	public String getTjmOpportunite() {
		return tjmOpportunite;
	}

	public void setTjmOpportunite(String tjmOpportunite) {
		this.tjmOpportunite = tjmOpportunite;
	}

	public String getUrlPhotoOpportunite() {
		return urlPhotoOpportunite;
	}

	public void setUrlPhotoOpportunite(String urlPhotoOpportunite) {
		this.urlPhotoOpportunite = urlPhotoOpportunite;
	}

	public Etat getEtatOpportunite() {
		return etatOpportunite;
	}

	public void setEtatOpportunite(Etat etatOpportunite) {
		this.etatOpportunite = etatOpportunite;
	}

	public Visibilite getVisibiliteOpportunite() {
		return visibiliteOpportunite;
	}

	public void setVisibiliteOpportunite(Visibilite visibiliteOpportunite) {
		this.visibiliteOpportunite = visibiliteOpportunite;
	}

	public Utilisateur getResponsableOpportunite() {
		return responsableOpportunite;
	}

	public void setResponsableOpportunite(Utilisateur responsableOpportunite) {
		this.responsableOpportunite = responsableOpportunite;
	}
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Technologie> getListeTechnologies() {
		return listeTechnologies;
	}

	public void setListeTechnologies(List<Technologie> listeTechnologies) {
		this.listeTechnologies = listeTechnologies;
	}

	public List<Certification> getListeCertifications() {
		return listeCertifications;
	}

	public void setListeCertifications(List<Certification> listeCertifications) {
		this.listeCertifications = listeCertifications;
	}
}
