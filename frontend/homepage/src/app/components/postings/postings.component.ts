import { Component, OnInit } from '@angular/core';
import { PostingService } from '../../services/posting.service';
import { Posting } from '../../models/posting.model';
import { ExportService } from '../../services/export.service';

@Component({
  selector: 'app-postings',
  templateUrl: './postings.component.html',
  styleUrls: ['./postings.component.css']
})
export class PostingsComponent implements OnInit {
  transactionid: string = '';
  masterreference: string = '';
  eventreference: string = '';
  defaultPostings: Posting[] = [];
  noPostings : Posting[]=[];
  transactionPostings: Posting[] = [];
  backendErrorMessage: string = '';
  errorMessage: string = '';
  showError: boolean = false;
  currentPage: number = 1;
  totalPages: number = 1;
  totalPagesWithDiffEtat: number = 1;
  currentPageWithDiffEtat: number = 1;

  constructor(private postingService: PostingService) {}

  ngOnInit() {
    this.loadDefaultPostings();
  }

  loadDefaultPostings() {
    const defaultRequest = {
      transactionid: '',
      masterreference: '',
      eventreference: '',
      page: this.currentPageWithDiffEtat - 1,
      size: 10
    };
    this.postingService.getPostings(defaultRequest).subscribe(
      response => {
        if (response) {
          if (response.postingWithDiffEtat && response.postingWithDiffEtat.length > 0) {
            this.defaultPostings = response.postingWithDiffEtat;
            this.totalPagesWithDiffEtat = response.totalPagesWithDiffEtat || 1;
            this.backendErrorMessage = '';
            this.showError=false;
          } else {
            this.defaultPostings = [];
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
      transactionid: this.transactionid,
      masterreference: this.masterreference,
      eventreference: this.eventreference,
      page: this.currentPage - 1,
      size: 10
    };

    this.postingService.getPostings(request).subscribe(
      response => {
        if (response) {
          if (response.POSTINGSEARCHED && response.POSTINGSEARCHED.length > 0) {
            this.transactionPostings = response.POSTINGSEARCHED;
            this.totalPages = response.totalPages || 1;
            this.backendErrorMessage = '';
            this.showError = false;

          } else {
            this.transactionPostings = [];
            this.totalPages = 1;
            this.backendErrorMessage = response.message || 'Aucun posting trouvé.';
            this.showError = true;
          }
        }
      },
      error => {
        console.error('Erreur lors de la récupération des postings :', error);
        this.errorMessage = 'Une erreur est survenue lors de la récupération des postings';
        this.backendErrorMessage = error.message || 'Une erreur inconnue est survenue';
        this.showError = true;
      }
    );
  }


  searchPostings() {
    if (!this.transactionid && !this.masterreference) {
      this.errorMessage = 'Transaction ID et Master Reference ne peuvent pas être tous les deux vides.';
      this.showError = true;
      return;
    }

    this.currentPage = 1;
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
    this.showError = false;
    this.transactionid = '';
    this.masterreference = '';
    this.eventreference = '';
    this.currentPage = 1;
    this.currentPageWithDiffEtat = 1;
    this.loadDefaultPostings();
  }
}
