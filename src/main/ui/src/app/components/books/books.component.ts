import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {HttpConfig} from "../../config/httpconfig";
import {Subject, Observable, of, merge} from 'rxjs';
import {Book} from "../entity/Book";
import {MatPaginator} from "@angular/material/paginator";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatTableDataSource} from "@angular/material/table";
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
import {ActivatedRoute} from "@angular/router";

declare var $: any;

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.scss']
})
export class BooksComponent extends GenericComponent implements OnInit {
  books: Observable<Book>;
  keyUp = new Subject<string>();
  dataSource = new MatTableDataSource();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  displayedColumns = ['bookName', 'bookAuthor', 'bookPublisher', 'tags'];

  resultsLength = 0;
  isLoadingResults: boolean = false;
  @ViewChild("findBook", {static: true}) txtFindBook: ElementRef;

  constructor(private http: HttpConfig, snackBar: MatSnackBar, private route: ActivatedRoute) {
    super(snackBar);
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      let value = params['q'];
      if(value !== undefined) {
        this.txtFindBook.nativeElement.value = value;
        this.searchBooks(params['q'], params['by']);
      } else {
        this.txtFindBook.nativeElement.value = "";
      }
    });

    this.dataSource.paginator = this.paginator;
    //this.paginator._intl.itemsPerPageLabel = "";
    this.keyUp.pipe(
      map(value => value),
      debounceTime(200),
      distinctUntilChanged(),
      flatMap(search => of(search).pipe(delay(500)))
    ).subscribe(value => {
      if (value === "") {
        this.isLoadingResults = false;
      } else {
        this.isLoadingResults = true;
        this.searchBooks(value);
      }
    });
  }

  private searchBooks(value: string, params?: string) {
    let queryParams = params === undefined ? "" : "?by=" + params;
    this.http.get(`/books/search/${value}${queryParams}`).subscribe((data) => {
      this.books = of(data);
      this.dataSource.data = data;
      this.resultsLength = data.length;
      this.isLoadingResults = false;
    });
  }
  test() {
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
    this.http.get("/books").subscribe((data) => {
      this.books = of(data);
      this.dataSource.data = data;
      this.resultsLength = data.length;
    });
  }
}
