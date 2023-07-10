import { Component } from '@angular/core';
import { Room } from './room/room.component';
import { Booking } from 'src/shared/interfaces/Booking';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { DateRange } from './room-search/room-search.component';
import { RoomSearchParams } from 'src/shared/interfaces/RoomSearchParams';
import { RoomService } from '../services/room-service/room.service';
import { DatePipe } from '@angular/common';

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

  booking: Booking = {}

  //render booleans
  hasBeenSearchedOnce: boolean = false
  loading: boolean = false

  bookingId!: string

  constructor(
    private roomService: RoomService,
    private datePipe: DatePipe,
  ) {
    this.formGroup = new FormGroup({
      dateStart: new FormControl('', Validators.required),
      dateEnd: new FormControl('', Validators.required),
      beds: new FormControl('', Validators.required),
    }, this.rangeValidator())
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

  rangeValidator() {
    return (control: AbstractControl) => {
      const start = control.get('dateStart')?.value || null
      const end = control.get('dateEnd')?.value || null

      return start && end && end.getTime() === start.getTime() ?
        { equal: true } : null
    }
  }

  setBookingDateRange(dateRange: DateRange) {
    this.booking = {
      roomDetails: this.booking.roomDetails,
      startDate: dateRange.dateStart,
      endDate: dateRange.dateEnd
    }
  }

  searchForRooms(obj: RoomSearchParams) {
    //format dates
    obj.dateStart = this.datePipe.transform(obj.dateStart, 'yyyy-MM-dd')!
    obj.dateEnd = this.datePipe.transform(obj.dateEnd, 'yyyy-MM-dd')!

    this.loading = true
    this.hasBeenSearchedOnce = true

    this.roomService.getRooms(obj).subscribe(rooms => {
      this.loading = false
      this.roomResults = rooms
    })
  }
}
