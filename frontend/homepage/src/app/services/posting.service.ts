import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import {Observable, of} from 'rxjs';
import { PostingModel } from '../models/posting.model';

@Injectable({
  providedIn: 'root'
})
export class PostingService {
  private searchResults: { postings: PostingModel[] } = {
    postings: []
  };

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getPosting(params: { [key: string]: any }): Observable<PostingModel[]> {
    let httpParams = new HttpParams();

    for (const key in params) {
      if (params[key] !== null && params[key] !== undefined) {
        httpParams = httpParams.set(key, params[key]);
      }
    }

    return this.http.get<PostingModel[]>(`${this.baseUrl}/api/validate`, { params: httpParams });
  }
  getInvalidPostings(): Observable<PostingModel[]> {

    return of(this.searchResults.postings);
  }
}

