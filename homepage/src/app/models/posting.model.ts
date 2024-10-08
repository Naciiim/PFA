export interface Posting {
  transactionid: string;
  transactionseqno: string;
  accbasicnumber: string;
  accbranchnumber: string;
  accountnumber: string;
  accounttype: string;
  accsuffix: string;
  addmntdelflag: string;
  application: string;
  backofficeaccountno: string;
  chargeid: string;
  customermnemonic: string;
  customertype: string;
  dateinsertion: Date;
  datetraitement: Date;
  debitcreditflag: string;
  etat: string;
  eventkey: string;
  eventreference: string;
  inputbranch: string;
  internalrecnref: string;
  issueorcontractdate: string;
  masterkey: string;
  masterreference: string;
  otherpartyref: string;
  parentcountry: string;
  postingamount: string;
  postingbranch: string;
  postingccy: string;
  postingnarrative1: string;
  postingseqno: string;
  productreference: string;
  relatedparty: string;
  settlementaccountused: string;
  transactioncode: string;
  valuedate: string;
  bankCode1: string;
  bankCode2: string;
  bankCode3: string;
  bankCode4: string;
  bankCode5: string;
  userCode1: string;
  userCode2: string;

  [key: string]: any; //à verifier si tout marche bien
}
