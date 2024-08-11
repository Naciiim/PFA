import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PostingcreComponent } from './postingcre.component';

describe('PostingcreComponent', () => {
  let component: PostingcreComponent;
  let fixture: ComponentFixture<PostingcreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PostingcreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PostingcreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
