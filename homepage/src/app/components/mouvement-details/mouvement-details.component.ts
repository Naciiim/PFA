import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Mouvement} from "../../models/mouvement.model";

@Component({
  selector: 'app-mouvement-details',
  templateUrl: './mouvement-details.component.html',
  styleUrls: ['./mouvement-details.component.css']
})
export class MouvementDetailsComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Mouvement,
              private dialogRef: MatDialogRef<MouvementDetailsComponent>
  ) {}
  closeDialog(): void {
    this.dialogRef.close();
  }

}
