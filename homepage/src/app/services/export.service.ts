import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExportService {
  private exportUrl = 'http://localhost:8080/api/exportPosting';
  private exportMvtUrl = 'http://localhost:8080/api/exportMouvement'
  private exportCreUrl='http://localhost:8080/api/exportPostingCre';

  constructor(private http: HttpClient) {}

  exportDataToExcel(): Observable<Blob> {
    const headers = new HttpHeaders({
      'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });

    return this.http.get(this.exportUrl, { headers: headers, responseType: 'blob' });
  }
  exportMvtDataToExcel(): Observable<Blob> {
    const headers = new HttpHeaders({
      'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });

    return this.http.get(this.exportMvtUrl, { headers: headers, responseType: 'blob' });
  }
  exportCreDataToExcel(): Observable<Blob> {
    const headers = new HttpHeaders({
      'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });

    return this.http.get(this.exportCreUrl, { headers: headers, responseType: 'blob' });
  }

}
