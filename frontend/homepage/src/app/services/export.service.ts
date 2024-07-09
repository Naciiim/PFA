import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExportService {

  private baseUrl = 'http://localhost:8080/api/export';

  constructor(private http: HttpClient) {}

  exportPosting(transactionId: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/postings/${transactionId}`, { responseType: 'blob' });
  }

  exportMouvement(transactionId: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/mouvements/${transactionId}`, { responseType: 'blob' });
  }

  exportCre(transactionId: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/cres/${transactionId}`, { responseType: 'blob' });
  }

  exportAll(transactionId: string): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/all/${transactionId}`, { responseType: 'blob' });
  }
}
