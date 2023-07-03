import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NotFoundComponent } from './not-found/not-found.component';
import { HomepageComponent } from './homepage/homepage.component';
import { BookComponent } from './book/book.component';
import { BookingsComponent } from './bookings/bookings.component';

const routes: Routes = [
  { path: '', component: HomepageComponent },
  { path: 'book', component: BookComponent },
  { path: 'bookings', component: BookingsComponent },
  { path: '**', component: NotFoundComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
