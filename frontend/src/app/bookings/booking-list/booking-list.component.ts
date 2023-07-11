import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BookingService } from 'src/app/services/booking/booking.service';
import { Booking } from 'src/shared/interfaces/Booking';
import { BookingStatus } from 'src/shared/interfaces/BookingStatus';
import { calculatePrice } from 'src/shared/util-functions/calculatePrice';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.scss']
})
export class BookingListComponent {
  @Input() bookings!: Booking[]
  @Input() adminMode!: boolean
  @Input() loadingBookings!: boolean

  @Output() searchAdminBookings = new EventEmitter<void>()

  @Output() bookingCanceled = new EventEmitter<void>()

  BookingStatus: typeof BookingStatus = BookingStatus

  error: boolean = false
  errorMessage: string = ""

  calculatePrice = calculatePrice

  constructor(
    private bookingService: BookingService
  ) { }

  ngOnInit() {
    if (this.adminMode) {
      //search all bookings
      setTimeout(() => this.searchAdminBookings.emit(), 0)
    }
  }

  cancelBooking(booking: Booking) {
    this.bookingService.cancelBooking(String(booking.id)).subscribe({
      next: (_) => {
        this.bookingCanceled.emit()
      },
      error: (_error) => {
        this.error = true
        const threeDaysError = 'Transaction silently rolled back because it has been marked as rollback-only'
        this.errorMessage = _error.error.errors[0] === threeDaysError ? 'Broneeringut ei saa tühistada kuni 3 päeva enne ööbimise algust!' : _error.error.errors[0]

        document.getElementById('error-msg-booking')?.scrollIntoView({ behavior: 'smooth' })
      }
    })
  }
}
