import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { RoomSearchParams } from 'src/shared/interfaces/RoomSearchParams';

export interface DateRange {
  dateStart: Date | string;
  dateEnd: Date | string;
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

  minDate: Date = new Date()

  beds: any = [
    { name: 'Ühe (1)', value: 1 },
    { name: 'Kahe (2)', value: 2 },
    { name: 'Kolme (3)', value: 3 },
  ]

  search() {
    this.formGroup.markAllAsTouched()

    if (this.formGroup.errors && this.formGroup?.errors['equal']) {
      this.formGroup.get('dateEnd')?.setErrors({ equal: true })
    }

    if (this.formGroup.valid) {
      const searchFilter: RoomSearchParams = {
        startDate: this.formGroup.get('dateStart')?.value,
        endDate: this.formGroup.get('dateEnd')?.value,
        beds: this.formGroup.get('beds')?.value
      }

      this.dateRangeChanged.emit(
        {
          dateStart: searchFilter.startDate,
          dateEnd: searchFilter.endDate
        }
      )

      this.searchForRoomsExecuted.emit(searchFilter)
    }
  }
}
