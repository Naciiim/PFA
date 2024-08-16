import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PostingCreService {
  private apiUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getPostingCre(request: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/getPostingCre`, request).pipe(
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
