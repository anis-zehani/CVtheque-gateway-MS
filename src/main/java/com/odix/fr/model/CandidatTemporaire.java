package com.odix.fr.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
public class CandidatTemporaire  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4470340248430361949L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false, unique=true)
	private UUID id;

	@Version
	private int version;
	
	@Column
	private String identite;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
    @Column
	private String email;
    
	@Column
	private LocalDateTime dateAjout;

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

	public String getIdentite() {
		return identite;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getDateAjout() {
		return dateAjout;
	}

	public void setIdentite(String identite) {
		this.identite = identite;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDateAjout(LocalDateTime dateAjout) {
		this.dateAjout = dateAjout;
	}

}
