import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { BookingService, PostBookingSearchParams } from './booking.service';

describe('BookingService', () => {
  let service: BookingService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [BookingService],
    });

    service = TestBed.inject(BookingService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getBookingById', () => {
    it('should send a GET request to the correct API endpoint', () => {
      const bookingId = '123';
      const apiUrl = `http://localhost:8080/api/booking/${bookingId}`;

      service.getBookingById(bookingId).subscribe();

      const req = httpMock.expectOne(apiUrl);
      expect(req.request.method).toBe('GET');
    });
  });

  describe('getBookings', () => {
    it('should send a POST request to the correct API endpoint with the provided params', () => {
      const params: PostBookingSearchParams = {
        bookingStatus: 'ACCEPTED',
        fromDate: new Date(),
        toDate: new Date(),
      };
      const apiUrl = 'http://localhost:8080/api/booking/search';

      service.getBookings(params).subscribe();

      const req = httpMock.expectOne(apiUrl);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(params);
    });
  });

  describe('postBooking', () => {
    it('should send a POST request to the correct API endpoint with the provided booking', () => {
      const booking = { /* Create a Booking object with necessary properties */ };
      const apiUrl = 'http://localhost:8080/api/booking';

      service.postBooking(booking).subscribe();

      const req = httpMock.expectOne(apiUrl);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual(booking);
    });
  });

  describe('cancelBooking', () => {
    it('should send a POST request to the correct API endpoint with the provided bookingId and headers', () => {
      const bookingId = '123';
      const apiUrl = `http://localhost:8080/api/booking/cancel/${bookingId}`;

      service.cancelBooking(bookingId).subscribe();

      const req = httpMock.expectOne(apiUrl);
      expect(req.request.method).toBe('POST');
    });
  });
});