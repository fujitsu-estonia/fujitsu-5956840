import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookingDoneComponent } from './booking-done.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';

describe('BookingDoneComponent', () => {
  let component: BookingDoneComponent;
  let fixture: ComponentFixture<BookingDoneComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule],
      declarations: [BookingDoneComponent]
    });
    fixture = TestBed.createComponent(BookingDoneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
