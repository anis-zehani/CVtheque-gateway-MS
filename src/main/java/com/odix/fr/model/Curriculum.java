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
public class Curriculum implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3125388939469501605L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false, unique=true)
	private UUID id;

	@Version
	private int version;
	
	@Column
	private String urlCvOriginal;
	
	@Column
	private String urlCvOdix;
	
	@Column
	private String urlCvOriginalAutoFill;

	public Curriculum() {
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


	public String getUrlCvOriginal() {
		return urlCvOriginal;
	}

	public void setUrlCvOriginal(String urlCvOriginal) {
		this.urlCvOriginal = urlCvOriginal;
	}

	public String getUrlCvOdix() {
		return urlCvOdix;
	}

	public void setUrlCvOdix(String urlCvOdix) {
		this.urlCvOdix = urlCvOdix;
	}

	public String getUrlCvOriginalAutoFill() {
		return urlCvOriginalAutoFill;
	}

	public void setUrlCvOriginalAutoFill(String urlCvOriginalAutoFill) {
		this.urlCvOriginalAutoFill = urlCvOriginalAutoFill;
	}
}
