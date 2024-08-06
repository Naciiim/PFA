package com.example.homepageBackend.model.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity(name="MOUVEMENT_TRF")
public class MouvementTrf implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MouvementPK id;	

	private String accounttype;
	private String cptgen;
	@Temporal(TemporalType.DATE)
	private Date datevaleur;

	private String etat;
	private String eventreference;
	private String libellecourt;
    private String libellelong;
	private String montant;
	private String numerocompte;
	private String reference;
	private String sens;

	public MouvementTrf() {
	}

	

	public String getLibellecourt() {
		return libellecourt;
	}



	public void setLibellecourt(String libellecourt) {
		this.libellecourt = libellecourt;
	}



	public String getLibellelong() {
		return libellelong;
	}



	public void setLibellelong(String libellelong) {
		this.libellelong = libellelong;
	}



	public String getCptgen() {
		return cptgen;
	}



	public void setCptgen(String cptgen) {
		this.cptgen = cptgen;
	}



	public MouvementPK getId() {
		return id;
	}



	public void setId(MouvementPK id) {
		this.id = id;
	}



	public String getEventreference() {
		return eventreference;
	}



	public void setEventreference(String eventreference) {
		this.eventreference = eventreference;
	}



	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	public String getNumerocompte() {
		return numerocompte;
	}

	public void setNumerocompte(String numerocompte) {
		this.numerocompte = numerocompte;
	}

	public String getMontant() {
		return montant;
	}

	public void setMontant(String montant) {
		this.montant = montant;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	

	public Date getDatevaleur() {
		return this.datevaleur;
	}



	public void setDatevaleur(Date datevaleur) {
		this.datevaleur = datevaleur;
	}



	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}



	public String getSens() {
		return sens;
	}



	public void setSens(String sens) {
		this.sens = sens;
	}
	
	

	

}
