package com.example.homepageBackend.model.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity(name="Posting")
public class Posting implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private PostingPK id;

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

    @Temporal(TemporalType.DATE)
    private Date dateinsertion;

    @Temporal(TemporalType.DATE)
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


    public Posting() {
    }

    public PostingPK getId() {
        return this.id;
    }

    public void setId(PostingPK id) {
        this.id = id;
    }

    public String getAccbasicnumber() {
        return this.accbasicnumber;
    }

    public void setAccbasicnumber(String accbasicnumber) {
        this.accbasicnumber = accbasicnumber;
    }

    public String getAccbranchnumber() {
        return this.accbranchnumber;
    }

    public void setAccbranchnumber(String accbranchnumber) {
        this.accbranchnumber = accbranchnumber;
    }

    public String getAccountnumber() {
        return this.accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getAccounttype() {
        return this.accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getAccsuffix() {
        return this.accsuffix;
    }

    public void setAccsuffix(String accsuffix) {
        this.accsuffix = accsuffix;
    }

    public String getAddmntdelflag() {
        return this.addmntdelflag;
    }

    public void setAddmntdelflag(String addmntdelflag) {
        this.addmntdelflag = addmntdelflag;
    }

    public String getApplication() {
        return this.application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getBackofficeaccountno() {
        return this.backofficeaccountno;
    }

    public void setBackofficeaccountno(String backofficeaccountno) {
        this.backofficeaccountno = backofficeaccountno;
    }


    public String getChargeid() {
        return this.chargeid;
    }

    public void setChargeid(String chargeid) {
        this.chargeid = chargeid;
    }

    public String getCustomermnemonic() {
        return this.customermnemonic;
    }

    public void setCustomermnemonic(String customermnemonic) {
        this.customermnemonic = customermnemonic;
    }

    public String getCustomertype() {
        return this.customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public Date getDateinsertion() {
        return this.dateinsertion;
    }

    public void setDateinsertion(Date dateinsertion) {
        this.dateinsertion = dateinsertion;
    }

    public Date getDatetraitement() {
        return this.datetraitement;
    }

    public void setDatetraitement(Date datetraitement) {
        this.datetraitement = datetraitement;
    }

    public String getDebitcreditflag() {
        return this.debitcreditflag;
    }

    public void setDebitcreditflag(String debitcreditflag) {
        this.debitcreditflag = debitcreditflag;
    }

    public String getEtat() {
        return this.etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getEventkey() {
        return this.eventkey;
    }

    public void setEventkey(String eventkey) {
        this.eventkey = eventkey;
    }

    public String getEventreference() {
        return this.eventreference;
    }

    public void setEventreference(String eventreference) {
        this.eventreference = eventreference;
    }

    public String getInputbranch() {
        return this.inputbranch;
    }

    public void setInputbranch(String inputbranch) {
        this.inputbranch = inputbranch;
    }

    public String getInternalrecnref() {
        return this.internalrecnref;
    }

    public void setInternalrecnref(String internalrecnref) {
        this.internalrecnref = internalrecnref;
    }

    public String getIssueorcontractdate() {
        return this.issueorcontractdate;
    }

    public void setIssueorcontractdate(String issueorcontractdate) {
        this.issueorcontractdate = issueorcontractdate;
    }

    public String getMasterkey() {
        return this.masterkey;
    }

    public void setMasterkey(String masterkey) {
        this.masterkey = masterkey;
    }

    public String getMasterreference() {
        return this.masterreference;
    }

    public void setMasterreference(String masterreference) {
        this.masterreference = masterreference;
    }

    public String getOtherpartyref() {
        return this.otherpartyref;
    }

    public void setOtherpartyref(String otherpartyref) {
        this.otherpartyref = otherpartyref;
    }

    public String getParentcountry() {
        return this.parentcountry;
    }

    public void setParentcountry(String parentcountry) {
        this.parentcountry = parentcountry;
    }

    public String getPostingamount() {
        return this.postingamount;
    }

    public void setPostingamount(String postingamount) {
        this.postingamount = postingamount;
    }

    public String getPostingbranch() {
        return this.postingbranch;
    }

    public void setPostingbranch(String postingbranch) {
        this.postingbranch = postingbranch;
    }

    public String getPostingccy() {
        return this.postingccy;
    }

    public void setPostingccy(String postingccy) {
        this.postingccy = postingccy;
    }

    public String getPostingnarrative1() {
        return this.postingnarrative1;
    }

    public void setPostingnarrative1(String postingnarrative1) {
        this.postingnarrative1 = postingnarrative1;
    }

    public String getPostingseqno() {
        return this.postingseqno;
    }

    public void setPostingseqno(String postingseqno) {
        this.postingseqno = postingseqno;
    }

    public String getProductreference() {
        return this.productreference;
    }

    public void setProductreference(String productreference) {
        this.productreference = productreference;
    }

    public String getRelatedparty() {
        return this.relatedparty;
    }

    public void setRelatedparty(String relatedparty) {
        this.relatedparty = relatedparty;
    }

    public String getSettlementaccountused() {
        return this.settlementaccountused;
    }

    public void setSettlementaccountused(String settlementaccountused) {
        this.settlementaccountused = settlementaccountused;
    }

    public String getTransactioncode() {
        return this.transactioncode;
    }

    public void setTransactioncode(String transactioncode) {
        this.transactioncode = transactioncode;
    }

    public String getValuedate() {
        return this.valuedate;
    }

    public void setValuedate(String valuedate) {
        this.valuedate = valuedate;
    }

    public String getBankCode1() {
        return bankCode1;
    }

    public void setBankCode1(String bankCode1) {
        this.bankCode1 = bankCode1;
    }

    public String getBankCode2() {
        return bankCode2;
    }

    public void setBankCode2(String bankCode2) {
        this.bankCode2 = bankCode2;
    }

    public String getBankCode3() {
        return bankCode3;
    }

    public void setBankCode3(String bankCode3) {
        this.bankCode3 = bankCode3;
    }

    public String getBankCode4() {
        return bankCode4;
    }

    public void setBankCode4(String bankCode4) {
        this.bankCode4 = bankCode4;
    }

    public String getBankCode5() {
        return bankCode5;
    }

    public void setBankCode5(String bankCode5) {
        this.bankCode5 = bankCode5;
    }

    public String getUserCode1() {
        return userCode1;
    }

    public void setUserCode1(String userCode1) {
        this.userCode1 = userCode1;
    }

    public String getUserCode2() {
        return userCode2;
    }

    public void setUserCode2(String userCode2) {
        this.userCode2 = userCode2;
    }

    @Override
    public String toString() {
        return "Posting [id=" + id + ", accbasicnumber=" + accbasicnumber
                + ", accbranchnumber=" + accbranchnumber + ", accountnumber="
                + accountnumber + ", accounttype=" + accounttype
                + ", accsuffix=" + accsuffix + ", addmntdelflag="
                + addmntdelflag + ", application=" + application
                + ", backofficeaccountno=" + backofficeaccountno
                + ", chargeid=" + chargeid + ", customermnemonic="
                + customermnemonic + ", customertype=" + customertype
                + ", dateinsertion=" + dateinsertion + ", datetraitement="
                + datetraitement + ", debitcreditflag=" + debitcreditflag
                + ", etat=" + etat + ", eventkey=" + eventkey
                + ", eventreference=" + eventreference + ", inputbranch="
                + inputbranch + ", internalrecnref=" + internalrecnref
                + ", issueorcontractdate=" + issueorcontractdate
                + ", masterkey=" + masterkey + ", masterreference="
                + masterreference + ", otherpartyref=" + otherpartyref
                + ", parentcountry=" + parentcountry + ", postingamount="
                + postingamount + ", postingbranch=" + postingbranch
                + ", postingccy=" + postingccy + ", postingnarrative1="
                + postingnarrative1 + ", postingseqno=" + postingseqno
                + ", productreference=" + productreference + ", relatedparty="
                + relatedparty + ", settlementaccountused="
                + settlementaccountused + ", transactioncode="
                + transactioncode + ", valuedate=" + valuedate + ", bankCode1="
                + bankCode1 + ", bankCode2=" + bankCode2 + ", bankCode3="
                + bankCode3 + ", bankCode4=" + bankCode4 + ", bankCode5="
                + bankCode5 + ", userCode1=" + userCode1 + ", userCode2="
                + userCode2 + "]";
    }





}