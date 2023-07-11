import Isikukood, { Gender, PersonalData } from './Isikukood';
import { calculatePrice, calculateDiffInDays, validateBooking } from './calculatePrice';
import { Booking } from '../interfaces/Booking';


describe('Isikukood', () => {
	let isikukood: Isikukood;

	beforeEach(() => {
		isikukood = new Isikukood('49101236023');
	});

	it('should create an instance', () => {
		expect(isikukood).toBeTruthy();
	});

	it('should get the code', () => {
		expect(isikukood.code).toBe('49101236023');
	});

	it('should set the code', () => {
		isikukood.code = '39012345678';
		expect(isikukood.code).toBe('39012345678');
	});

	it('should get the control number', () => {
		const controlNumber = isikukood.getControlNumber();
		expect(controlNumber).toBe(3);
	});

	it('should validate the Estonian personal ID', () => {
		const isValid = isikukood.validate();
		expect(isValid).toBe(true);
	});

	it('should get the gender', () => {
		const gender = isikukood.getGender();
		expect(gender).toBe(Gender.FEMALE);
	});

	it('should get the age', () => {
		const age = isikukood.getAge();
		expect(age).toBeGreaterThan(30);
		expect(age).toBeLessThan(40);
	});

	it('should get the birthday', () => {
		const birthday = isikukood.getBirthday();
		expect(birthday.getFullYear()).toBe(1991);
		expect(birthday.getMonth()).toBe(0); // October (0-based index)
		expect(birthday.getDate()).toBe(23);
	});

	it('should parse the code and return personal data', () => {
		const personalData: PersonalData = isikukood.parse();
		expect(personalData.gender).toBe(Gender.FEMALE);
		expect(personalData.birthDay.getFullYear()).toBe(1991);
		expect(personalData.age).toBeGreaterThan(30);
		expect(personalData.age).toBeLessThan(40);
	});

	it('should generate a valid Estonian personal ID', () => {
		const generatedCode = Isikukood.generate();
		const generatedIsikukood = new Isikukood(generatedCode);
		expect(generatedIsikukood.validate()).toBe(true);
	});
});


describe('booking-utils', () => {
	describe('validateBooking', () => {
		it('should return true if booking has startDate, endDate, and roomDetails', () => {
			const booking: Booking = {
				startDate: new Date(),
				endDate: new Date(),
				roomDetails: { title: "test", pricePerNight: 100, description: "test", bedsCount: 2 },
			};

			expect(validateBooking(booking)).toBeTruthy();
		});

		it('should return false if booking is missing startDate', () => {
			const booking: Booking = {
				endDate: new Date(),
				roomDetails: { title: "test", pricePerNight: 100, description: "test", bedsCount: 2 },
			};

			expect(validateBooking(booking)).toBeFalsy();
		});

		it('should return false if booking is missing endDate', () => {
			const booking: Booking = {
				startDate: new Date(),
				roomDetails: { title: "test", pricePerNight: 100, description: "test", bedsCount: 2 },
			};

			expect(validateBooking(booking)).toBeFalsy();
		});

		it('should return false if booking is missing roomDetails', () => {
			const booking: Booking = {
				startDate: new Date(),
				endDate: new Date(),
			};

			expect(validateBooking(booking)).toBeFalsy();
		});

		it('should return false if booking is missing all properties', () => {
			const booking: Booking = {};

			expect(validateBooking(booking)).toBeFalsy();
		});
	});

	describe('calculateDiffInDays', () => {
		it('should calculate the difference in days between two dates', () => {
			const startDate = new Date('2023-01-01');
			const endDate = new Date('2023-01-05');

			expect(calculateDiffInDays(startDate, endDate)).toBe(4);
		});
	});

	describe('calculatePrice', () => {
		it('should return undefined if booking is invalid', () => {
			const booking: Booking = {};

			expect(calculatePrice(booking)).toBeUndefined();
		});

		it('should calculate the price based on the number of days and price per night', () => {
			const booking: Booking = {
				startDate: new Date('2023-01-01'),
				endDate: new Date('2023-01-05'),
				roomDetails: { title: "test", pricePerNight: 100, description: "test", bedsCount: 2 },
			};

			expect(calculatePrice(booking)).toBe(400);
		});
	});
});