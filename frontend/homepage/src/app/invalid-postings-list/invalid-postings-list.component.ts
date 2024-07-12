import { Component, Input, OnInit } from '@angular/core';
import { PostingModel } from '../models/posting.model';

@Component({
  selector: 'app-invalid-postings-list',
  templateUrl: './invalid-postings-list.component.html',
  styleUrls: ['./invalid-postings-list.component.css']
})
export class InvalidPostingsListComponent implements OnInit {
  @Input() invalidPostings: PostingModel[] = [];

  ngOnInit(): void {}
}
