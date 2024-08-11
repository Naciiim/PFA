package com.example.homepageBackend.model.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PostingCrePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String transId;

	private String typeCre;

	public PostingCrePK() {
	}
		
	

	public String getTransId() {
		return transId;
	}




	public void setTransId(String transID) {
		this.transId = transID;
	}




	public String getTypeCre() {
		return typeCre;
	}
	public void setTypeCre(String typeCre) {
		this.typeCre = typeCre;
	}
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PostingCrePK)) {
			return false;
		}
		PostingCrePK castOther = (PostingCrePK)other;
		return 
			this.transId.equals(castOther.transId)
			&& this.typeCre.equals(castOther.typeCre);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.transId.hashCode();
		hash = hash * prime + this.typeCre.hashCode();
		
		return hash;
	}
}