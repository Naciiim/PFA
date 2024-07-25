import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostingDetailsComponent } from './posting-details.component';

describe('PostingDetailsComponent', () => {
  let component: PostingDetailsComponent;
  let fixture: ComponentFixture<PostingDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostingDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostingDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
