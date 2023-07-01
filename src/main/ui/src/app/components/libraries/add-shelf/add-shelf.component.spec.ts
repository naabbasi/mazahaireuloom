import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AddShelfComponent } from './add-shelf.component';

describe('AddShelfComponent', () => {
  let component: AddShelfComponent;
  let fixture: ComponentFixture<AddShelfComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ AddShelfComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddShelfComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
