import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransactionMouvementComponent } from './transaction-mouvement.component';

describe('TransactionMouvementComponent', () => {
  let component: TransactionMouvementComponent;
  let fixture: ComponentFixture<TransactionMouvementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransactionMouvementComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransactionMouvementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
