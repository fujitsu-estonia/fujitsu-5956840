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
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
