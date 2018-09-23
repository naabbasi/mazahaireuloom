import {Component, OnInit} from '@angular/core';
import {HttpConfig} from "../../config/httpconfig";


@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit{

  constructor(private http : HttpConfig) {}

  ngOnInit() {
    this.http.get("/books").subscribe(res => {
      console.log(res);
    });
  }
}
