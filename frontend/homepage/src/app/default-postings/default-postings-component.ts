import { Component, Input } from '@angular/core';
import { Posting } from '../models/posting.model';

@Component({
  selector: 'app-default-postings',
  templateUrl: './default-postings-component.html',
  styleUrls: ['./default-postings-component.css']
})
export class DefaultPostingsComponent {
  @Input() postings: Posting[] = [];
}
