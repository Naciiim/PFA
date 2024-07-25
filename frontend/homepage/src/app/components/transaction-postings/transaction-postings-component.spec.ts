import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionPostingsComponentComponent } from './transaction-postings-component';

describe('TransactionPostingsComponentComponent', () => {
  let component: TransactionPostingsComponentComponent;
  let fixture: ComponentFixture<TransactionPostingsComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransactionPostingsComponentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionPostingsComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
