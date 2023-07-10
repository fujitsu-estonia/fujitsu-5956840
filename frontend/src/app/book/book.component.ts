import { Component } from '@angular/core';
import { Room } from './room/room.component';
import { Booking } from 'src/shared/interfaces/Booking';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DateRange } from './room-search/room-search.component';

const mockRooms: Room[] = [
  { title: "Ühekohaline klassik tuba", pricePerNight: 98, bedsCount: 1 },
  { title: "Kahekohaline klassik tuba", pricePerNight: 148, bedsCount: 2 },
  { title: "Kolmekohaline klassik tuba", pricePerNight: 197, bedsCount: 3 }
]

export enum BookView {
  search = 'search',
  book = 'book',
  done = 'done'
}

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent {

  formGroup!: FormGroup
  view: BookView = BookView.search
  BookView: typeof BookView = BookView

  roomResults!: Room[]

  booking: Booking = {
    roomDetails: {
      bedsCount: 1,
      pricePerNight: 98,
      title: "Ühekohaline klassik tuba"
    },
    startDate: new Date((new Date().getTime() - 1000 * 60 * 60 * 24 * 2)),
    endDate: new Date()
  }

  //render booleans
  hasBeenSearchedOnce: boolean = false
  loading: boolean = false

  bookingId!: string

  constructor() {
    this.formGroup = new FormGroup({
      dateStart: new FormControl('', Validators.required),
      dateEnd: new FormControl('', Validators.required),
      roomType: new FormControl('', Validators.required),
    })
  }

  bookRoom(room: Room) {
    this.booking = {
      roomDetails: room,
      startDate: this.booking.startDate,
      endDate: this.booking.endDate
    }

    this.view = BookView.book
    window.scrollTo(0, 0)
  }

  setViewToSearch() {
    this.view = BookView.search
  }

  setViewToDone() {
    this.view = BookView.done
  }

  setBookingDateRange(dateRange: DateRange) {
    this.booking = {
      roomDetails: this.booking.roomDetails,
      startDate: dateRange.dateStart,
      endDate: dateRange.dateEnd
    }
  }

  searchForRooms(obj: any) {
    console.log("Search for rooms, params: ", obj)
    this.loading = true
    this.hasBeenSearchedOnce = true

    // mock search
    setTimeout(() => {
      this.loading = false
      this.roomResults = mockRooms
    }, 1000)
  }
}
