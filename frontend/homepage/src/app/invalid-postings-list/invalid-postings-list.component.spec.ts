import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InvalidPostingsListComponent } from './invalid-postings-list.component';

describe('InvalidPostingsListComponent', () => {
  let component: InvalidPostingsListComponent;
  let fixture: ComponentFixture<InvalidPostingsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InvalidPostingsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InvalidPostingsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
