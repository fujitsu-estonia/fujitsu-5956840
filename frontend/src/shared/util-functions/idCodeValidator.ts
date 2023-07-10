import { AbstractControl, ValidationErrors } from "@angular/forms";
import Isikukood, { Gender } from "./Isikukood";

export function createIdCodeValidator() {
	return (control: AbstractControl): ValidationErrors | null => {

		const value = control.value;

		if (!value) {
			return null;
		}

		const isValidIdCode = testId(value)

		return !isValidIdCode ? { validIdCode: true } : null;
	}
}

function testId(kood: string) {
	const id = new Isikukood(kood)
	console.log(Isikukood.generate({
		gender: 'female' as Gender,
		birthDay: 23,
		birthMonth: 1,
		birthYear: 1991,
	}))
	return id.validate()
}

