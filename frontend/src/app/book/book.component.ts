import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.scss']
})
export class BookComponent {

  roomTypes: any = [
    { name: 'Ühte (1)', value: '1' },
    { name: 'Kahte (2)', value: '2' },
    { name: 'Kolme (3)', value: '3' },
  ]

  formGroup!: FormGroup

  constructor() {
    this.formGroup = new FormGroup({
      dateStart: new FormControl('', Validators.required),
      dateEnd: new FormControl('', Validators.required),
      roomType: new FormControl('', Validators.required),
    })
  }
}
