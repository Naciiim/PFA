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
  defaultPostings: Posting[] = [];
  transactionPostings: Posting[] = [];
  errorMessage: string = '';

  constructor(private postingService: PostingService, private router: Router) {}

  ngOnInit() {
    this.loadDefaultPostings();
  }

  loadDefaultPostings() {
    const defaultRequest = { transactionid: '', ref: '', event: '' };
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
    if (!this.transactionid) {
      this.errorMessage = 'L\'ID de transaction est requis';
      return;
    }

    const searchRequest = { transactionid: this.transactionid, ref: '', event: '' };
    this.postingService.getPostings(searchRequest).subscribe(
      response => {
        console.log('Réponse du serveur pour les postings de la transaction :', response);
        if (response) {
          // Filtrer les postings par transactionid
          const filteredPostingsWithTEtat = (response.POSTINGSEARCHED || []).filter((posting: Posting) => posting.transactionid === this.transactionid);
          const filteredPostingsWithDifferentEtat = (response.postingWithDiffEtat || []).filter((posting: Posting) => posting.transactionid === this.transactionid);

          const allPostings = [...filteredPostingsWithTEtat, ...filteredPostingsWithDifferentEtat];

          if (allPostings.length > 0) {
            this.transactionPostings = allPostings;
            this.errorMessage = '';
          } else {
            this.transactionPostings = [];
            this.errorMessage = 'Aucun posting trouvé pour l\'ID de transaction donné';
          }

          console.log('Tous les postings pour transactionId:', this.transactionid, allPostings);

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
    this.loadDefaultPostings();
    this.router.navigate(['/']);
  }
}
