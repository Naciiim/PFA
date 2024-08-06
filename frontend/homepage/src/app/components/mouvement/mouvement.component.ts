import { Component, OnInit } from '@angular/core';
import { MouvementService } from '../../services/mouvement.service';
import { Mouvement } from '../../models/mouvement.model';
import { ExportService } from '../../services/export.service';
import { MatDialog } from '@angular/material/dialog';
import { MouvementDetailsComponent } from '../mouvement-details/mouvement-details.component';

@Component({
  selector: 'app-mouvement',
  templateUrl: './mouvement.component.html',
  styleUrls: ['./mouvement.component.css']
})
export class MouvementComponent implements OnInit {
  transactionid: string = '';
  reference: string = '';
  eventreference: string = '';
  defaultMouvements: Mouvement[] = [];
  searchedMouvements: Mouvement[] = [];
  noMouvements: Mouvement[] = [];
  errorMessage: string = '';
  currentPage: number = 1;
  totalPages: number = 1;
  totalPagesWithDiffEtat: number = 1;
  currentPageWithDiffEtat: number = 1;

  constructor(
    private mouvementService: MouvementService,
    private exportService: ExportService,
    private dialog: MatDialog
  ) {}

  ngOnInit() {
    this.loadDefaultMouvements();
  }

  loadDefaultMouvements() {
    const defaultRequest = {
      transactionid: '',
      reference: '',
      eventreference: '',
      page: this.currentPageWithDiffEtat - 1,
      size: 10
    };

    this.mouvementService.getMouvements(defaultRequest).subscribe(
      response => {
        if (response) {
          if (response.mvtWithEtatDiff && response.mvtWithEtatDiff.length > 0) {
            this.defaultMouvements = response.mvtWithEtatDiff;
            this.totalPagesWithDiffEtat = response.totalPagesWithEtatDiff || 1;
            this.errorMessage = ''; // Réinitialiser le message d'erreur en cas de succès
          } else {
            this.defaultMouvements = [];
            this.totalPagesWithDiffEtat = 1;
            this.errorMessage = response.message || 'Aucun mouvement trouvé avec état différent.';
          }
        }
      },
      error => {
        console.error('Erreur lors de la récupération des mouvements par défaut:', error);
        this.errorMessage = 'Une erreur est survenue lors de la récupération des mouvements par défaut';
      }
    );
  }

  loadSearchedMouvements() {
    const request = {
      transactionid: this.transactionid,
      reference: this.reference,
      eventreference: this.eventreference,
      page: this.currentPage - 1,
      size: 10
    };

    this.mouvementService.getMouvements(request).subscribe(
      response => {
        if (response) {
          if (response.mvtSearched && response.mvtSearched.length > 0) {
            this.searchedMouvements = response.mvtSearched;
            this.totalPages = response.totalPages || 1;
            this.errorMessage = ''; // Réinitialiser le message d'erreur en cas de succès
          } else {
            this.searchedMouvements = [];
            this.totalPages = 1;
            this.errorMessage = response.message || 'Aucun mouvement trouvé.';
          }
        }
      },
      error => {
        console.error('Erreur lors de la récupération des mouvements recherchés:', error);
        this.errorMessage = 'Une erreur est survenue lors de la récupération des mouvements';
      }
    );
  }

  searchMouvements() {
    if (!this.transactionid && !this.reference) {
      this.errorMessage = "L'ID de transaction ou la référence est requise";
      return;
    }

    this.currentPage = 1; // Réinitialiser à la première page lors de la nouvelle recherche
    this.loadSearchedMouvements();
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.loadSearchedMouvements();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.loadSearchedMouvements();
    }
  }

  prevPageWithDiffEtat() {
    if (this.currentPageWithDiffEtat > 1) {
      this.currentPageWithDiffEtat--;
      this.loadDefaultMouvements();
    }
  }

  nextPageWithDiffEtat() {
    if (this.currentPageWithDiffEtat < this.totalPagesWithDiffEtat) {
      this.currentPageWithDiffEtat++;
      this.loadDefaultMouvements();
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
      this.loadSearchedMouvements();
    }
  }

  goToPageWithDiffEtat(page: number): void {
    if (page !== this.currentPageWithDiffEtat && page >= 1 && page <= this.totalPagesWithDiffEtat) {
      this.currentPageWithDiffEtat = page;
      this.loadDefaultMouvements();
    }
  }

  dismissError() {
    this.errorMessage = '';
    this.transactionid = '';
    this.reference = '';
    this.eventreference = '';
    this.currentPage = 1;
    this.currentPageWithDiffEtat = 1;
    this.loadDefaultMouvements();
  }
}
