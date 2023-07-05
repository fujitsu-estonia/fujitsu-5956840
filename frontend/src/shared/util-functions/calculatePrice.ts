import { Booking } from "../interfaces/Booking"

function validateBooking(booking: Booking) {
	return booking.startDate && booking.endDate && booking.room
}

function calculateDiffInDays(startDate: any, endDate: any) {
	const diffInMs = Math.abs(endDate.getTime() - startDate.getTime());
	return Math.round(diffInMs / (1000 * 60 * 60 * 24));
}

export function calculatePrice(booking: Booking) {
	if (!validateBooking(booking)) return

	const days = calculateDiffInDays(booking.startDate, booking.endDate)
	const price = days * (booking.room?.price as any)
	return price
}