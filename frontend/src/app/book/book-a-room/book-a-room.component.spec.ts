import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookARoomComponent } from './book-a-room.component';

describe('BookARoomComponent', () => {
  let component: BookARoomComponent;
  let fixture: ComponentFixture<BookARoomComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BookARoomComponent]
    });
    fixture = TestBed.createComponent(BookARoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
