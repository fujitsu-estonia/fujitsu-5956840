import { AbstractControl, ValidationErrors } from "@angular/forms";
import Isikukood from "./Isikukood";

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
	return id.validate()
}

