// angular.module('myApp')
//   .service('CreService', ['$http', function($http) {
//     var baseUrl = '/api';
//
//     this.exportCreToExcel = function(transactionId) {
//       return $http.get(baseUrl + '/export/cre', { params: { transactionId: transactionId }, responseType: 'arraybuffer' });
//     };
//   }]);
import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import { CreModel } from "../models/cre.model";

@Injectable({
  providedIn: 'root'
})
export class CreService {


  constructor(private http: HttpClient) { }

  private apiUrl = 'http://localhost:8080/api/validate';

  getCre(): Observable<CreModel[]>{
    return this.http.get<CreModel[]>(this.apiUrl);
  }

}
