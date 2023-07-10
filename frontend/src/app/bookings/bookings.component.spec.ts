import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingsComponent } from './bookings.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';
import { RouterTestingModule } from '@angular/router/testing';
import { BookingListComponent } from './booking-list/booking-list.component';
import { BookingSearchComponent } from './booking-search/booking-search.component';
import { VeeraErrorComponent } from '../shared-components/veera-error/veera-error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';

describe('BookingsComponent', () => {
  let component: BookingsComponent;
  let fixture: ComponentFixture<BookingsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule, RouterTestingModule, BrowserAnimationsModule, HttpClientTestingModule],
      declarations: [BookingsComponent, BookingListComponent, BookingSearchComponent, VeeraErrorComponent]
    });
    fixture = TestBed.createComponent(BookingsComponent);
    component = fixture.componentInstance;
    component.adminMode = true;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
