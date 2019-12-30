package com.odix.fr.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class PartenaireTemporaire implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2655886717769992583L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false, unique=true)
	private UUID id;
	
	@Column
	private String identite;
	
	@Column
	private String telephone;
	
    @Column
	private String email;
    
	@Column
	private String username;
	
	@Column
	private String password;

	@Column
	private String entreprise;
	
	@Column
	private String posteOccupe;
	
	@Column(length = 1024)
	private String descriptionDetaillee;
    
	@Column
	private LocalDateTime dateAjout;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getIdentite() {
		return identite;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEntreprise() {
		return entreprise;
	}

	public String getPosteOccupe() {
		return posteOccupe;
	}

	public LocalDateTime getDateAjout() {
		return dateAjout;
	}

	public void setIdentite(String identite) {
		this.identite = identite;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}

	public void setPosteOccupe(String posteOccupe) {
		this.posteOccupe = posteOccupe;
	}

	public void setDateAjout(LocalDateTime dateAjout) {
		this.dateAjout = dateAjout;
	}

	public String getDescriptionDetaillee() {
		return descriptionDetaillee;
	}

	public void setDescriptionDetaillee(String descriptionDetaillee) {
		this.descriptionDetaillee = descriptionDetaillee;
	}
}
