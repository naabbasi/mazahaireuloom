import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AllBooksComponent } from './all-books.component';

describe('AllBooksComponent', () => {
  let component: AllLibrariesComponent;
  let fixture: ComponentFixture<AllLibrariesComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AllLibrariesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllLibrariesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
