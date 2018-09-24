import {Component, OnInit} from '@angular/core';
import {HttpConfig} from "../../config/httpconfig";
import {Observable, of} from "rxjs";
import {Book} from "./entity/Book";



@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit{
  books : Observable<Book>;
  constructor(private http : HttpConfig) {}

  ngOnInit() {
    this.http.get("/books").subscribe( (data : Book) => {
      this.books = of(data);
    });
  }
}
