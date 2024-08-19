import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { PostingcreDetailsComponent } from '../postingcre-details/postingcre-details.component';
import { PostingCre } from '../../models/postingcre.model';
import { ExportService } from '../../services/export.service';

@Component({
  selector: 'app-default-postingcre',
  templateUrl: './default-postingcre.component.html',
  styleUrls: ['./default-postingcre.component.css']
})
export class DefaultPostingcreComponent {
  @Input() postingCres: PostingCre[] = [];

  constructor(private exportService: ExportService, private dialog: MatDialog) {}

  exportToExcel() {
    this.exportService.exportCreDataToExcel().subscribe(
      (data: Blob) => {
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        const temps=new Date();
        const jour=temps.toLocaleDateString('fr-FR').replace(/\//g, '-');
        const heure=temps.toTimeString().slice(0, 8).replace(/:/g, '-');
        const date=`${jour} at ${heure}`
        a.href = url;
        a.download = `CresEnInstance_${date}.xlsx`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error => {
        console.error('Erreur lors de l\'export vers Excel', error);
        alert('Une erreur s\'est produite lors de l\'exportation des données. Veuillez réessayer.');
      }
    );
  }

  openDialog(posting: PostingCre): void {
    this.dialog.open(PostingcreDetailsComponent, {
      data: posting
    });
  }
}
