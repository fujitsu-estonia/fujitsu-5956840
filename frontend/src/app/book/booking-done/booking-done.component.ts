import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-booking-done',
  templateUrl: './booking-done.component.html',
  styleUrls: ['./booking-done.component.scss']
})
export class BookingDoneComponent {

  @Input() bookingId!: string
  @Output() goToStartPressed = new EventEmitter<void>()

  host: string = window.location.host

  goToStart() {
    window.location.reload()
  }
}
