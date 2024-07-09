import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { MouvementModel } from '../models/mouvement.model';

@Injectable({
  providedIn: 'root'
})
export class MouvementService {
  private apiUrl = 'http://localhost:8080/api/validate';

  constructor(private http: HttpClient) {}

  getMouvement(params: any): Observable<MouvementModel> {
    return this.http.get<MouvementModel>(this.apiUrl, { params }).pipe(
      catchError(error => {
        console.error('Erreur lors de la récupération des mouvements :', error);
        return throwError(error);
      })
    );
  }
}
