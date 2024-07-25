import { Component, OnInit } from '@angular/core';
import { PostingService } from '../../services/posting.service';
import { Posting } from '../../models/posting.model';
import { ExportService } from '../../services/export.service';

@Component({
  selector: 'app-homepage-front',
  templateUrl: './homepage-front.component.html',
  styleUrls: ['./homepage-front.component.css']
})
export class HomepageFrontComponent implements OnInit {
  transactionid: string = '';
  masterreference: string = '';
  eventreference: string = '';
  defaultPostings: Posting[] = [];
  noPostings : Posting[]=[];
  transactionPostings: Posting[] = [];
  allPostings: Posting[] = [];
  errorMessage: string = '';
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
      page: this.currentPageWithDiffEtat - 1,  // Assurez-vous que le numéro de page est correct
      size: 10
    };
    this.postingService.getPostings(defaultRequest).subscribe(
      response => {
        console.log('Réponse pour les postings avec état différent:', response);
        if (response) {
          this.defaultPostings = response.postingWithDiffEtat || [];
          this.totalPagesWithDiffEtat = response.totalPagesWithDiffEtat || 1;
        }
      },
      error => {
        console.error('Erreur lors de la récupération des postings par défaut :', error);
        this.errorMessage = 'Une erreur est survenue lors de la récupération des postings par défaut';
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
          this.transactionPostings = response.POSTINGSEARCHED || [];
          this.totalPages = response.totalPages || 1;
        }
      },
      error => {
        console.error('Erreur lors de la récupération des postings :', error);
        this.errorMessage = 'Une erreur est survenue lors de la récupération des postings';
      }
    );
  }

  searchPostings() {
    if (!this.transactionid && !this.masterreference) {
      this.errorMessage = 'L\'ID de transaction ou la référence maître est requise';
      return;
    }

    this.currentPage = 1; // Reset to first page on new search
    this.allPostings = []; // Clear previous search results

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
    this.transactionid = '';
    this.masterreference = '';
    this.eventreference = '';
    this.currentPage = 1;
    this.currentPageWithDiffEtat = 1;
    this.loadDefaultPostings();
  }
}
