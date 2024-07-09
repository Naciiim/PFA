export interface MouvementModel{
    id: number;
    transactionId:  string;
    ref: string;
    amount: number;
    date: Date;
    event: string;
    status: string;
}
