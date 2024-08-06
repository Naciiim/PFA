import { Component, Input } from '@angular/core';

import { MatDialog } from '@angular/material/dialog';
import {PostingDetailsComponent} from "../posting-details/posting-details.component";
import {Posting} from "../../models/posting.model";
import {ExportService} from "../../services/export.service";
import {HttpClient} from "@angular/common/http";

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
        a.href = url;
        a.download = 'PostingSearched.xlsx';
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
                a.href = url;
                a.download = 'Mouvements.xlsx';
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
      this.errorMessage = 'Aucun posting disponible pour effectuer la recherche.';
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
