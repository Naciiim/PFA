import { Component, Input } from '@angular/core';

import { MatDialog } from '@angular/material/dialog';
import {PostingDetailsComponent} from "../posting-details/posting-details.component";
import {Posting} from "../../models/posting.model";
import {ExportService} from "../../services/export.service";
import {HttpClient} from "@angular/common/http";
import {forkJoin, Observable} from "rxjs";
import * as XLSX from 'xlsx';



@Component({
  selector: 'app-transaction-postings',
  templateUrl: './transaction-postings-component.html',
  styleUrls: ['./transaction-postings-component.css']
})
export class TransactionPostingsComponent {
  @Input() postings: Posting[] = [];
  errorMessage: string = '';



  constructor(protected exportService: ExportService, private dialog: MatDialog,private http: HttpClient) {}



  exportToExcel() {
    this.exportService.exportDataToExcel().subscribe(
      (data: Blob) => {
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        const temps=new Date();
        const jour=temps.toLocaleDateString('fr-FR').replace(/\//g, '-');
        const heure=temps.toTimeString().slice(0, 8).replace(/:/g, '-');
        const date=`${jour} at ${heure}`
        a.href = url;
        a.download = `Postings_${date}.xlsx`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error => {
        console.error('Erreur lors de l\'export vers Excel', error);
        alert('Une erreur s\'est produite lors de l\'exportation des données. Veuillez réessayer.');
      }
    );
  }

  exportMouvementsToExcel() {
    if (this.postings.length > 0) {
      const firstPosting = this.postings[0];
      const mouvementRequest = {
        transactionid: firstPosting.transactionid,
        masterreference: firstPosting.masterreference,
        eventreference: firstPosting.eventreference,
        page: 0 // Ajoutez d'autres critères si nécessaire
      };

      console.log('Mouvement Request:', mouvementRequest); // Ajoutez cette ligne pour loguer la requête

      this.http.post('http://localhost:8080/api/getMouvement', mouvementRequest).subscribe(
        (response: any) => {
          console.log('Response:', response); // Ajoutez cette ligne pour loguer la réponse
          if (response && response.mvtSearched) {
            this.exportService.exportMvtDataToExcel().subscribe(
              (data: Blob) => {
                const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                const temps=new Date();
                const jour=temps.toLocaleDateString('fr-FR').replace(/\//g, '-');
                const heure=temps.toTimeString().slice(0, 8).replace(/:/g, '-');
                const date=`${jour} at ${heure}`
                a.href = url;
                a.download = `Mouvements_${date}.xlsx`;
                a.click();
                window.URL.revokeObjectURL(url);
              },
              error => {
                this.errorMessage = 'Une erreur s\'est produite lors de l\'exportation des mouvements. Veuillez réessayer.';
                console.error('Erreur lors de l\'export des mouvements vers Excel', error);
              }
            );
          } else {
            this.errorMessage = 'Aucun mouvement trouvé pour les critères de recherche spécifiés.';
          }
        },
        error => {
          this.errorMessage = 'Une erreur s\'est produite lors de la récupération des mouvements. Veuillez réessayer.';
          console.error('Erreur lors de la récupération des mouvements', error);
        }
      );
    } else {
      this.errorMessage = 'Aucune posting disponible pour effectuer la recherche.';
    }
  }
  exportCresToExcel() {
    if (this.postings.length > 0) {
      const firstPosting = this.postings[0];
      const creRequest = {
        transId: firstPosting.transactionid,

        page: 0 // Ajoutez d'autres critères si nécessaire
      };

      console.log('Cre Request:', creRequest); // Ajoutez cette ligne pour loguer la requête

      this.http.post('http://localhost:8080/api/getPostingCre', creRequest).subscribe(
        (response: any) => {
          console.log('Response:', response); // Ajoutez cette ligne pour loguer la réponse
          if (response && response.postingCreSearched) {
            this.exportService.exportCreDataToExcel().subscribe(
              (data: Blob) => {
                const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                const temps=new Date();
                const jour=temps.toLocaleDateString('fr-FR').replace(/\//g, '-');
                const heure=temps.toTimeString().slice(0, 8).replace(/:/g, '-');
                const date=`${jour} at ${heure}`
                a.href = url;
                a.download = `Cres_${date}.xlsx`;
                a.click();
                window.URL.revokeObjectURL(url);
              },
              error => {
                this.errorMessage = 'Une erreur s\'est produite lors de l\'exportation des postin Cre. Veuillez réessayer.';
                console.error('Erreur lors de l\'export des cres vers Excel', error);
              }
            );
          } else {
            this.errorMessage = 'Aucun cre trouvé pour les critères de recherche spécifiés.';
          }
        },
        error => {
          this.errorMessage = 'Une erreur s\'est produite lors de la récupération des cres. Veuillez réessayer.';
          console.error('Erreur lors de la récupération des cres', error);
        }
      );
    } else {
      this.errorMessage = 'Aucune posting disponible pour effectuer la recherche.';
    }
  }
  exportAllDataToExcel() {
    if (this.postings.length > 0) {
      const firstPosting = this.postings[0];

      const mouvementRequest = {
        transactionid: firstPosting.transactionid,
        masterreference: firstPosting.masterreference,
        eventreference: firstPosting.eventreference,
        page: 0
      };

      const creRequest = {
        transId: firstPosting.transactionid,
        page: 0
      };

      const fetchAllMouvements = (request: any) => {
        const results: any[] = [];
        return new Observable(observer => {
          const fetchPage = (page: number) => {
            request.page = page;
            this.http.post<{ mvtSearched: any[] }>('http://localhost:8080/api/getMouvement', request).subscribe(
              response => {
                if (response.mvtSearched && response.mvtSearched.length > 0) {
                  results.push(...response.mvtSearched);
                  fetchPage(page + 1); // Fetch the next page
                } else {
                  observer.next(results);
                  observer.complete();
                }
              },
              error => {
                observer.error(error);
              }
            );
          };
          fetchPage(0); // Start fetching from page 0
        });
      };

      const fetchAllCres = (request: any) => {
        const results: any[] = [];
        return new Observable(observer => {
          const fetchPage = (page: number) => {
            request.page = page;
            this.http.post<{ postingCreSearched: any[] }>('http://localhost:8080/api/getPostingCre', request).subscribe(
              response => {
                if (response.postingCreSearched && response.postingCreSearched.length > 0) {
                  results.push(...response.postingCreSearched);
                  fetchPage(page + 1); // Fetch the next page
                } else {
                  observer.next(results);
                  observer.complete();
                }
              },
              error => {
                observer.error(error);
              }
            );
          };
          fetchPage(0); // Start fetching from page 0
        });
      };

      const postingObservable = this.exportService.exportDataToExcel(); // Assurez-vous que cette méthode renvoie un Blob
      const mouvementObservable = fetchAllMouvements(mouvementRequest);
      const creObservable = fetchAllCres(creRequest);

      forkJoin([postingObservable, mouvementObservable, creObservable]).subscribe(
        ([postingData, mouvementData, creData]) => {
          if (mouvementData && creData) {
            const workbook = XLSX.utils.book_new();

            // Ajouter la feuille PostingSearched à partir du Blob
            const reader = new FileReader();
            reader.onload = () => {
              const arrayBuffer = reader.result as ArrayBuffer;
              const wb = XLSX.read(arrayBuffer, { type: 'array' });
              const postingSheet = wb.Sheets[wb.SheetNames[0]];
              XLSX.utils.book_append_sheet(workbook, postingSheet, 'Postings');

              // Ajouter la feuille MvtSearched à partir des données JSON
              const mvtDataArray = Array.isArray(mouvementData) ? mouvementData : [];
              const mvtSheet = XLSX.utils.json_to_sheet(mvtDataArray);
              XLSX.utils.book_append_sheet(workbook, mvtSheet, 'Mouvements');

              // Ajouter la feuille PostingCreSearched à partir des données JSON
              const creDataArray = Array.isArray(creData) ? creData : [];
              const creSheet = XLSX.utils.json_to_sheet(creDataArray);
              XLSX.utils.book_append_sheet(workbook, creSheet, 'Cres');

              // Générer le fichier Excel et le télécharger
              const excelBuffer = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
              const blob = new Blob([excelBuffer], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
              const url = window.URL.createObjectURL(blob);
              const a = document.createElement('a');
              const temps=new Date();
              const jour=temps.toLocaleDateString('fr-FR').replace(/\//g, '-');
              const heure=temps.toTimeString().slice(0, 8).replace(/:/g, '-');
              const date=`${jour}_${heure}`
              a.href = url;
              a.download = `Export_${date}.xlsx`;
              a.click();
              window.URL.revokeObjectURL(url);
            };
            reader.readAsArrayBuffer(postingData);  // Lire le Blob en tant qu'ArrayBuffer
          } else {
            this.errorMessage = 'Aucune donnée trouvée pour les critères de recherche spécifiés.';
          }
        },
        error => {
          console.error('Erreur lors de l\'exportation des données:', error);
          alert('Une erreur s\'est produite lors de l\'exportation des données. Veuillez réessayer.');
        }
      );
    } else {
      this.errorMessage = 'Aucune posting disponible pour effectuer la recherche.';
    }
  }
  onSelectExportOption(event: any) {
    const selectedValue = event.target.value;

    switch (selectedValue) {
      case 'exportPostings':
        this.exportToExcel();
        break;
      case 'exportMouvements':
        this.exportMouvementsToExcel();
        break;
      case 'exportCres':
        this.exportCresToExcel();
        break;
      case 'exportAll':
        this.exportAllDataToExcel();
        break;
      default:
        break;
    }
  }

  dismissError() {
    this.errorMessage = '';

  }


  openDialog(posting: Posting): void {
    this.dialog.open(PostingDetailsComponent, {
      data: posting
    });
  }
}
