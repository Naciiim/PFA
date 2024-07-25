import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExportService {
  private exportUrl = 'http://localhost:8080/api/exportPostings';

  constructor(private http: HttpClient) {}

  exportDataToExcel(): Observable<Blob> {
    const headers = new HttpHeaders({
      'Accept': 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });

    return this.http.get(this.exportUrl, { headers: headers, responseType: 'blob' });
  }
}
