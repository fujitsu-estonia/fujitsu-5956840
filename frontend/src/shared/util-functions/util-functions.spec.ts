import Isikukood, { Gender, PersonalData } from './Isikukood';

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
