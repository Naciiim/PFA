import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {PostingCre} from "../../models/postingcre.model";

@Component({
  selector: 'app-postingcre-details',
  templateUrl: './postingcre-details.component.html',
  styleUrls: ['./postingcre-details.component.css']
})
export class PostingcreDetailsComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: PostingCre,
              private dialogRef: MatDialogRef<PostingcreDetailsComponent>
  ) {}
  closeDialog(): void {
    this.dialogRef.close();
  }

}
