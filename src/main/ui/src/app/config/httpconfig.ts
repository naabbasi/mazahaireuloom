import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {catchError, retry} from "rxjs/operators";
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HttpConfig {
  readonly baseUrl : string = window.location.origin + "/api";
  private readonly options : {};

  constructor(private httpClient: HttpClient) {
    this.options = {
      withCredentials: true,
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }

  public get<T>(url: string) : Observable<any> {
    let completeUrl = `${this.baseUrl}${url}`;
    return this.httpClient.get(completeUrl, this.options)
      .pipe(
        retry(3),
        catchError(this.handleError)
      );
  }

  public post<T>(url: string, body) : Observable<any>{
    let completeUrl = `${this.baseUrl}${url}`;
    return this.httpClient.post(completeUrl,body, this.options)
      .pipe(
        retry(3),
        catchError(this.handleError)
      );
  }

  public delete<T>(url: string) : Observable<any> {
    let completeUrl = `${this.baseUrl}${url}`;
    return this.httpClient.delete(completeUrl, this.options)
      .pipe(
        retry(3),
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    if(error.error instanceof ErrorEvent){
      console.error(`Backend return code ${error.error.message}`);
    } else {
      console.error(`Backend return code ${error.status}, body was: ${error.error}`);
    }

    return throwError("تکنیکی وجوہات کی وجہ سے رابط ممکن نہں");
  }
}
