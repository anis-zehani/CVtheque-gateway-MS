package com.odix.fr.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
public class Notification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1938031921001982088L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false, unique=true)
	private UUID id;

	@Version
	private int version;
	
	@ManyToMany
	@JoinTable(name = "notification_destinataire",
	joinColumns = { @JoinColumn(name = "id_notification") },
	inverseJoinColumns = { @JoinColumn(name = "id_destinataire") })
	private List<Utilisateur> listeDestinatairesNotification;
	
	@Column
	private Utilisateur generateurNotification;
	
	@Column
	private String objetNotification;
	
	@Column(length = 1024)
	private String corpstNotification;
	
	@Column
	private LocalDateTime dateAjout;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Etat etatNotification;
	
	/*Paramètres d'informations supplémantaires pour l'affichage de la Pop-Up Notification*/
	@Column
	private Candidat candidatNotification;
	
	@Column
	private PartenaireTemporaire partenaireTemporaireNotification;
	
	@Column
	private Opportunite opportuniteNotification;

	
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

	public String getObjetNotification() {
		return objetNotification;
	}

	public String getCorpstNotification() {
		return corpstNotification;
	}

	public LocalDateTime getDateAjout() {
		return dateAjout;
	}
	
	public void setObjetNotification(String objetNotification) {
		this.objetNotification = objetNotification;
	}

	public void setCorpstNotification(String corpstNotification) {
		this.corpstNotification = corpstNotification;
	}

	public void setDateAjout(LocalDateTime dateAjout) {
		this.dateAjout = dateAjout;
	}

	public List<Utilisateur> getListeDestinatairesNotification() {
		return listeDestinatairesNotification;
	}

	public Utilisateur getGenerateurNotification() {
		return generateurNotification;
	}

	public void setListeDestinatairesNotification(List<Utilisateur> listeDestinatairesNotification) {
		this.listeDestinatairesNotification = listeDestinatairesNotification;
	}

	public void setGenerateurNotification(Utilisateur generateurNotification) {
		this.generateurNotification = generateurNotification;
	}

	public Etat getEtatNotification() {
		return etatNotification;
	}

	public void setEtatNotification(Etat etatNotification) {
		this.etatNotification = etatNotification;
	}

	public Candidat getCandidatNotification() {
		return candidatNotification;
	}

	public Opportunite getOpportuniteNotification() {
		return opportuniteNotification;
	}

	public void setCandidatNotification(Candidat candidatNotification) {
		this.candidatNotification = candidatNotification;
	}

	public void setOpportuniteNotification(Opportunite opportuniteNotification) {
		this.opportuniteNotification = opportuniteNotification;
	}

	public PartenaireTemporaire getPartenaireTemporaireNotification() {
		return partenaireTemporaireNotification;
	}

	public void setPartenaireTemporaireNotification(PartenaireTemporaire partenaireTemporaireNotification) {
		this.partenaireTemporaireNotification = partenaireTemporaireNotification;
	}
	
}
