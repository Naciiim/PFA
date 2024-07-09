import { Component } from '@angular/core';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PostingModel } from '../models/posting.model';
import { CreModel } from '../models/cre.model';
import { MouvementModel } from '../models/mouvement.model';
import { PostingService } from '../services/posting.service';
import { CreService } from '../services/cre.service';
import { MouvementService } from '../services/mouvement.service';
import { ExportService } from '../services/export.service';

@Component({
  selector: 'app-homepage-front',
  templateUrl: './homepage-front.component.html',
  styleUrls: ['./homepage-front.component.css']
})
export class HomepageFrontComponent {
  transactionId = '';
  ref = '';
  event = '';

  postings: PostingModel[] = [];
  mouvements: MouvementModel[] = [];
  cres: CreModel[] = [];

  searchResults = {
    postings: [] as PostingModel[],
    mouvements: [] as MouvementModel[],
    cres: [] as CreModel[]
  };

  errorMessage = '';
  warningMessages: string[] = [];

  constructor(
    private postingService: PostingService,
    private creService: CreService,
    private mouvementService: MouvementService,
    private exportService: ExportService
  ) {}

  onSearch() {
    this.errorMessage = '';
    this.warningMessages = [];

    if (this.isValid()) {
      const params = {
        transactionId: this.transactionId,
        ref: this.ref,
        event: this.event
      };

      this.fetchData(params);
    } else {
      this.errorMessage = 'Veuillez remplir correctement les champs obligatoires.';
    }
  }

  private fetchData(params: any) {
    this.postingService.getPosting(params).pipe(
      catchError(error => {
        this.errorMessage = 'Erreur lors de la récupération des données.';
        return throwError(error);
      })
    ).subscribe(
      (response: any) => {
        console.log('Réponse reçue :', response);

        if (response) {
          this.postings = response.posting ? [response.posting] : [];
          this.cres = response.cre ? [response.cre] : [];
          this.mouvements = response.mouvement ? [response.mouvement] : [];

          this.searchResults = {
            postings: this.postings,
            mouvements: this.mouvements,
            cres: this.cres
          };

          this.handleSearchResponse();
        } else {
          console.error('Réponse vide ou invalide reçue :', response);
          this.errorMessage = 'Erreur : Réponse vide ou invalide.';
        }
      },
      error => {
        console.error('Erreur lors de la récupération des données :', error);
        this.errorMessage = 'Erreur lors de la récupération des données.';
      }
    );
  }

  private handleSearchResponse(): void {
    this.errorMessage = '';
    this.warningMessages = [];

    if (this.searchResults.postings.length > 0) {
      const invalidPostings = this.searchResults.postings.filter(posting => posting.status !== 'T');
      if (invalidPostings.length > 0) {
        this.errorMessage = 'Erreur : Certains postings ont un statut différent de "T".';
        this.clearSearchResults();
        return;
      }
    } else {
      this.errorMessage = 'Aucun résultat trouvé pour les postings.';
      this.clearSearchResults();
      return;
    }

    if (this.searchResults.mouvements.length > 0) {
      const invalidMouvements = this.searchResults.mouvements.filter(mouvement => mouvement.status !== 'T');
      if (invalidMouvements.length > 0) {
        this.warningMessages.push('Attention : Certains mouvements ont un statut différent de "T".');
      }
    } else {
      this.errorMessage = 'Aucun résultat trouvé pour les mouvements.';
    }

    if (this.searchResults.cres.length > 0) {
      const invalidCres = this.searchResults.cres.filter(cre => cre.status !== 'O');
      if (invalidCres.length > 0) {
        this.warningMessages.push('Attention : Certains CRE ont un statut différent de "O".');
      }
    } else {
      this.errorMessage = 'Aucun résultat trouvé pour les CRE.';
    }

    if (this.searchResults.postings.length === 0 && this.searchResults.mouvements.length === 0 && this.searchResults.cres.length === 0) {
      this.errorMessage = 'Aucun résultat trouvé pour les critères spécifiés.';
    }
  }

  private clearSearchResults(): void {
    this.searchResults = { postings: [], mouvements: [], cres: [] };
  }

  isValid(): boolean {
    const refPattern = /^[A-Z]{3}24k\d{7}$/;
    const eventPattern = /^[A-Z]{2}\d{5}$/;

    if ((this.transactionId && this.transactionId.trim() !== '') || (this.ref && this.ref.trim() !== '')) {
      if (this.ref && !refPattern.test(this.ref)) {
        this.errorMessage = 'Le format de référence est incorrect. Utilisez le format suivant : 3 lettres majuscules suivies de "24k" et de 7 chiffres.';
        return false;
      }

      if (this.event && !eventPattern.test(this.event)) {
        this.errorMessage = 'Le format de l\'événement est incorrect. Utilisez le format suivant : 2 lettres majuscules suivies de 5 chiffres.';
        return false;
      }

      return true;
    }

    return false;
  }

  exportPostings() {
    this.exportService.exportPosting(this.transactionId).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'postings.xlsx';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    });
  }

  exportMouvements() {
    this.exportService.exportMouvement(this.transactionId).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'mouvements.xlsx';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    });
  }

  exportCres() {
    this.exportService.exportCre(this.transactionId).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'cres.xlsx';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    });
  }
  exportAll() {
    this.exportService.exportAll(this.transactionId).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'export_all.xlsx';
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    });
  }
}
