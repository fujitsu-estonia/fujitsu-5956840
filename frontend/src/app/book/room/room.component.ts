import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RoomTypeToImgMap } from './RoomTypeToImgMap';

export interface Room {
  type: string;
  beds: number;
  price: number;
}

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss']
})
export class RoomComponent {
  @Input() room!: Room;
  @Output() bookRoomPressed = new EventEmitter<Room>()

  RoomTypeToImgMap: any = RoomTypeToImgMap

  bookRoom(room: Room) {
    this.bookRoomPressed.emit(room)
  }

}
