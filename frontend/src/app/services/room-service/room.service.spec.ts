import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { RoomService, RoomSearchParams } from './room.service';

describe('RoomService', () => {
  let service: RoomService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [RoomService],
    });

    service = TestBed.inject(RoomService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getRooms', () => {
    it('should send a GET request to the correct API endpoint with the provided searchParams', () => {
      const searchParams: RoomSearchParams = {
        beds: 2,
        dateStart: new Date(),
        dateEnd: new Date(),
      };
      const apiUrl = `http://localhost:8080/api/rooms?&beds=${searchParams.beds}&dateStart=${searchParams.dateStart}&dateEnd=${searchParams.dateEnd}`;

      service.getRooms(searchParams).subscribe();

      const req = httpMock.expectOne(apiUrl);
      expect(req.request.method).toBe('GET');
    });
  });
});