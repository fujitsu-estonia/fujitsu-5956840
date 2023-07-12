import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Booking } from 'src/shared/interfaces/Booking';
import { calculatePrice } from 'src/shared/util-functions/calculatePrice';
import { createIdCodeValidator } from 'src/shared/util-functions/idCodeValidator';
import { DatePipe } from '@angular/common';
import { BookingService } from 'src/app/services/booking/booking.service';
import { BookingPost } from 'src/shared/interfaces/BookingPost';

@Component({
  selector: 'app-book-a-room',
  templateUrl: './book-a-room.component.html',
  styleUrls: ['./book-a-room.component.scss']
})
export class BookARoomComponent {
  @Input() booking!: Booking;
  @Input() bookingId!: string
  @Output() bookingIdChange = new EventEmitter<string>()

  @Output() backToSearchPressed = new EventEmitter<void>()
  @Output() bookingDone = new EventEmitter<void>()

  formGroup!: FormGroup

  sendingBooking: boolean = false
  error: boolean = false

  calculatePrice = calculatePrice

  constructor(
    private datePipe: DatePipe,
    private bookingService: BookingService
  ) {
    this.formGroup = new FormGroup({
      firstName: new FormControl("", Validators.required),
      lastName: new FormControl("", Validators.required),
      idCode: new FormControl("", [Validators.required, createIdCodeValidator()]),
      email: new FormControl("", [Validators.required, Validators.email]),
      ignoreIdCode: new FormControl(false),
    })
  }

  goBackToSearch() {
    this.formGroup.markAsUntouched()
    this.error = false
    this.backToSearchPressed.emit()
  }

  goToDoneView() {
    this.error = false
    this.bookingDone.emit()
  }

  validateBooking(booking: Booking) {
    return booking.startDate && booking.endDate && booking.roomDetails
  }

  bookRoom(booking: Booking) {
    this.formGroup.markAllAsTouched()

    if (this.formGroup.valid) {
      if (!this.validateBooking(booking)) return

      //sets the personData property of the booking
      const postData: BookingPost = this.createBookingPostData(booking)

      //TODO: send booking to backend
      this.sendingBooking = true
      this.formGroup.disable()

      this.bookingService.postBooking(postData).subscribe({
        next: (bookingId: string) => {
          this.bookingIdChange.emit(bookingId)
          this.sendingBooking = false
          this.formGroup.enable()
          this.goToDoneView()
        },
        error: (_err) => {
          this.error = true
          this.sendingBooking = false
          this.formGroup.enable()
        }
      })
    }
  }

  createBookingPostData(booking: Booking): BookingPost {
    let postData: BookingPost = {
      roomTypeId: booking.roomDetails?.roomTypeId,
      personData: this.formGroup.value,
      bookingPeriod: {
        startDate: this.datePipe.transform(booking.startDate, 'yyyy-MM-dd')!,
        endDate: this.datePipe.transform(booking.endDate, 'yyyy-MM-dd')!
      }
    }

    return postData
  }

  checkIfIdStillRequired() {
    const idIgnore: boolean = this.formGroup.get('ignoreIdCode')?.value

    if (idIgnore) {
      this.formGroup.get('idCode')?.clearValidators()
      this.formGroup.get('idCode')?.disable()
    } else {
      this.formGroup.get('idCode')?.setValidators([Validators.required, createIdCodeValidator()])
      this.formGroup.get('idCode')?.enable()
    }
  }


}
