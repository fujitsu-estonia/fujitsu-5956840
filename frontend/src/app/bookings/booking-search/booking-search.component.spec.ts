import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingSearchComponent } from './booking-search.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';
import { VeeraErrorComponent } from 'src/app/shared-components/veera-error/veera-error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('BookingSearchComponent', () => {
  let component: BookingSearchComponent;
  let fixture: ComponentFixture<BookingSearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule, BrowserAnimationsModule],
      declarations: [BookingSearchComponent, VeeraErrorComponent]
    });
    fixture = TestBed.createComponent(BookingSearchComponent);
    component = fixture.componentInstance;
    component.bookingId = '123456789';
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should emit searchExecuted event when search is called', () => {
    spyOn(component.searchExecuted, 'emit');
    component.formGroup.setValue({ search: '123456789' });
    component.search();
    expect(component.searchExecuted.emit).toHaveBeenCalledWith('123456789');
  });
});
