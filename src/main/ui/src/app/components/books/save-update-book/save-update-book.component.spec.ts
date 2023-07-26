import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SaveUpdateBookComponent } from './save-update-book.component';

describe('SaveUpdateBookComponent', () => {
  let component: SaveUpdateBookComponent;
  let fixture: ComponentFixture<SaveUpdateBookComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ SaveUpdateBookComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SaveUpdateBookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
