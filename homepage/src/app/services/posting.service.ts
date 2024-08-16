import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import { Posting } from '../models/posting.model';

@Injectable({
  providedIn: 'root'
})
export class PostingService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getPostings(request: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/getPosting`, request).pipe(
      catchError(error => {
        let errorMessage = 'Une erreur est survenue';
        if (error.error && error.error.message) {
          errorMessage = error.error.message;
        }
        return throwError(() => new Error(errorMessage));
      })
    );
  }

}
