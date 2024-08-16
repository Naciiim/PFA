import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultMovementsComponent } from './default-movements.component';

describe('DefaultMovementsComponent', () => {
  let component: DefaultMovementsComponent;
  let fixture: ComponentFixture<DefaultMovementsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DefaultMovementsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DefaultMovementsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
