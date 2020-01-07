package com.odix.fr.model;

import java.io.Serializable;
import java.util.List;

public class POJONotification implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2115001054422981896L;
	
	String objetNotification;
	String corpsNotification;
	List<Utilisateur> listeDestinatairesNotification; 
	Utilisateur generateurNotification;
	Utilisateur candidatNotification;
	Utilisateur partenaireNotification;
	Opportunite opportuniteNotification;
	
	
	public POJONotification(String objetNotification, String corpsNotification,
			List<Utilisateur> listeDestinatairesNotification, Utilisateur generateurNotification,
			Utilisateur candidatNotification, Utilisateur partenaireNotification, Opportunite opportuniteNotification) {
		super();
		this.objetNotification = objetNotification;
		this.corpsNotification = corpsNotification;
		this.listeDestinatairesNotification = listeDestinatairesNotification;
		this.generateurNotification = generateurNotification;
		this.candidatNotification = candidatNotification;
		this.partenaireNotification = partenaireNotification;
		this.opportuniteNotification = opportuniteNotification;
	}


	public String getObjetNotification() {
		return objetNotification;
	}


	public String getCorpsNotification() {
		return corpsNotification;
	}


	public List<Utilisateur> getListeDestinatairesNotification() {
		return listeDestinatairesNotification;
	}


	public Utilisateur getGenerateurNotification() {
		return generateurNotification;
	}


	public Utilisateur getCandidatNotification() {
		return candidatNotification;
	}


	public Utilisateur getPartenaireNotification() {
		return partenaireNotification;
	}


	public Opportunite getOpportuniteNotification() {
		return opportuniteNotification;
	}


	public void setObjetNotification(String objetNotification) {
		this.objetNotification = objetNotification;
	}


	public void setCorpsNotification(String corpsNotification) {
		this.corpsNotification = corpsNotification;
	}


	public void setListeDestinatairesNotification(List<Utilisateur> listeDestinatairesNotification) {
		this.listeDestinatairesNotification = listeDestinatairesNotification;
	}


	public void setGenerateurNotification(Utilisateur generateurNotification) {
		this.generateurNotification = generateurNotification;
	}


	public void setCandidatNotification(Utilisateur candidatNotification) {
		this.candidatNotification = candidatNotification;
	}


	public void setPartenaireNotification(Utilisateur partenaireNotification) {
		this.partenaireNotification = partenaireNotification;
	}


	public void setOpportuniteNotification(Opportunite opportuniteNotification) {
		this.opportuniteNotification = opportuniteNotification;
	}

}
