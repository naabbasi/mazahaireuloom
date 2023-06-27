import {Component, OnInit, ViewChild} from '@angular/core';
import {HttpConfig} from "../../config/httpconfig";
import {Subject, Observable, of, merge} from 'rxjs';
import {Library} from "../entity/Library";
import { MatPaginator } from "@angular/material/paginator";
import { MatSnackBar } from "@angular/material/snack-bar";
import { MatTableDataSource } from "@angular/material/table";
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
  selector: 'app-libraries',
  templateUrl: './libraries.component.html',
  styleUrls: ['./libraries.component.scss']
})
export class LibrariesComponent extends GenericComponent implements OnInit{
  libraries : Observable<Library>;
  keyUp = new Subject<string>();
  dataSource = new MatTableDataSource();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  displayedColumns = ['libraryName', 'libraryNumber'];

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
          this.http.get(`/libraries/search/${value}`).subscribe( (data) => {
            this.libraries = of(data);
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
          return this.http.get(`/libraries?page=${this.paginator.pageIndex + 1}`)
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.resultsLength = data.length;
          return data;
        })
      ).subscribe(data => this.libraries = data);
  }

  onSearch(searchString) {
    this.keyUp.next(searchString);
  }

  loadLibraries() {
    this.http.get("/libraries").subscribe( (data) => {
      this.libraries = of(data);
      this.dataSource.data = data;
      this.resultsLength = data.length;
    });
  }
}
