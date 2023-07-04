import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-booking-done',
  templateUrl: './booking-done.component.html',
  styleUrls: ['./booking-done.component.scss']
})
export class BookingDoneComponent {

  @Input() bookingId!: string

  host: string = window.location.host
}
