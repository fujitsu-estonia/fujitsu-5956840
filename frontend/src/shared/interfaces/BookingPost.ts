import { PersonData } from "./PersonData";

export interface BookingPost {
	//booking info
	id?: string;

	bookingPeriod: {
		startDate: Date | string;
		endDate: Date | string;
	}

	//roomType
	roomTypeId?: number;

	//guest info
	personData?: PersonData;
}