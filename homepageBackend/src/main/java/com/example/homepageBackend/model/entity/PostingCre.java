package com.example.homepageBackend.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity(name="POSTINGCRE")
public class PostingCre implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PostingCrePK id;

	private String chaineCre;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateCreation;
	private String etat;

	
	public PostingCre() {
	}

	

	public PostingCrePK getId() {
		return id;
	}

	public void setId(PostingCrePK id) {
		this.id = id;
	}

	public String getChaineCre() {
		return chaineCre;
	}

	public void setChaineCre(String chaineCre) {
		this.chaineCre = chaineCre;
	}



	public String getEtat() {
		return etat;
	}



	public void setEtat(String etat) {
		this.etat = etat;
	}



	public Date getDateCreation() {
		return dateCreation;
	}



	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	
	
	
	
}