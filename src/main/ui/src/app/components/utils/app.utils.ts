import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppUtils {

  constructor() { }
  
  public convertStringDateToDate(stringDate: string) {
    const [day, month, year] = stringDate.split("-");
    return new Date(+year, +month - 1, +day)
  }
}
