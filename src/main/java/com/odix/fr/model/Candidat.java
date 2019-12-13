package com.odix.fr.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;
	
@Data
@Entity
@DiscriminatorValue(value="ROLE_CANDIDAT")
public class Candidat extends Utilisateur implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1004341819482868284L;

	/**Les champs du candidat, hérités de la classe utilisateur :
	 * 
	 * id
	 * identite
	 * telephone
	 * email
	 * poste_occupe
	 * description_detaillee
	 * urlPhoto
	 * entreprise : @ManyToOne
	 * 
	 ***************
	 */

	@Column
	private String idLinkedin;
	
	@Column
	private LocalDate dateDeNaissance;
	
	@Column
	private String adresse;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private SituationFamiliale situationFamiliale;
	
	@Column
	private String nombreEnfants;
	
	@Column
	private String salaireActuel;
	
	@Column
	private String pretentionSalariale;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Note niveauEnFrancais;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Note niveauEnAnglais;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Note noteGlobale;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Disponibilite disponibilite;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Etat etatCandidat;
	
	@Column
	private Date dateDemarrageCarriere;
	
	@Column
	private Date dateEpuisementPasseport;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional=true)
	private Diplome diplome;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional=true)
	private Visa visa;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional=true)
	private Curriculum curriculum;
	
	// Pour regrouper les candidats par IdUtilisateur : qui a inséré ce candidat (pour le moment c'est l'Administrateur)
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Utilisateur utilisateur;
	
	@ManyToMany
	@JoinTable(name = "candidat_opportunite",
	joinColumns = { @JoinColumn(name = "id_candidat") },
	inverseJoinColumns = { @JoinColumn(name = "id_opportunite") })
	private List<Opportunite> listeOpportunites;
	
	@ManyToMany
	@JoinTable(name = "candidat_technologie",
	joinColumns = { @JoinColumn(name = "id_candidat") },
	inverseJoinColumns = { @JoinColumn(name = "id_technologie") })
	private List<Technologie> listeTechnologies;
	
	@ManyToMany
	@JoinTable(name = "candidat_certification",
	joinColumns = { @JoinColumn(name = "id_candidat") },
	inverseJoinColumns = { @JoinColumn(name = "id_certification") })
	private List<Certification> listeCertifications;
	
	/*Paramètres AutoFill : le candidat remplira ça tout seul via son espace candidat*/
	
    @Column
	private String telephoneAutoFill;
    
    @Column
	private String emailAutoFill;
    
	@Column
	private String posteOccupeAutoFill;
	
	@Column
	private String salaireActuelAutoFill;
	
	@Column
	private String pretentionSalarialeAutoFill;
	
	@Column
	private LocalDate dateDeNaissanceAutoFill;
	
	@Column
	private String entrepriseAutoFill;
	
	@Column(length = 1024)
	private String urlPhotoAutoFill;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Disponibilite disponibiliteAutoFill;
	
	@Column
	private LocalDate dateDemarrageCarriereAutoFill;
	
	@Column
	private LocalDate dateEpuisementPasseportAutoFill;
	
	@Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private SituationFamiliale situationFamilialeAutoFill;
	
	@Column
	private String nombreEnfantsAutoFill;
	
	@Column
	private String adresseAutoFill;
	
	@Column(length = 4096)
	private String descriptionDetailleeAutoFill;
	
	
	public Candidat() {
		super();
	}

	public String getIdLinkedin() {
		return idLinkedin;
	}

	public void setIdLinkedin(String idLinkedin) {
		this.idLinkedin = idLinkedin;
	}

	public LocalDate getDateDeNaissance() {
		return dateDeNaissance;
	}

	public void setDateDeNaissance(LocalDate dateDeNaissance) {
		this.dateDeNaissance = dateDeNaissance;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public SituationFamiliale getSituationFamiliale() {
		return situationFamiliale;
	}

	public void setSituationFamiliale(SituationFamiliale situationFamiliale) {
		this.situationFamiliale = situationFamiliale;
	}

	public String getNombreEnfants() {
		return nombreEnfants;
	}

	public void setNombreEnfants(String nombreEnfants) {
		this.nombreEnfants = nombreEnfants;
	}

	public String getSalaireActuel() {
		return salaireActuel;
	}

	public void setSalaireActuel(String salaireActuel) {
		this.salaireActuel = salaireActuel;
	}

	public String getPretentionSalariale() {
		return pretentionSalariale;
	}

	public void setPretentionSalariale(String pretentionSalariale) {
		this.pretentionSalariale = pretentionSalariale;
	}

	public Note getNiveauEnFrancais() {
		return niveauEnFrancais;
	}

	public void setNiveauEnFrancais(Note niveauEnFrancais) {
		this.niveauEnFrancais = niveauEnFrancais;
	}

	public Note getNiveauEnAnglais() {
		return niveauEnAnglais;
	}

	public void setNiveauEnAnglais(Note niveauEnAnglais) {
		this.niveauEnAnglais = niveauEnAnglais;
	}

	public Note getNoteGlobale() {
		return noteGlobale;
	}

	public void setNoteGlobale(Note noteGlobale) {
		this.noteGlobale = noteGlobale;
	}

	public Disponibilite getDisponibilite() {
		return disponibilite;
	}

	public void setDisponibilite(Disponibilite disponibilite) {
		this.disponibilite = disponibilite;
	}

	public Etat getEtatCandidat() {
		return etatCandidat;
	}

	public void setEtatCandidat(Etat etatCandidat) {
		this.etatCandidat = etatCandidat;
	}

	public Date getDateDemarrageCarriere() {
		return dateDemarrageCarriere;
	}

	public void setDateDemarrageCarriere(Date dateDemarrageCarriere) {
		this.dateDemarrageCarriere = dateDemarrageCarriere;
	}

	public Date getDateEpuisementPasseport() {
		return dateEpuisementPasseport;
	}

	public void setDateEpuisementPasseport(Date dateEpuisementPasseport) {
		this.dateEpuisementPasseport = dateEpuisementPasseport;
	}
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public Diplome getDiplome() {
		return diplome;
	}

	public void setDiplome(Diplome diplome) {
		this.diplome = diplome;
	}

	public Visa getVisa() {
		return visa;
	}

	public void setVisa(Visa visa) {
		this.visa = visa;
	}

	public Curriculum getCurriculum() {
		return curriculum;
	}

	public void setCurriculum(Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public List<Opportunite> getListeOpportunites() {
		return listeOpportunites;
	}

	public void setListeOpportunites(List<Opportunite> listeOpportunites) {
		this.listeOpportunites = listeOpportunites;
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

	public String getTelephoneAutoFill() {
		return telephoneAutoFill;
	}

	public String getEmailAutoFill() {
		return emailAutoFill;
	}

	public String getPosteOccupeAutoFill() {
		return posteOccupeAutoFill;
	}

	public String getSalaireActuelAutoFill() {
		return salaireActuelAutoFill;
	}

	public String getPretentionSalarialeAutoFill() {
		return pretentionSalarialeAutoFill;
	}

	public String getEntrepriseAutoFill() {
		return entrepriseAutoFill;
	}

	public String getUrlPhotoAutoFill() {
		return urlPhotoAutoFill;
	}

	public Disponibilite getDisponibiliteAutoFill() {
		return disponibiliteAutoFill;
	}

	public LocalDate getDateDemarrageCarriereAutoFill() {
		return dateDemarrageCarriereAutoFill;
	}

	public LocalDate getDateEpuisementPasseportAutoFill() {
		return dateEpuisementPasseportAutoFill;
	}

	public SituationFamiliale getSituationFamilialeAutoFill() {
		return situationFamilialeAutoFill;
	}

	public String getNombreEnfantsAutoFill() {
		return nombreEnfantsAutoFill;
	}

	public String getAdresseAutoFill() {
		return adresseAutoFill;
	}

	public String getDescriptionDetailleeAutoFill() {
		return descriptionDetailleeAutoFill;
	}

	public void setTelephoneAutoFill(String telephoneAutoFill) {
		this.telephoneAutoFill = telephoneAutoFill;
	}

	public void setEmailAutoFill(String emailAutoFill) {
		this.emailAutoFill = emailAutoFill;
	}

	public void setPosteOccupeAutoFill(String posteOccupeAutoFill) {
		this.posteOccupeAutoFill = posteOccupeAutoFill;
	}

	public void setSalaireActuelAutoFill(String salaireActuelAutoFill) {
		this.salaireActuelAutoFill = salaireActuelAutoFill;
	}

	public void setPretentionSalarialeAutoFill(String pretentionSalarialeAutoFill) {
		this.pretentionSalarialeAutoFill = pretentionSalarialeAutoFill;
	}

	public LocalDate getDateDeNaissanceAutoFill() {
		return dateDeNaissanceAutoFill;
	}

	public void setDateDeNaissanceAutoFill(LocalDate dateDeNaissanceAutoFill) {
		this.dateDeNaissanceAutoFill = dateDeNaissanceAutoFill;
	}

	public void setEntrepriseAutoFill(String entrepriseAutoFill) {
		this.entrepriseAutoFill = entrepriseAutoFill;
	}

	public void setUrlPhotoAutoFill(String urlPhotoAutoFill) {
		this.urlPhotoAutoFill = urlPhotoAutoFill;
	}

	public void setDisponibiliteAutoFill(Disponibilite disponibiliteAutoFill) {
		this.disponibiliteAutoFill = disponibiliteAutoFill;
	}

	public void setDateDemarrageCarriereAutoFill(LocalDate dateDemarrageCarriereAutoFill) {
		this.dateDemarrageCarriereAutoFill = dateDemarrageCarriereAutoFill;
	}

	public void setDateEpuisementPasseportAutoFill(LocalDate dateEpuisementPasseportAutoFill) {
		this.dateEpuisementPasseportAutoFill = dateEpuisementPasseportAutoFill;
	}

	public void setSituationFamilialeAutoFill(SituationFamiliale situationFamilialeAutoFill) {
		this.situationFamilialeAutoFill = situationFamilialeAutoFill;
	}

	public void setNombreEnfantsAutoFill(String nombreEnfantsAutoFill) {
		this.nombreEnfantsAutoFill = nombreEnfantsAutoFill;
	}

	public void setAdresseAutoFill(String adresseAutoFill) {
		this.adresseAutoFill = adresseAutoFill;
	}

	public void setDescriptionDetailleeAutoFill(String descriptionDetailleeAutoFill) {
		this.descriptionDetailleeAutoFill = descriptionDetailleeAutoFill;
	}
}
