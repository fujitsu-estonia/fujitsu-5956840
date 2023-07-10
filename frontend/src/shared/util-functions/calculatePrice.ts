import { Booking } from "../interfaces/Booking"

function validateBooking(booking: Booking) {
	return booking.startDate && booking.endDate && booking.roomDetails
}

function calculateDiffInDays(startDate: any, endDate: any) {
	const diffInMs = Math.abs(new Date(endDate).getTime() - new Date(startDate).getTime());
	return Math.round(diffInMs / (1000 * 60 * 60 * 24));
}

export function calculatePrice(booking: Booking) {
	if (!validateBooking(booking)) return

	const days = calculateDiffInDays(booking.startDate, booking.endDate)
	const price = days * (booking.roomDetails?.pricePerNight as any)
	return price
}