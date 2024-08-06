package com.example.homepageBackend.model.entity;

import java.io.Serializable;
import javax.persistence.*;


@Embeddable
public class MouvementPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String transactionid;

	private String transactionseqno;

	public MouvementPK() {
	}
	public String getTransactionid() {
		return this.transactionid;
	}
	public void setTransactionid(String transactionid) {
		this.transactionid = transactionid;
	}
	public String getTransactionseqno() {
		return this.transactionseqno;
	}
	public void setTransactionseqno(String transactionseqno) {
		this.transactionseqno = transactionseqno;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MouvementPK)) {
			return false;
		}
		MouvementPK castOther = (MouvementPK)other;
		return 
			this.transactionid.equals(castOther.transactionid)
			&& this.transactionseqno.equals(castOther.transactionseqno);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.transactionid.hashCode();
		hash = hash * prime + this.transactionseqno.hashCode();
		
		return hash;
	}
}