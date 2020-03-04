import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpConfig} from "../../config/httpconfig";
import {Subject, Observable, of, merge} from 'rxjs';
import {Book} from "./entity/Book";
import {MatPaginator, MatSnackBar, MatTableDataSource} from "@angular/material";
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
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  displayedColumns = ['bookName', 'bookAuthor', 'bookPublisher', 'tags'];

  resultsLength = 0;
  isLoadingResults: boolean = false;

  constructor(private http : HttpConfig, snackBar : MatSnackBar) {
    super(snackBar);
  }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    //this.paginator._intl.itemsPerPageLabel = "";
    this.keyUp.pipe(
      map(value => value),
      debounceTime(200),
      distinctUntilChanged(),
      flatMap(search => of(search).pipe(delay(500)))
    ).subscribe(value => {
        if(value === ""){
          this.isLoadingResults = false;
        } else {
          this.isLoadingResults = true;
          this.http.get(`/books/search/${value}`).subscribe( (data) => {
            this.books = of(data);
            this.dataSource.data = data;
            this.resultsLength = data.length;
            this.isLoadingResults = false;
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
