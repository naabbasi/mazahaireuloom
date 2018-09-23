import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpResponse} from "@angular/common/http";
import {catchError, retry} from "rxjs/operators";
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HttpConfig {
  private readonly baseUrl : string;
  private readonly options : {};

  constructor(private httpClient: HttpClient) {
    this.baseUrl = "http://localhost:90/api";
    this.options = {
      withCredentials: true,
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    };
  }

  public get(url: string) : Observable<any> {
    return this.httpClient.get(this.baseUrl + url, this.options)
      .pipe(
        catchError(this.handleError)
      );
  }

  public post(url: string, body) {
    return this.httpClient.post(this.baseUrl + url,body, this.options)
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
