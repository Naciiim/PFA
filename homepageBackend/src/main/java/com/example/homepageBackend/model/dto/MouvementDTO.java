package com.example.homepageBackend.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MouvementDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transactionid;

    private String transactionseqno;


    private String accounttype;

    private String numerocompte;

    private String sens;

    private String montant;

    private String reference;

    private String libellecourt;
    private Date datevaleur;

    private String etat;

    private String eventreference;

    private String libellelong;

    private String cptgen;
}

