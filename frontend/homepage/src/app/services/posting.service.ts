import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PostingService {

  private baseUrl = "http://localhost:8080/api"

  constructor(private http: HttpClient) { }

  getPostings(requestData: any): Observable<any> {
    const url = `${this.baseUrl}/getPosting`;
    return this.http.post(url, requestData);
  }
}
