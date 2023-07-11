import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BookingListComponent } from './booking-list.component';
import { BookingService } from 'src/app/services/booking/booking.service';
import { Booking } from 'src/shared/interfaces/Booking';
import { of, throwError } from 'rxjs';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('BookingListComponent', () => {
  let component: BookingListComponent;
  let fixture: ComponentFixture<BookingListComponent>;
  let bookingService: jasmine.SpyObj<BookingService>;

  beforeEach(async () => {
    const bookingServiceSpy = jasmine.createSpyObj('BookingService', ['cancelBooking']);

    await TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [BookingListComponent],
      providers: [
        { provide: BookingService, useValue: bookingServiceSpy }
      ]
    }).compileComponents();

    bookingService = TestBed.inject(BookingService) as jasmine.SpyObj<BookingService>;
    fixture = TestBed.createComponent(BookingListComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should cancel booking and emit bookingCanceled event', () => {
    // Arrange
    const booking: Booking = { id: "test1" };
    bookingService.cancelBooking.and.returnValue(of(null));
    spyOn(component.bookingCanceled, 'emit');

    // Act
    component.cancelBooking(booking);

    // Assert
    expect(bookingService.cancelBooking).toHaveBeenCalledWith('test1');
    expect(component.bookingCanceled.emit).toHaveBeenCalled();
  });

  it('should handle error when canceling booking', () => {
    // Arrange
    const booking: Booking = { id: "test1" };
    const errorResponse = {
      error: {
        errors: [
          'Transaction silently rolled back because it has been marked as rollback-only'
        ]
      }
    };
    bookingService.cancelBooking.and.returnValue(throwError(() => errorResponse));

    // Act
    component.cancelBooking(booking);

    // Assert
    expect(component.error).toBe(true);
    expect(component.errorMessage).toBe('Broneeringut ei saa tühistada kuni 3 päeva enne ööbimise algust!');
  });
});
