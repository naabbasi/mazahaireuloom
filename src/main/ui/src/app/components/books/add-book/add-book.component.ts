import { Component, OnInit } from '@angular/core';
import {HttpConfig} from "../../../config/httpconfig";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from "@angular/material";
import {Tags} from '../entity/Tags';
declare var $ : any;

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css'],
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
export class AddBookComponent implements OnInit {
  value: string;
  minDate = new Date(2018, 0, 1);
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  tags: Tags[] = [];

  statusStyle = {
    "font-size": "12px",
    "font-weight": "bold",
    "color": "#F00"
  };

  constructor(private http: HttpConfig) { }

  ngOnInit() {
  }

  bindKeyboard(fieldName) {
    $('#' + fieldName.id).setUrduInput({urduNumerals: true});
  }

  onEnter(value: string) {
    this.value = value;
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our tags
    if ((value || '').trim()) {
      this.tags.push({name: value.trim()});
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(tag: Tags): void {
    const index = this.tags.indexOf(tag);

    if (index >= 0) {
      this.tags.splice(index, 1);
    }
  }

  onSave() {
    let addBook = {
      "bookName": $('#bookName').val(),
      "date": $('#date').val(),
      "bookAuthor": {
        "name" : $('#bookAuthor').val()
      },
      "bookPublisher": {
        "name": $('#bookPublisher').val()
      },
      "tags": this.tags
    };

    this.http.post("/books", addBook).subscribe(res => {
      this.statusStyle = {
        "font-size": "12px",
        "font-weight": "normal",
        "color": "#0F0"
      };
      $('#status').html("Book saved successfully ...");
    }, error => {
      $('#status').html(error);
    });
    return false;
  }
}
