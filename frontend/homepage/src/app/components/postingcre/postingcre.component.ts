import { Component } from '@angular/core';
import {PostingCre} from "../../models/postingcre.model";
import {PostingCreService} from "../../services/postingcre.service";

@Component({
  selector: 'app-postingcre',
  templateUrl: './postingcre.component.html',
  styleUrls: ['./postingcre.component.css']
})
export class PostingcreComponent {
  transId: string = '';
  masterreference: string = '';
  eventreference: string = '';
  defaultPostingCres: PostingCre[] = [];
  noPostings : PostingCre[]=[];
  transactionPostingCres: PostingCre[] = [];
  backendErrorMessage: string = '';
  errorMessage: string = '';
  currentPage: number = 1;
  totalPages: number = 1;
  totalPagesWithDiffEtat: number = 1;
  currentPageWithDiffEtat: number = 1;

  constructor(private postingCreService: PostingCreService) {}

  ngOnInit() {
    this.loadDefaultPostings();
  }

  loadDefaultPostings() {
    const defaultRequest = {
      transId: '',
      masterreference: '',
      eventreference: '',
      page: this.currentPageWithDiffEtat - 1,
      size: 10
    };
    this.postingCreService.getPostingCre(defaultRequest).subscribe(
      response => {
        if (response) {
          if (response.postingCreWithDiffEtat && response.postingCreWithDiffEtat.length > 0) {
            this.defaultPostingCres = response.postingCreWithDiffEtat;
            this.totalPagesWithDiffEtat = response.totalPagesWithDiffEtat || 1;
            this.backendErrorMessage = ''; // Réinitialiser le message d'erreur en cas de succès
          } else {
            this.defaultPostingCres = [];
            this.totalPagesWithDiffEtat = 1;
            this.backendErrorMessage = response.message || 'Aucun posting trouvé avec état différent.';
          }
        }
      },
      error => {
        console.error('Erreur lors de la récupération des postings par défaut :', error);
        this.errorMessage = 'Une erreur est survenue lors de la récupération des postings par défaut';
        this.backendErrorMessage = error.message || 'Une erreur inconnue est survenue'; // Réinitialiser le message d'erreur en cas de problème avec l'API
      }
    );
  }

  private loadPostings() {
    const request = {
      transId: this.transId,
      masterreference: this.masterreference,
      eventreference: this.eventreference,
      page: this.currentPage - 1,
      size: 10
    };

    this.postingCreService.getPostingCre(request).subscribe(
      response => {
        if (response) {
          if (response.postingCreSearched && response.postingCreSearched.length > 0) {
            this.transactionPostingCres = response.postingCreSearched;
            this.totalPages = response.totalPages || 1;
            this.backendErrorMessage = ''; // Réinitialiser le message d'erreur en cas de succès
          } else {
            this.transactionPostingCres = [];
            this.totalPages = 1;
            this.backendErrorMessage = response.message || 'Aucun posting trouvé.';
          }
        }
      },
      error => {
        console.error('Erreur lors de la récupération des postings :', error);
        this.errorMessage = 'Une erreur est survenue lors de la récupération des postings';
        this.backendErrorMessage = error.message || 'Une erreur inconnue est survenue'; // Réinitialiser le message d'erreur en cas de problème avec l'API
      }
    );
  }


  searchPostings() {
    if (!this.transId && !this.masterreference) {
      this.errorMessage = 'L\'ID de transaction ou la référence maître est requise';
      return;
    }

    this.currentPage = 1; // Réinitialiser à la première page lors de la nouvelle recherche
    this.loadPostings();
  }


  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.loadPostings();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.loadPostings();
    }
  }

  prevPageWithDiffEtat() {
    if (this.currentPageWithDiffEtat > 1) {
      this.currentPageWithDiffEtat--;
      this.loadDefaultPostings();
    }
  }

  nextPageWithDiffEtat() {
    if (this.currentPageWithDiffEtat < this.totalPagesWithDiffEtat) {
      this.currentPageWithDiffEtat++;
      this.loadDefaultPostings();
    }
  }

  pagesArray(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i + 1);
  }

  pagesArrayWithDiffEtat(): number[] {
    return Array.from({ length: this.totalPagesWithDiffEtat }, (_, i) => i + 1);
  }

  goToPage(page: number): void {
    if (page !== this.currentPage && page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.loadPostings();
    }
  }

  goToPageWithDiffEtat(page: number): void {
    if (page !== this.currentPageWithDiffEtat && page >= 1 && page <= this.totalPagesWithDiffEtat) {
      this.currentPageWithDiffEtat = page;
      this.loadDefaultPostings();
    }
  }

  dismissError() {
    this.errorMessage = '';
    this.transId = '';
    this.masterreference = '';
    this.eventreference = '';
    this.currentPage = 1;
    this.currentPageWithDiffEtat = 1;
    this.loadDefaultPostings();
  }
}
