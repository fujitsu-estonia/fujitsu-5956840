import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingListComponent } from './booking-list.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('BookingListComponent', () => {
  let component: BookingListComponent;
  let fixture: ComponentFixture<BookingListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [BookingListComponent]
    });
    fixture = TestBed.createComponent(BookingListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
