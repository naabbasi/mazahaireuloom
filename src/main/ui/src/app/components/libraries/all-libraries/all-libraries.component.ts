import {Component, OnInit, ViewChild} from '@angular/core';
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import {Observable, of} from "rxjs";
import {Library} from "../../entity/Library";
import {HttpConfig} from "../../../config/httpconfig";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-all-libraries',
  templateUrl: './all-libraries.component.html',
  styleUrls: ['./all-libraries.component.scss']
})
export class AllLibrariesComponent implements OnInit {
  private libraries : Observable<Library>;
  private dataSource = new MatTableDataSource();
  @ViewChild(MatPaginator) paginator: MatPaginator;
  private displayedColumns = ['libraryName', 'libraryNumber', 'shelves'];
  resultsLength = 0;

  constructor(private http : HttpConfig, private route : ActivatedRoute) { }

  ngOnInit() {
    this.dataSource.paginator = this.paginator;
    this.http.get(`/libraries`).subscribe( (data) => {
      this.libraries = of(data);
      this.dataSource.data = data;
      this.resultsLength = data.length;
      console.log(`resultsLength = ${this.resultsLength}`)
    });
  }

}
