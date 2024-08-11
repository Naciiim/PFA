import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionPostingcreComponent } from './transaction-postingcre.component';

describe('TransactionPostingcreComponent', () => {
  let component: TransactionPostingcreComponent;
  let fixture: ComponentFixture<TransactionPostingcreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransactionPostingcreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionPostingcreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
