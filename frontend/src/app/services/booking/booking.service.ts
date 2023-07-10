import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Booking } from 'src/shared/interfaces/Booking';

interface PostBookingSearchParams {
  bookingStatus?: "ACCEPTED",
  fromDate?: Date,
  toDate?: Date
}

@Injectable({
  providedIn: 'root'
})
export class BookingService {


  constructor(private http: HttpClient) { }

  getApi(apiUrl: string) {
    if (window.location.host === "localhost:4200") {
      /* API url in dev*/
      apiUrl = "http://localhost:8090/" + apiUrl;
    } else {
      apiUrl = "http://" + window.location.hostname + ":8080/" + apiUrl
    }

    return apiUrl;
  }


  getBookingById(bookingId: string): Observable<any> {
    const apiUrl = this.getApi(`api/booking/${bookingId}`)

    return this.http.get(apiUrl).pipe(
      catchError(this.handleError())
    )
  }

  getBookings(params: PostBookingSearchParams): Observable<any> {
    const apiUrl = this.getApi(`api/booking/search`)
    return this.http.post(apiUrl, params).pipe(
      catchError(this.handleError())
    )
  }

  postBooking(booking: Booking): Observable<any> {
    const apiUrl = this.getApi(`api/booking`)

    return this.http.post(apiUrl, booking).pipe(
      catchError(this.handleError())
    )
  }

  cancelBooking(bookingId: string): Observable<any> {
    const apiUrl = this.getApi(`api/booking/cancel/${bookingId}`)
    const headerDict = {
      'X-Request-Originator': 'ADMIN',
    }

    return this.http.post(apiUrl, { headers: headerDict }).pipe(
      catchError(this.handleError())
    )

  }

  handleError() {
    return (error: any): Observable<any> => {
      console.log(error)

      return throwError(() => error.message)
    }
  }
}
