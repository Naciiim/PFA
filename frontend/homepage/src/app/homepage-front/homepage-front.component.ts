import { Component, OnInit } from '@angular/core';
import { PostingService } from '../services/posting.service';
import { Router } from "@angular/router";
import { Posting } from '../models/posting.model';

@Component({
  selector: 'app-homepage-front',
  templateUrl: './homepage-front.component.html',
  styleUrls: ['./homepage-front.component.css']
})
export class HomepageFrontComponent implements OnInit {
  transactionid: string = '';
  masterreference: string = '';
  defaultPostings: Posting[] = [];
  transactionPostings: Posting[] = [];
  errorMessage: string = '';

  constructor(private postingService: PostingService, private router: Router) {}

  ngOnInit() {
    this.loadDefaultPostings();
  }

  loadDefaultPostings() {
    const defaultRequest = { transactionid: '', masterreference: '', eventreference: '' };
    this.postingService.getPostings(defaultRequest).subscribe(
      response => {
        console.log('Réponse du serveur pour les postings par défaut :', response);
        if (response && response.postingWithDiffEtat) {
          this.defaultPostings = response.postingWithDiffEtat.filter((posting: Posting) => posting.etat !== 'T');
        } else {
          this.defaultPostings = [];
        }
      },
      error => {
        console.error('Erreur lors de la récupération des postings par défaut :', error);
        this.errorMessage = 'Une erreur est survenue lors de la récupération des postings par défaut';
      }
    );
  }

  searchPostings() {
    if (!this.transactionid && !this.masterreference) {
      this.errorMessage = 'L\'ID de transaction ou la référence maître est requise';
      return;
    }

    const searchRequest = {
      transactionid: this.transactionid,
      masterreference: this.masterreference,
      eventreference: ''
    };

    this.postingService.getPostings(searchRequest).subscribe(
      response => {
        console.log('Réponse du serveur pour les postings recherchés :', response);
        if (response) {
          this.transactionPostings = response.POSTINGSEARCHED || [];

          if (this.transactionPostings.length > 0) {
            this.errorMessage = '';
          } else {
            this.errorMessage = 'Aucun posting trouvé pour les critères spécifiés';
          }

          if (response.message) {
            console.warn(response.message);
          }
        }
      },
      error => {
        console.error('Erreur lors de la récupération des postings :', error);
        this.errorMessage = 'Une erreur est survenue lors de la récupération des postings';
      }
    );
  }

  dismissError() {
    this.errorMessage = '';
    this.transactionid = '';
    this.masterreference = '';
    this.loadDefaultPostings();
    this.router.navigate(['/']);
  }
}
