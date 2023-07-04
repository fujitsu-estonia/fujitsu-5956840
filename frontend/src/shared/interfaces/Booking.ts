import { Room } from "src/app/book/room/room.component";

export interface Booking {
	startDate?: Date;
	endDate?: Date;
	room?: Room;
}