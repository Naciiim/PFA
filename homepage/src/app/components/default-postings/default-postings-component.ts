import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PostingDetailsComponent } from '../posting-details/posting-details.component';
import { Posting } from '../../models/posting.model';
import { ExportService } from '../../services/export.service';

@Component({
  selector: 'app-default-postings',
  templateUrl: './default-postings-component.html',
  styleUrls: ['./default-postings-component.css']
})
export class DefaultPostingsComponent {
  @Input() postings: Posting[] = [];

  constructor(private exportService: ExportService, private dialog: MatDialog) {}

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
        a.download = `PostingsEnInstance_${date}.xlsx`;
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
