import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Posting } from '../models/posting.model';

@Injectable({
  providedIn: 'root'
})
export class PostingService {
  private apiUrl = 'http://localhost:8080/api/getPosting';

  constructor(private http: HttpClient) {}

  getPostings(request: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, request);
  }
}
