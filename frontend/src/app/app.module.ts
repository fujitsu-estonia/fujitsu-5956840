import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AngularMaterialModule } from 'src/shared/modules/angular-material.module';
import { NotFoundComponent } from './not-found/not-found.component';
import { HomepageComponent } from './homepage/homepage.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { BookComponent } from './book/book.component';
import { BookingsComponent } from './bookings/bookings.component';
import { HttpClientModule } from '@angular/common/http';
import { VeeraErrorComponent } from './shared-components/veera-error/veera-error.component';
import { RoomComponent } from './book/room/room.component';
import { RoomSearchComponent } from './book/room-search/room-search.component';
import { BookARoomComponent } from './book/book-a-room/book-a-room.component';
import { BookingDoneComponent } from './book/booking-done/booking-done.component';
import { MAT_DATE_LOCALE } from '@angular/material/core';
import { BookingListComponent } from './bookings/booking-list/booking-list.component';
import { BookingSearchComponent } from './bookings/booking-search/booking-search.component';

@NgModule({
  declarations: [
    AppComponent,
    NotFoundComponent,
    HomepageComponent,
    HeaderComponent,
    FooterComponent,
    BookComponent,
    BookingsComponent,
    VeeraErrorComponent,
    RoomComponent,
    RoomSearchComponent,
    BookARoomComponent,
    BookingDoneComponent,
    BookingListComponent,
    BookingSearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    AngularMaterialModule,
    HttpClientModule,
  ],
  exports: [
    AngularMaterialModule,
    AppComponent,
    NotFoundComponent,
    HomepageComponent,
    HeaderComponent,
    FooterComponent,
    BookComponent,
    BookingsComponent,
    VeeraErrorComponent,
    RoomComponent,
    RoomSearchComponent,
    BookARoomComponent,
    BookingDoneComponent,
    BookingListComponent,
    BookingSearchComponent
  ],
  providers: [
    { provide: MAT_DATE_LOCALE, useValue: 'et-EE' }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
