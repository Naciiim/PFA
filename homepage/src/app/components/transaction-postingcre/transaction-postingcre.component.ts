import {Component, Input} from '@angular/core';
import {PostingCre} from "../../models/postingcre.model";
import {ExportService} from "../../services/export.service";
import {MatDialog} from "@angular/material/dialog";
import {PostingcreDetailsComponent} from "../postingcre-details/postingcre-details.component";

@Component({
  selector: 'app-transaction-postingcre',
  templateUrl: './transaction-postingcre.component.html',
  styleUrls: ['./transaction-postingcre.component.css']
})
export class TransactionPostingcreComponent {
  @Input() postingCres:PostingCre[]=[];
  errorMessage: string='';
  constructor(protected exportService: ExportService, private dialog: MatDialog) {}
  exportCreToExcel() {
    this.exportService.exportCreDataToExcel().subscribe(
      data => {
        const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'PostingCre.xlsx';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error => {
        console.error("Erreur lors de l'export vers Excel", error);
        alert("Une erreur s'est produite lors de l'exportation des données. Veuillez réessayer.");
      }
    );
  }
  dismissError() {
    this.errorMessage = '';

  }


  openDialog(postingCre: PostingCre): void {
    this.dialog.open(PostingcreDetailsComponent, {
      data: postingCre
    });
  }

}
