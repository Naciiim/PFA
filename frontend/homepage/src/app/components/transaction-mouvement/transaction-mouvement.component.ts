import {Component, Input} from '@angular/core';
import {Mouvement} from "../../models/mouvement.model";
import {ExportService} from "../../services/export.service";
import {MatDialog} from "@angular/material/dialog";
import {MouvementDetailsComponent} from "../mouvement-details/mouvement-details.component";

@Component({
  selector: 'app-transaction-mouvement',
  templateUrl: './transaction-mouvement.component.html',
  styleUrls: ['./transaction-mouvement.component.css']
})
export class TransactionMouvementComponent {
@Input() mouvements:Mouvement[]=[];
  constructor(private exportService: ExportService, private dialog: MatDialog) {}
  exportToExcel() {
    this.exportService.exportMvtDataToExcel().subscribe(
      data => {
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'Mouvements.xlsx';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error => {
        console.error("Erreur lors de l'export vers Excel", error);
        alert("Une erreur s'est produite lors de l'exportation des données. Veuillez réessayer.");
      }
    );
  }

  openDialog(mouvement: Mouvement): void {
    this.dialog.open(MouvementDetailsComponent, {
      data: mouvement
    });
  }

}
