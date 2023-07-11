import { Component } from '@angular/core';
import { Booking } from 'src/shared/interfaces/Booking';
import { ActivatedRoute, Router } from '@angular/router';
import { BaseComponent } from 'src/shared/components/BaseComponents';
import { BookingService } from '../services/booking/booking.service';

@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html'
})
export class BookingsComponent extends BaseComponent {
  adminMode: boolean = false
  bookingId: any = undefined

  loading: boolean = false

  //mock
  bookings: Booking[] = []

  error: boolean = false

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookingService: BookingService,
  ) {
    super()

    if (this.router?.url?.includes('admin')) this.adminMode = true

    this.subscriptions.push(this.route.params.subscribe(params => {
      if (params["id"]) {
        this.bookingId = params["id"]
      }
    }))
  }

  searchBooking(bookingId: string) {
    this.bookingId = bookingId
    this.loading = true
    this.error = false

    this.bookingService.getBookingById(bookingId).subscribe({
      next: (booking: Booking) => {
        this.bookings = [booking]

        this.loading = false
      },

      error: (error: any) => {
        this.error = true
      }
    })
  }

  searchAllBookings() {
    this.loading = true
    this.error = false

    this.bookingService.getBookings({}).subscribe({
      next: (bookings: Booking[]) => {
        this.bookings = bookings

        this.loading = false
      },

      error: (error: any) => {
        this.error = true
      }
    })
  }


  reloadBooking() {
    if (this.adminMode) {
      this.searchAllBookings()
    } else {
      this.searchBooking(this.bookingId)
    }
  }
}
