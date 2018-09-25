import {Component, OnInit} from '@angular/core';
import {HttpConfig} from "../../config/httpconfig";
import {Observable, of} from "rxjs";
import {Book} from "./entity/Book";
import {MatTableDataSource} from "@angular/material";



@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit{
  books : Observable<Book>;
  dataSource = new MatTableDataSource();
  displayedColumns = ['bookName', 'bookAuthor', 'bookPublisher'];

  constructor(private http : HttpConfig) {}

  ngOnInit() {
    this.http.get("/books").subscribe( (data) => {
      this.books = of(data);
      this.dataSource.data = data;
    });
  }
}
