import {Component, OnInit, ViewChild} from '@angular/core';
import { MatLegacyPaginator as MatPaginator } from "@angular/material/legacy-paginator";
import { MatLegacyTableDataSource as MatTableDataSource } from "@angular/material/legacy-table";
import {Observable, of} from "rxjs";
import {Book} from "../entity/Book";
import {HttpConfig} from "../../../config/httpconfig";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-all-books',
  templateUrl: './all-books.component.html',
  styleUrls: ['./all-books.component.css']
})
export class AllBooksComponent implements OnInit {
  private books : Observable<Book>;
  private dataSource = new MatTableDataSource();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  private displayedColumns = ['bookName', 'bookAuthor', 'bookPublisher', 'tags'];
  resultsLength = 0;

  constructor(private http : HttpConfig, private route : ActivatedRoute) { }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.http.get(`/books`).subscribe( (data) => {
      this.books = of(data);
      this.dataSource.data = data;
      this.resultsLength = data.length;
    });
  }

}
