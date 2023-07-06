import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookComponent, BookView } from './book.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';
import { RoomSearchComponent } from './room-search/room-search.component';
import { BookARoomComponent } from './book-a-room/book-a-room.component';
import { BookingDoneComponent } from './booking-done/booking-done.component';
import { VeeraErrorComponent } from '../shared-components/veera-error/veera-error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Room } from './room/room.component';

describe('BookComponent', () => {
  let component: BookComponent;
  let fixture: ComponentFixture<BookComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule, BrowserAnimationsModule],
      declarations: [BookComponent, RoomSearchComponent, BookARoomComponent, BookingDoneComponent, VeeraErrorComponent]
    });
    fixture = TestBed.createComponent(BookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should set the initial view to BookView.search', () => {
    expect(component.view).toEqual(BookView.search);
  });

  it('should set the view to BookView.search', () => {
    component.setViewToSearch();
    expect(component.view).toEqual(BookView.search);
  });

  it('should set the view to BookView.done', () => {
    component.setViewToDone();
    expect(component.view).toEqual(BookView.done);
  });

  it('should update booking with the selected room', () => {
    const room: Room = { type: 'Test Room', price: 100, beds: 2 };
    component.bookRoom(room);
    expect(component.booking.room).toEqual(room);
  });

  it('should update booking with the selected date range', () => {
    const dateRange = { dateStart: new Date(), dateEnd: new Date() };
    component.setBookingDateRange(dateRange);
    expect(component.booking.startDate).toEqual(dateRange.dateStart);
    expect(component.booking.endDate).toEqual(dateRange.dateEnd);
  });
});
