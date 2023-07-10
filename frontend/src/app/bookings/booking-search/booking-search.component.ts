import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-booking-search',
  templateUrl: './booking-search.component.html',
  styleUrls: ['./booking-search.component.scss']
})
export class BookingSearchComponent {
  @Input() bookingId !: string
  @Output() searchExecuted = new EventEmitter<string>()

  formGroup: FormGroup = new FormGroup({
    search: new FormControl("", Validators.required)
  })

  ngOnInit() {
    if (this.bookingId) {
      this.formGroup.get('search')?.setValue(this.bookingId)
      setTimeout(() => this.search(), 0)
    }
  }

  search() {
    this.formGroup.markAllAsTouched()

    if (this.formGroup.invalid) return

    this.searchExecuted.emit(this.formGroup.get('search')?.value)
  }

}
