import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookARoomComponent } from './book-a-room.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';
import { Booking } from 'src/shared/interfaces/Booking';

describe('BookARoomComponent', () => {
  let component: BookARoomComponent;
  let fixture: ComponentFixture<BookARoomComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule],
      declarations: [BookARoomComponent]
    });
    fixture = TestBed.createComponent(BookARoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
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

  it('should return false when validateBooking is called with an incomplete booking', () => {
    const booking: Booking = {
      room: { beds: 1, price: 100, type: 'Test Room' },
      startDate: new Date(),
      endDate: undefined
    };
    const result = component.validateBooking(booking);
    expect(result).toBeFalsy();
  });

  it('should return true when validateBooking is called with a complete booking', () => {
    const booking: Booking = {
      room: { beds: 1, price: 100, type: 'Test Room' },
      startDate: new Date(),
      endDate: new Date()
    };
    const result = component.validateBooking(booking);
    expect(result).toBeTruthy();
  });

  it('should not send a booking and emit bookingDone event when bookRoom is called with invalid form', () => {
    spyOn(component.bookingDone, 'emit');
    component.bookRoom({});
    expect(component.sendingBooking).toBeFalse();
    expect(component.bookingDone.emit).not.toHaveBeenCalled();
  });

});
