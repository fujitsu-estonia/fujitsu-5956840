import { Component } from '@angular/core';
import { Booking } from 'src/shared/interfaces/Booking';
import { ActivatedRoute, Router } from '@angular/router';
import { BaseComponent } from 'src/shared/components/BaseComponents';

const mockBooking = {
  id: 1234,
  startDate: new Date((new Date().getTime() - 1000 * 60 * 60 * 24 * 2)),
  endDate: new Date(),
  room: {
    type: 'Ühekohaline klassik tuba',
    price: 79,
    beds: 1,
  },

  //guest info
  firstName: "Sander",
  lastName: "Ruusmaa",
  email: "san.maa@gmail.com",
  idCode: "50010140866",
}

@Component({
  selector: 'app-bookings',
  templateUrl: './bookings.component.html',
  styleUrls: ['./bookings.component.scss']
})
export class BookingsComponent extends BaseComponent {
  adminMode: boolean = false
  bookingId: any = undefined

  loading: boolean = false

  //mock
  bookings: Booking[] = []

  constructor(private route: ActivatedRoute, private router: Router) {
    super()

    if (this.router.url.includes('admin')) this.adminMode = true

    this.subscriptions.push(this.route.params.subscribe(params => {
      if (params["id"]) {
        this.bookingId = params["id"]
      }
    }))
  }


  searchBooking(bookingId: string) {
    this.loading = true
    console.log("search for booking, ID: ", bookingId)
    setTimeout(() => {
      this.loading = false
      this.bookings = [mockBooking]
    }, 1000)
  }

  searchAllBookings() {
    this.loading = true
    console.log("search for all bookings")
    setTimeout(() => {
      this.loading = false
      this.bookings = [mockBooking]
    }, 1000)
  }
}
