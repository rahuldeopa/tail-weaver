import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewStoryComponent } from './view-story.component';

describe('ViewStoryComponent', () => {
  let component: ViewStoryComponent;
  let fixture: ComponentFixture<ViewStoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewStoryComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ViewStoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
