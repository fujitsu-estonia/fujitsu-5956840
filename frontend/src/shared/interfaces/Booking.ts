import { Room } from "src/app/book/room/room.component";
import { PersonData } from "./PersonData";
import { BookingStatus } from "./BookingStatus";

export interface Booking {
	//booking info
	id?: string;

	startDate?: Date | string;
	endDate?: Date | string;
	roomDetails?: Room;

	//guest info
	personData?: PersonData;

	status?: BookingStatus;
	previewPictureUrl?: string;
}