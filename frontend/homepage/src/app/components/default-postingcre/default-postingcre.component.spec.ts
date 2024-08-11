import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefaultPostingcreComponent } from './default-postingcre.component';

describe('DefaultPostingcreComponent', () => {
  let component: DefaultPostingcreComponent;
  let fixture: ComponentFixture<DefaultPostingcreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DefaultPostingcreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DefaultPostingcreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
