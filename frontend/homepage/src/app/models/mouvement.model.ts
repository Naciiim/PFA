import {CdkCellOutlet} from "@angular/cdk/table";

export interface Mouvement
{
  transactionid: string;
  transactionseqno: string;
  accounttype: string;
  numerocompte: string;
  sens: string;
  montant: number ;
  reference: string ;
  libellecourt: string;
  etat: string;
  eventreference: string ;
  libellelong: string;
  cptgen: number;
  datevaleur: Date;
}
