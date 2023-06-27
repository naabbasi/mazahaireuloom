import {Component, LOCALE_ID, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from "@angular/material/core";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from "@angular/material-moment-adapter";
import {Library} from "../../entity/Library";
import {HttpConfig} from "../../../config/httpconfig";
import {ActivatedRoute} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {GenericComponent} from "../../GenericComponent";

declare var $ : any;

@Component({
  selector: 'app-edit-library',
  templateUrl: './edit-library.component.html',
  styleUrls: ['./edit-library.component.scss'],
  providers: [
    // The locale would typically be provided on the root module of your application. We do it at
    // the component level here, due to limitations of our example generation script.
    {provide: MAT_DATE_LOCALE, useValue: 'ar-SA'},
    {provide: LOCALE_ID, useValue: 'ar-SA'},

    // `MomentDateAdapter` and `MAT_MOMENT_DATE_FORMATS` can be automatically provided by importing
    // `MatMomentDateModule` in your applications root module. We provide it at the component level
    // here, due to limitations of our example generation script.
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
  ]
})
export class EditLibraryComponent extends GenericComponent implements OnInit {
  library: Library = {} as Library;
  @ViewChild('dialogTemplate', {static: true}) dialogTemplate: TemplateRef<any>;

  constructor(private http: HttpConfig, private route: ActivatedRoute,
              snackBar: MatSnackBar, private dialog: MatDialog) {
    super(snackBar);
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let {libraryId} = params;
      this.http.get<Library>(`/libraries/${libraryId}`).subscribe((library: Library) => {
        this.library = library;
        console.log(this.library);
      });
    })
  }

  onUpdate() {
    console.log(this.library);
    this.http.post("/libraries", this.library).subscribe(res => {
      this.statusStyle = {
        "font-size": "12px",
        "font-weight": "normal",
        "color": "#0F0"
      };
      $('#status').html("کتاب کا اندراج ہوچکا ہے");
    }, error => {
      $('#status').html(error);
    });
    return false;
  }

  onDelete() {
    //const dialogRef = this.dialog.open(GenericDialogComponent);
    const dialogRef = this.dialog.open(this.dialogTemplate);
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.http.delete(`/libraries/${this.library.libraryId}`).subscribe(response => {
          this.statusStyle = {
            "font-size": "12px",
            "font-weight": "normal",
            "color": "#0F0"
          };

          $('#status').html("کتاب حذف ہوچکی ہے");
        }, error => {
          $('#status').html(error);
        });
      }
    });
  }
}
