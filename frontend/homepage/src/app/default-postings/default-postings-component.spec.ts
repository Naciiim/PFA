import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultPostingsComponent } from './default-postings-component';

describe('DefaultPostingsComponent', () => {
  let component: DefaultPostingsComponent;
  let fixture: ComponentFixture<DefaultPostingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DefaultPostingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DefaultPostingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
