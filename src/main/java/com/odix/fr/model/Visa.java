package com.odix.fr.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
public class Visa implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3545362140070255221L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false, unique=true)
	private UUID id;

	@Version
	private int version;
	
	@Column
	@Enumerated(EnumType.STRING)
	private TypeVisa typeVisa;
	
	@Column
	private LocalDate dateDebutVisa;
	
	@Column
	private LocalDate dateFinVisa;
	
	/*Paramètres AutoFill : le candidat remplira ça tout seul via son espace candidat*/
	
	@Column
	@Enumerated(EnumType.STRING)
	private TypeVisa typeVisaAutoFill;
	
	@Column
	private LocalDate dateDebutVisaAutoFill;
	
	@Column
	private LocalDate dateFinVisaAutoFill;

	public Visa() {
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

	public TypeVisa getTypeVisa() {
		return typeVisa;
	}

	public void setTypeVisa(TypeVisa typeVisa) {
		this.typeVisa = typeVisa;
	}

	public LocalDate getDateDebutVisa() {
		return dateDebutVisa;
	}

	public void setDateDebutVisa(LocalDate dateDebutVisa) {
		this.dateDebutVisa = dateDebutVisa;
	}

	public LocalDate getDateFinVisa() {
		return dateFinVisa;
	}

	public void setDateFinVisa(LocalDate dateFinVisa) {
		this.dateFinVisa = dateFinVisa;
	}

	public TypeVisa getTypeVisaAutoFill() {
		return typeVisaAutoFill;
	}

	public void setTypeVisaAutoFill(TypeVisa typeVisaAutoFill) {
		this.typeVisaAutoFill = typeVisaAutoFill;
	}

	public LocalDate getDateDebutVisaAutoFill() {
		return dateDebutVisaAutoFill;
	}

	public void setDateDebutVisaAutoFill(LocalDate dateDebutVisaAutoFill) {
		this.dateDebutVisaAutoFill = dateDebutVisaAutoFill;
	}

	public LocalDate getDateFinVisaAutoFill() {
		return dateFinVisaAutoFill;
	}

	public void setDateFinVisaAutoFill(LocalDate dateFinVisaAutoFill) {
		this.dateFinVisaAutoFill = dateFinVisaAutoFill;
	}
}
