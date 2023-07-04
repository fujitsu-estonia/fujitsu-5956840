import { Component } from '@angular/core';
import { Room } from './room/room.component';
import { Booking } from 'src/shared/interfaces/Booking';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { DateRange } from './room-search/room-search.component';

const mockRooms: Room[] = [
  { type: "Ühekohaline klassik tuba", price: 98, beds: 1 },
  { type: "Kahekohaline klassik tuba", price: 148, beds: 2 },
  { type: "Kolmekohaline klassik tuba", price: 197, beds: 3 }
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
    room: {
      beds: 1,
      price: 98,
      type: "Ühekohaline klassik tuba"
    },
    startDate: new Date((new Date().getTime() - 1000 * 60 * 60 * 24 * 2)),
    endDate: new Date()
  }

  bookingId: string = "123456789"

  //render booleans
  hasBeenSearchedOnce: boolean = false
  loading: boolean = false

  constructor() {
    this.formGroup = new FormGroup({
      dateStart: new FormControl('', Validators.required),
      dateEnd: new FormControl('', Validators.required),
      roomType: new FormControl('', Validators.required),
    })
  }

  bookRoom(room: Room) {
    this.booking = {
      room: room,
      startDate: this.booking.startDate,
      endDate: this.booking.endDate
    }

    this.view = BookView.book
  }

  setViewToSearch() {
    this.view = BookView.search
  }

  setViewToDone() {

    this.view = BookView.done
  }

  setBookingDateRange(dateRange: DateRange) {
    this.booking = {
      room: this.booking.room,
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
