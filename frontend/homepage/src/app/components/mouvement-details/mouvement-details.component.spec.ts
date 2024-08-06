import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MouvementDetailsComponent } from './mouvement-details.component';

describe('MouvementDetailsComponent', () => {
  let component: MouvementDetailsComponent;
  let fixture: ComponentFixture<MouvementDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MouvementDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MouvementDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
