import { AbstractControl, ValidationErrors } from "@angular/forms";

const idCodeRegex = /^[1-6]\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2]\d|3[0-1])\d{4}$/

export function createIdCodeValidator() {
	return (control: AbstractControl): ValidationErrors | null => {

		const value = control.value;

		if (!value) {
			return null;
		}

		const isValidIdCode = idCodeRegex.test(value);

		return !isValidIdCode ? { validIdCode: true } : null;
	}
}

