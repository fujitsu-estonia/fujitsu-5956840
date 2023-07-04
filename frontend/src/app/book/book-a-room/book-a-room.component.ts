import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Booking } from 'src/shared/interfaces/Booking';

@Component({
  selector: 'app-book-a-room',
  templateUrl: './book-a-room.component.html',
  styleUrls: ['./book-a-room.component.scss']
})
export class BookARoomComponent {
  @Input() booking!: Booking;
  @Output() backToSearchPressed = new EventEmitter<void>()

  formGroup!: FormGroup

  constructor() {
    this.formGroup = new FormGroup({
      firstName: new FormControl("", Validators.required),
      lastName: new FormControl("", Validators.required),
      idCode: new FormControl("", Validators.required),
      email: new FormControl("", [Validators.required, Validators.email]),
    })
  }

  goBackToSearch() {
    this.backToSearchPressed.emit()
  }

  calculateDiffInDays(startDate: any, endDate: any) {
    const diffInMs = Math.abs(endDate.getTime() - startDate.getTime());
    return Math.round(diffInMs / (1000 * 60 * 60 * 24));
  }

  calculatePrice(booking: Booking) {
    if (!this.validateBooking(booking)) return

    const days = this.calculateDiffInDays(booking.startDate, booking.endDate)
    const price = days * (booking.room?.price as any)
    return price
  }

  validateBooking(booking: Booking) {
    return booking.startDate && booking.endDate && booking.room
  }
}
