import { Component, OnInit } from '@angular/core';
import {HttpConfig} from "../../../config/httpconfig";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import { MatChipInputEvent } from "@angular/material/chips";
import { MatSnackBar } from "@angular/material/snack-bar";
import {Tags} from '../../entity/Tags';
import {GenericComponent} from "../../GenericComponent";
import {Library} from "../../entity/Library";
declare var $ : any;

@Component({
  selector: 'app-add-library',
  templateUrl: './add-library.component.html',
  styleUrls: ['./add-library.component.scss'],
  providers: [
    // The locale would typically be provided on the root module of your application. We do it at
    // the component level here, due to limitations of our example generation script.
    {provide: MAT_DATE_LOCALE, useValue: 'ar-SA'},

    // `MomentDateAdapter` and `MAT_MOMENT_DATE_FORMATS` can be automatically provided by importing
    // `MatMomentDateModule` in your applications root module. We provide it at the component level
    // here, due to limitations of our example generation script.
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
  ]
})
export class AddLibraryComponent extends GenericComponent implements OnInit {
  minDate = new Date(2018, 0, 1);
  visible = true;
  library: Library = {} as Library;
  tags: Tags[] = [];

  constructor(private http: HttpConfig, snackBar: MatSnackBar) { super(snackBar) }

  ngOnInit() {
  }

  onSave() {
    this.http.post("/libraries", this.library).subscribe(res => {
      this.statusStyle = {
        "font-size": "12px",
        "font-weight": "normal",
        "color": "#0F0"
      };
      $('#status').html("مكتبة کا اندراج ہوچکا ہے");
    }, error => {
      $('#status').html(error);
    });
    return false;
  }
}
