import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { RoomSearchParams } from 'src/shared/interfaces/RoomSearchParams';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

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

  getRooms(searchParams: RoomSearchParams) {
    const bedsParam = searchParams.beds ? `&beds=${searchParams.beds}` : ''
    const dateStartParam = searchParams.startDate ? `&startDate=${searchParams.startDate}` : ''
    const dateEndParam = searchParams.endDate ? `&endDate=${searchParams.endDate}` : ''
    const apiUrl = this.getApi(`api/rooms?${bedsParam}${dateStartParam}${dateEndParam}`)

    return this.http.post(apiUrl, {}).pipe(
      catchError(this.handleError())
    )
  }

  handleError() {
    return (error: any): Observable<any> => {
      console.error(error)

      return throwError(() => error.message)
    }
  }
}

export { RoomSearchParams };
