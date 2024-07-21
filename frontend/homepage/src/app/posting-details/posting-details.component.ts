import { Component, Inject } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import { Posting } from '../models/posting.model';

@Component({
  selector: 'app-posting-details',
  templateUrl: './posting-details.component.html',
  styleUrls: ['./posting-details.component.css']
})
export class PostingDetailsComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data: Posting,
              private dialogRef: MatDialogRef<PostingDetailsComponent>
  ) {}
  closeDialog(): void {
    this.dialogRef.close();
  }
}
