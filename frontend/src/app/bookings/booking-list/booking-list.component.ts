import { Component, Input } from '@angular/core';
import { RoomTypeToImgMap } from 'src/app/book/room/RoomTypeToImgMap';
import { Booking } from 'src/shared/interfaces/Booking';
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

  calculatePrice = calculatePrice
  RoomTypeToImgMap: any = RoomTypeToImgMap

  ngOnInit() {
    if (this.adminMode) {
      //search all bookings
      console.log("searching all bookings")
    }
  }
}