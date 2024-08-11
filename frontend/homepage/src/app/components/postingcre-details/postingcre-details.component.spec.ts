import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostingcreDetailsComponent } from './postingcre-details.component';

describe('PostingcreDetailsComponent', () => {
  let component: PostingcreDetailsComponent;
  let fixture: ComponentFixture<PostingcreDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostingcreDetailsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostingcreDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
