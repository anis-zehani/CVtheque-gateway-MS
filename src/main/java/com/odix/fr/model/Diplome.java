package com.odix.fr.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import lombok.Data;

@Data
@Entity
public class Diplome implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7405930119584792399L;

	@Id
	@GeneratedValue
	@Column(name = "id", updatable = false, nullable = false, unique=true)
	private UUID id;

	@Version
	private int version;
	
    @Column
	@Enumerated(EnumType.STRING)
	private TypeDiplome typeDiplome;

    @Column
	private LocalDate dateObtentionDiplome;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
	Ecole ecole;
    
    /*Paramètres AutoFill : le candidat remplira ça tout seul via son espace candidat*/
    
    @Column
	@Enumerated(EnumType.STRING)
	private TypeDiplome typeDiplomeAutoFill;
    
    @Column
	private LocalDate dateObtentionDiplomeAutoFill;
    
    @Column
    private String ecoleAutoFill;

	public Diplome() {
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

	public TypeDiplome getTypeDiplome() {
		return typeDiplome;
	}

	public void setTypeDiplome(TypeDiplome typeDiplome) {
		this.typeDiplome = typeDiplome;
	}

	public LocalDate getDateObtentionDiplome() {
		return dateObtentionDiplome;
	}

	public void setDateObtentionDiplome(LocalDate dateObtentionDiplome) {
		this.dateObtentionDiplome = dateObtentionDiplome;
	}
	
	public Ecole getEcole() {
		return ecole;
	}

	public void setEcole(Ecole ecole) {
		this.ecole = ecole;
	}

	public TypeDiplome getTypeDiplomeAutoFill() {
		return typeDiplomeAutoFill;
	}

	public void setTypeDiplomeAutoFill(TypeDiplome typeDiplomeAutoFill) {
		this.typeDiplomeAutoFill = typeDiplomeAutoFill;
	}

	public LocalDate getDateObtentionDiplomeAutoFill() {
		return dateObtentionDiplomeAutoFill;
	}

	public void setDateObtentionDiplomeAutoFill(LocalDate dateObtentionDiplomeAutoFill) {
		this.dateObtentionDiplomeAutoFill = dateObtentionDiplomeAutoFill;
	}

	public String getEcoleAutoFill() {
		return ecoleAutoFill;
	}

	public void setEcoleAutoFill(String ecoleAutoFill) {
		this.ecoleAutoFill = ecoleAutoFill;
	}
}
