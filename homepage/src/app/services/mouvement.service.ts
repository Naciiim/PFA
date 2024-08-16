import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import { Mouvement } from '../models/mouvement.model';

@Injectable({
  providedIn: 'root'
})
export class MouvementService {
  private baseUrl = 'http://localhost:8080/api/getMouvement'; // Replace with your backend API URL

  constructor(private http: HttpClient) {}

  getMouvements(request: any): Observable<any> {
    return this.http.post<any>(this.baseUrl, request).pipe(
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
