import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpConfig} from "../../config/httpconfig";
import {Subject, Observable, of, merge} from 'rxjs';
import {Book} from "./entity/Book";
import {MatPaginator, MatTableDataSource} from "@angular/material";
import {GenericComponent} from "../GenericComponent";
import {
  catchError,
  debounceTime,
  delay,
  distinctUntilChanged,
  flatMap,
  map,
  startWith,
  switchMap
} from "rxjs/operators";

declare var $ : any;

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent extends GenericComponent implements OnInit{
  books : Observable<Book>;
  keyUp = new Subject<string>();
  dataSource = new MatTableDataSource();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  displayedColumns = ['bookName', 'bookAuthor', 'bookPublisher', 'tags'];

  resultsLength = 0;

  constructor(private http : HttpConfig) {
    super();
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.loadBooks();
    //this.paginator._intl.itemsPerPageLabel = "";
    this.keyUp.pipe(
      map(value => value),
      debounceTime(500),
      distinctUntilChanged(),
      flatMap(search => of(search).pipe(delay(500)))
    ).subscribe(value => {
      if(value === ""){
        this.loadBooks();
      }else {
        this.http.get(`/books/search/${value}`).subscribe( (data) => {
          this.books = of(data);
          this.dataSource.data = data;
          this.resultsLength = data.length;
        });
      }
    });
  }

  test(){
    merge(this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          return this.http.get(`/books?page=${this.paginator.pageIndex + 1}`)
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.resultsLength = data.length;
          return data;
        })
      ).subscribe(data => this.books = data);
  }

  onSearch(searchString) {
    this.keyUp.next(searchString);
  }

  loadBooks() {
    this.http.get("/books").subscribe( (data) => {
      this.books = of(data);
      this.dataSource.data = data;
      this.resultsLength = data.length;
    });
  }
}
