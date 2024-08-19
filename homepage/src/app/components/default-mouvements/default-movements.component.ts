import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MouvementDetailsComponent } from '../mouvement-details/mouvement-details.component';
import { ExportService } from '../../services/export.service';
import {Mouvement} from "../../models/mouvement.model";

@Component({
  selector: 'app-default-mouvements',
  templateUrl: './default-mouvements.component.html',
  styleUrls: ['./default-mouvements.component.css']
})
export class DefaultMovementsComponent {
  @Input() mouvements: Mouvement[] = [];

  constructor(private exportService: ExportService, private dialog: MatDialog) {}

  exportToExcel() {
    this.exportService.exportMvtDataToExcel().subscribe(
      (data: Blob) => {
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        const temps=new Date();
        const jour=temps.toLocaleDateString('fr-FR').replace(/\//g, '-');
        const heure=temps.toTimeString().slice(0, 8).replace(/:/g, '-');
        const date=`${jour} at ${heure}`
        a.href = url;
        a.download = `MouvementsEnInstance_${date}.xlsx`;
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error => {
        console.error('Erreur lors de l\'export vers Excel', error);
        alert('Une erreur s\'est produite lors de l\'exportation des données. Veuillez réessayer.');
      }
    );
  }

  openDialog(mouvement: Mouvement): void {
    this.dialog.open(MouvementDetailsComponent, {
      data: mouvement
    });
  }
}
