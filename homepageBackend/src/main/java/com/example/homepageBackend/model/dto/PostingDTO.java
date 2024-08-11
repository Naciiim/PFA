package com.example.homepageBackend.model.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
public class PostingDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transactionid;
    private String transactionseqno;
    private String accbasicnumber;
    private String accbranchnumber;
    private String accountnumber;
    private String accounttype;
    private String accsuffix;
    private String addmntdelflag;
    private String application;
    private String backofficeaccountno;
    private String chargeid;
    private String customermnemonic;
    private String customertype;
    private Date dateinsertion;
    private Date datetraitement;
    private String debitcreditflag;
    private String etat;
    private String eventkey;
    private String eventreference;
    private String inputbranch;
    private String internalrecnref;
    private String issueorcontractdate;
    private String masterkey;
    private String masterreference;
    private String otherpartyref;
    private String parentcountry;
    private String postingamount;
    private String postingbranch;
    private String postingccy;
    private String postingnarrative1;
    private String postingseqno;
    private String productreference;
    private String relatedparty;
    private String settlementaccountused;
    private String transactioncode;
    private String valuedate;
    private String bankCode1;
    private String bankCode2;
    private String bankCode3;
    private String bankCode4;
    private String bankCode5;
    private String userCode1;
    private String userCode2;
}
