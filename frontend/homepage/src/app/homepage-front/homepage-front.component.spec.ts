import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomepageFrontComponent } from './homepage-front.component';

describe('HomepageFrontComponent', () => {
  let component: HomepageFrontComponent;
  let fixture: ComponentFixture<HomepageFrontComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomepageFrontComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HomepageFrontComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
