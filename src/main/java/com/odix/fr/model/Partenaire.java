package com.odix.fr.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
@DiscriminatorValue(value="ROLE_PARTENAIRE")
public class Partenaire extends Utilisateur implements Serializable {
	
	private static final long serialVersionUID = -2652463772687522895L;

	/**Les champs remplis pour le partenaire sont uniquement :
	 * 
	 * Identite
	 * telephone
	 * email
	 * poste_occupe
	 * description_detaillee
	 * urlPhoto
	 * login
	 * password
	 * entreprise : @ManyToOne
	 * etatPartenaire : Actif/Inactif
	 * utilisateur : @ManyToOne
	 */
	
	@Column
	@Enumerated(EnumType.STRING)
	private Etat etatPartenaire;
	
	// Pour regrouper les partenaires par IdUtilisateur : qui a inséré ce partenaire (pour le moment c'est l'Administrateur)
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Utilisateur utilisateur;
	
	/*Paramètres AutoFill : le partenaire remplira ça tout seul via son espace partenaire*/
	
    @Column
	private String emailPartenaireAutoFill;
    
    @Column
	private String telephonePartenaireAutoFill;
    
    @Column
	private String entrepriseActuellePartenaireAutoFill;
    
	@Column
	private String posteOccupePartenaireAutoFill;
	
    @Column
	private String telephoneEntreprisePartenaireAutoFill;
    
    @Column
	private String effectifEntreprisePartenaireAutoFill;
    
    @Column
	private String siteInternetEntreprisePartenaireAutoFill;
    
    @Column
	private String adresseEntreprisePartenaireAutoFill;
	
	@Column(length = 1024)
	private String urlPhotoPartenaireAutoFill;
	
	@Column(length = 4096)
	private String descriptionDetailleePartenaireAutoFill;
	
	
	public Partenaire() {
		super();
	}

	public Etat getEtatPartenaire() {
		return etatPartenaire;
	}

	public void setEtatPartenaire(Etat etatPartenaire) {
		this.etatPartenaire = etatPartenaire;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getTelephonePartenaireAutoFill() {
		return telephonePartenaireAutoFill;
	}

	public String getEmailPartenaireAutoFill() {
		return emailPartenaireAutoFill;
	}

	public String getPosteOccupePartenaireAutoFill() {
		return posteOccupePartenaireAutoFill;
	}

	public String getUrlPhotoPartenaireAutoFill() {
		return urlPhotoPartenaireAutoFill;
	}

	public String getDescriptionDetailleePartenaireAutoFill() {
		return descriptionDetailleePartenaireAutoFill;
	}

	public void setTelephonePartenaireAutoFill(String telephonePartenaireAutoFill) {
		this.telephonePartenaireAutoFill = telephonePartenaireAutoFill;
	}

	public void setEmailPartenaireAutoFill(String emailPartenaireAutoFill) {
		this.emailPartenaireAutoFill = emailPartenaireAutoFill;
	}

	public void setPosteOccupePartenaireAutoFill(String posteOccupePartenaireAutoFill) {
		this.posteOccupePartenaireAutoFill = posteOccupePartenaireAutoFill;
	}

	public void setUrlPhotoPartenaireAutoFill(String urlPhotoPartenaireAutoFill) {
		this.urlPhotoPartenaireAutoFill = urlPhotoPartenaireAutoFill;
	}

	public void setDescriptionDetailleePartenaireAutoFill(String descriptionDetailleePartenaireAutoFill) {
		this.descriptionDetailleePartenaireAutoFill = descriptionDetailleePartenaireAutoFill;
	}

	public String getEntrepriseActuellePartenaireAutoFill() {
		return entrepriseActuellePartenaireAutoFill;
	}

	public void setEntrepriseActuellePartenaireAutoFill(String entrepriseActuellePartenaireAutoFill) {
		this.entrepriseActuellePartenaireAutoFill = entrepriseActuellePartenaireAutoFill;
	}

	public String getTelephoneEntreprisePartenaireAutoFill() {
		return telephoneEntreprisePartenaireAutoFill;
	}

	public String getEffectifEntreprisePartenaireAutoFill() {
		return effectifEntreprisePartenaireAutoFill;
	}

	public String getSiteInternetEntreprisePartenaireAutoFill() {
		return siteInternetEntreprisePartenaireAutoFill;
	}

	public String getAdresseEntreprisePartenaireAutoFill() {
		return adresseEntreprisePartenaireAutoFill;
	}

	public void setTelephoneEntreprisePartenaireAutoFill(String telephoneEntreprisePartenaireAutoFill) {
		this.telephoneEntreprisePartenaireAutoFill = telephoneEntreprisePartenaireAutoFill;
	}

	public void setEffectifEntreprisePartenaireAutoFill(String effectifEntreprisePartenaireAutoFill) {
		this.effectifEntreprisePartenaireAutoFill = effectifEntreprisePartenaireAutoFill;
	}

	public void setSiteInternetEntreprisePartenaireAutoFill(String siteInternetEntreprisePartenaireAutoFill) {
		this.siteInternetEntreprisePartenaireAutoFill = siteInternetEntreprisePartenaireAutoFill;
	}

	public void setAdresseEntreprisePartenaireAutoFill(String adresseEntreprisePartenaireAutoFill) {
		this.adresseEntreprisePartenaireAutoFill = adresseEntreprisePartenaireAutoFill;
	}
}
