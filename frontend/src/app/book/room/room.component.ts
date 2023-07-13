import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Booking } from 'src/shared/interfaces/Booking';
import { calculatePrice } from 'src/shared/util-functions/calculatePrice';

export interface Room {
  title: string;
  description?: string;
  bedsCount: number;
  pricePerNight: number;
  previewPictureUrl?: string;
  roomNumber?: any,
  freeRooms: number,
  roomTypeId?: number
}

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss']
})
export class RoomComponent {
  @Input() room!: Room;
  @Input() booking!: Booking;
  bookingCopy!: Booking
  @Output() bookRoomPressed = new EventEmitter<Room>()

  ngOnInit(): void {
    this.bookingCopy = structuredClone(this.booking)
    this.bookingCopy.roomDetails = this.room
  }

  calculatePrice = calculatePrice;

  bookRoom(room: Room) {
    this.bookRoomPressed.emit(room)
  }

}
