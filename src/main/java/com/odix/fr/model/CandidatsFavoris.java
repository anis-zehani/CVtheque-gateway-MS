package com.odix.fr.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
public class CandidatsFavoris implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6140276321360588560L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false, unique=true)
	private UUID id;

	@Version
	private int version;
	
	@Column
	private UUID idUtilisateur;
	
	@Column
	private UUID idCandidat;
	
	@Column
	private String identiteCandidat;

	
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

	public UUID getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(UUID idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

	public UUID getIdCandidat() {
		return idCandidat;
	}

	public void setIdCandidat(UUID idCandidat) {
		this.idCandidat = idCandidat;
	}

	public String getIdentiteCandidat() {
		return identiteCandidat;
	}

	public void setIdentiteCandidat(String identiteCandidat) {
		this.identiteCandidat = identiteCandidat;
	}
}
