import { Room } from "src/app/book/room/room.component";

export interface Booking {
	//booking info
	id?: number;
	startDate?: Date;
	endDate?: Date;
	room?: Room;

	//guest info
	firstName?: string;
	lastName?: string;
	email?: string;
	idCode?: string;
}