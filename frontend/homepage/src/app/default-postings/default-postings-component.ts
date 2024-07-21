import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Posting } from '../models/posting.model';
import {PostingDetailsComponent} from "../posting-details/posting-details.component";

@Component({
  selector: 'app-default-postings',
  templateUrl: './default-postings-component.html',
  styleUrls: ['./default-postings-component.css']
})
export class DefaultPostingsComponent {
  @Input() postings: Posting[] = [];

  constructor(private dialog: MatDialog) {}

  openDialog(posting: Posting): void {
    this.dialog.open(PostingDetailsComponent, {
      data: posting
    });
  }
}
