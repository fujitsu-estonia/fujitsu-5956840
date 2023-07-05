import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { RoomSearchParams } from 'src/shared/interfaces/RoomSearchParams';

export interface DateRange {
  dateStart: Date;
  dateEnd: Date;
}

@Component({
  selector: 'app-room-search',
  templateUrl: './room-search.component.html',
  styleUrls: ['./room-search.component.scss']
})
export class RoomSearchComponent {
  @Input() formGroup!: FormGroup
  @Output() dateRangeChanged = new EventEmitter<DateRange>()
  @Output() searchForRoomsExecuted = new EventEmitter<RoomSearchParams>()

  roomTypes: any = [
    { name: 'Ühe (1)', value: 1 },
    { name: 'Kahe (2)', value: 2 },
    { name: 'Kolme (3)', value: 3 },
  ]

  search() {
    this.formGroup.markAllAsTouched()

    if (this.formGroup.valid) {
      const searchFilter: RoomSearchParams = {
        dateStart: this.formGroup.get('dateStart')?.value,
        dateEnd: this.formGroup.get('dateEnd')?.value,
        roomType: this.formGroup.get('roomType')?.value
      }

      this.dateRangeChanged.emit(
        {
          dateStart: searchFilter.dateStart,
          dateEnd: searchFilter.dateEnd
        }
      )

      this.searchForRoomsExecuted.emit(searchFilter)

      //search
    }
  }
}
