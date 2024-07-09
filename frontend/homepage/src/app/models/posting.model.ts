export interface PostingModel {
  id: number;
  transactionId: string;
  transactionDate: Date;
  debitAccount: string;
  creditAccount: string;
  status: string;
}
