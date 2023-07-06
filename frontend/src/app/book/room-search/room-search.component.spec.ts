import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RoomSearchComponent } from './room-search.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { VeeraErrorComponent } from 'src/app/shared-components/veera-error/veera-error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('RoomSearchComponent', () => {
  let component: RoomSearchComponent;
  let fixture: ComponentFixture<RoomSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule, BrowserAnimationsModule],
      declarations: [RoomSearchComponent, VeeraErrorComponent]
    });
    fixture = TestBed.createComponent(RoomSearchComponent);
    component = fixture.componentInstance;
    component.formGroup = new FormGroup({
      dateStart: new FormControl('', Validators.required),
      dateEnd: new FormControl('', Validators.required),
      roomType: new FormControl('', Validators.required),
    })
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not emit any events when search is called with invalid form', () => {
    spyOn(component.dateRangeChanged, 'emit');
    spyOn(component.searchForRoomsExecuted, 'emit');
    component.search();
    expect(component.dateRangeChanged.emit).not.toHaveBeenCalled();
    expect(component.searchForRoomsExecuted.emit).not.toHaveBeenCalled();
  });
});
