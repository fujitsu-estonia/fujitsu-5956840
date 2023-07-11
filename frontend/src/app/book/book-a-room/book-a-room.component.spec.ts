import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookARoomComponent } from './book-a-room.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';
import { DatePipe } from '@angular/common';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormControl, FormGroup } from '@angular/forms';
import { BookingPost } from 'src/shared/interfaces/BookingPost';
import { of } from 'rxjs';
import { BookingService } from 'src/app/services/booking/booking.service';

describe('BookARoomComponent', () => {
  let component: BookARoomComponent;
  let fixture: ComponentFixture<BookARoomComponent>;
  let mockBookingService = jasmine.createSpyObj<BookingService>('BookingService', ['postBooking']);
  const mockBookingId = 'abc123';
  mockBookingService.postBooking.and.returnValue(of(mockBookingId));

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule, HttpClientTestingModule],
      declarations: [BookARoomComponent],
      providers: [DatePipe, { provide: BookingService, useValue: mockBookingService }]
    });
    fixture = TestBed.createComponent(BookARoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  it('should emit backToSearchPressed event when goBackToSearch is called', () => {
    spyOn(component.backToSearchPressed, 'emit');

    component.goBackToSearch();

    expect(component.backToSearchPressed.emit).toHaveBeenCalled();
  });

  it('should emit bookingDone event when goToDoneView is called', () => {
    spyOn(component.bookingDone, 'emit');

    component.goToDoneView();

    expect(component.bookingDone.emit).toHaveBeenCalled();
  });

  it('should return true when booking has startDate, endDate, and roomDetails', () => {
    const booking = { startDate: '2023-07-11', endDate: '2023-07-13', roomDetails: { title: "test", bedsCount: 1, pricePerNight: 79, freeRooms: 5 } };

    const result = component.validateBooking(booking);

    expect(result).toBeTruthy();
  });

  it('should create a valid BookingPost object', () => {
    const booking = {
      startDate: '2023-07-11',
      endDate: '2023-07-13',
      roomDetails: { title: "test", bedsCount: 2, pricePerNight: 79, freeRooms: 5 }
    };

    const firstNameControl = new FormControl('John');
    const lastNameControl = new FormControl('Doe');
    const idCodeControl = new FormControl('123456789');
    const emailControl = new FormControl('test@example.com');
    const ignoreIdCodeControl = new FormControl(false);

    component.formGroup = new FormGroup({
      firstName: firstNameControl,
      lastName: lastNameControl,
      idCode: idCodeControl,
      email: emailControl,
      ignoreIdCode: ignoreIdCodeControl
    });

    const expectedPostData: BookingPost = {
      roomTypeId: 2,
      personData: {
        firstName: 'John',
        lastName: 'Doe',
        idCode: '123456789',
        email: 'test@example.com',
        ignoreIdCode: false
      },
      bookingPeriod: {
        startDate: '2023-07-11',
        endDate: '2023-07-13'
      },
    };

    const postData = component.createBookingPostData(booking);

    expect(postData).toEqual(expectedPostData);
  });

  it('should disable and enable the idCode form control based on ignoreIdCode value', () => {
    const ignoreIdCodeControl = new FormControl(false);
    component.formGroup = new FormGroup({
      ignoreIdCode: ignoreIdCodeControl,
      idCode: new FormControl('')
    });

    // Initial state
    expect(component.formGroup.get('idCode')?.enabled).toBe(true);

    // Ignore ID code
    ignoreIdCodeControl.setValue(true);
    component.checkIfIdStillRequired();
    expect(component.formGroup.get('idCode')?.enabled).toBe(false);

    // Do not ignore ID code
    ignoreIdCodeControl.setValue(false);
    component.checkIfIdStillRequired();
    expect(component.formGroup.get('idCode')?.enabled).toBe(true);
  });

});
