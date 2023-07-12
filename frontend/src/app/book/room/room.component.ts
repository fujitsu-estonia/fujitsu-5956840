import { Component, EventEmitter, Input, Output } from '@angular/core';

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
  @Output() bookRoomPressed = new EventEmitter<Room>()

  bookRoom(room: Room) {
    this.bookRoomPressed.emit(room)
  }

}
