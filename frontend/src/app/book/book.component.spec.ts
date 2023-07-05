import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BookComponent } from './book.component';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';
import { RoomSearchComponent } from './room-search/room-search.component';
import { BookARoomComponent } from './book-a-room/book-a-room.component';
import { BookingDoneComponent } from './booking-done/booking-done.component';
import { VeeraErrorComponent } from '../shared-components/veera-error/veera-error.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('BookComponent', () => {
  let component: BookComponent;
  let fixture: ComponentFixture<BookComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AngularMaterialModule, BrowserAnimationsModule],
      declarations: [BookComponent, RoomSearchComponent, BookARoomComponent, BookingDoneComponent, VeeraErrorComponent]
    });
    fixture = TestBed.createComponent(BookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
