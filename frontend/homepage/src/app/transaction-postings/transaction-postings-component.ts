import { Component, Input } from '@angular/core';
import { Posting } from '../models/posting.model';
import { ExportService } from '../services/export.service';
import {PostingDetailsComponent} from "../posting-details/posting-details.component";
import {MatDialog} from "@angular/material/dialog";

@Component({
  selector: 'app-transaction-postings',
  templateUrl: './transaction-postings-component.html',
  styleUrls: ['./transaction-postings-component.css']
})
export class TransactionPostingsComponent {
  @Input() postings: Posting[] = [];

  constructor(private exportService: ExportService,
              private dialog: MatDialog) {}


  exportToExcel() {
    this.exportService.exportPostingsToExcel().subscribe(
      (data: Blob) => {
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'postings.xlsx';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error => {
        console.error('Erreur lors de l\'export vers Excel', error);
        alert('Une erreur s\'est produite lors de l\'exportation des données. Veuillez réessayer.');
      }
    );
  }
  openDialog(posting: Posting): void {
    this.dialog.open(PostingDetailsComponent, {
      data: posting
    });
  }
}
