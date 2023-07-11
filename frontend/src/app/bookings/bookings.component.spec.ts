import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { Booking } from 'src/shared/interfaces/Booking';
import { BookingService } from '../services/booking/booking.service';
import { BookingsComponent } from './bookings.component';
import { BookingSearchComponent } from './booking-search/booking-search.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';
import { BookingListComponent } from './booking-list/booking-list.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('BookingsComponent', () => {
  let component: BookingsComponent;
  let fixture: ComponentFixture<BookingsComponent>;
  let bookingService: jasmine.SpyObj<BookingService>;
  let router: jasmine.SpyObj<Router>;

  beforeEach(() => {
    const bookingServiceSpy = jasmine.createSpyObj('BookingService', ['getBookingById', 'getBookings']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);

    TestBed.configureTestingModule({
      imports: [AngularMaterialModule, BrowserAnimationsModule],
      declarations: [BookingsComponent, BookingSearchComponent, BookingListComponent],
      providers: [
        { provide: ActivatedRoute, useValue: { params: of({ id: '123' }) } },
        { provide: BookingService, useValue: bookingServiceSpy },
        { provide: Router, useValue: routerSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(BookingsComponent);
    component = fixture.componentInstance;
    bookingService = TestBed.inject(BookingService) as jasmine.SpyObj<BookingService>;


    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize adminMode to false', () => {
    expect(component.adminMode).toBeFalse();
  });

  it('should initialize loading to false', () => {
    expect(component.loading).toBeFalse();
  });

  it('should initialize bookings to an empty array', () => {
    expect(component.bookings).toEqual([]);
  });

  it('should initialize error to false', () => {
    expect(component.error).toBeFalse();
  });

  describe('searchBooking', () => {
    const bookingId = '123';
    const booking: Booking = { /* create a sample booking object */ };

    beforeEach(() => {
      bookingService.getBookingById.and.returnValue(of(booking));
    });

    it('should call the bookingService to get the booking by ID', () => {
      component.searchBooking(bookingId);

      expect(bookingService.getBookingById).toHaveBeenCalledWith(bookingId);
    });

    it('should set the bookings array with the retrieved booking', () => {
      component.searchBooking(bookingId);

      expect(component.bookings).toEqual([booking]);
    });

    it('should set loading to false', () => {
      component.searchBooking(bookingId);

      expect(component.loading).toBeFalse();
    });

    it('should set error to true if an error occurs', () => {
      bookingService.getBookingById.and.returnValue(throwError(() => new Error()));

      component.searchBooking(bookingId);

      expect(component.error).toBeTrue();
    });
  });

  describe('searchAllBookings', () => {
    const bookings: Booking[] = [/* create a sample array of bookings */];

    beforeEach(() => {
      bookingService.getBookings.and.returnValue(of(bookings));
    });

    it('should call the bookingService to get all bookings', () => {
      component.searchAllBookings();

      expect(bookingService.getBookings).toHaveBeenCalledWith({});
    });

    it('should set the bookings array with the retrieved bookings', () => {
      component.searchAllBookings();

      expect(component.bookings).toEqual(bookings);
    });

    it('should set loading to false', () => {
      component.searchAllBookings();

      expect(component.loading).toBeFalse();
    });

    it('should set error to true if an error occurs', () => {
      bookingService.getBookings.and.returnValue(throwError(() => new Error()));

      component.searchAllBookings();

      expect(component.error).toBeTrue();
    });
  });

  describe('reloadBooking', () => {
    it('should call searchAllBookings if adminMode is true', () => {
      component.adminMode = true;
      spyOn(component, 'searchAllBookings');

      component.reloadBooking();

      expect(component.searchAllBookings).toHaveBeenCalled();
    });

    it('should call searchBooking with bookingId if adminMode is false', () => {
      component.adminMode = false;
      component.bookingId = '123';
      spyOn(component, 'searchBooking');

      component.reloadBooking();

      expect(component.searchBooking).toHaveBeenCalledWith('123');
    });
  });
});