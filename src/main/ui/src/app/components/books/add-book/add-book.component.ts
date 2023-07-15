import {Component, OnInit} from '@angular/core';
import {HttpConfig} from "../../../config/httpconfig";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from "@angular/material/chips";
import {MatSnackBar} from "@angular/material/snack-bar";
import {Tags} from '../../entity/Tags';
import {Book} from '../../entity/Book';
import {GenericComponent} from "../../GenericComponent";
import {DatePipe} from "@angular/common";

declare var $: any;

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.scss'],
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
export class AddBookComponent extends GenericComponent implements OnInit {
  book: Book = {
    bookId: '',
    bookSource: '',
    bookName: '',
    bookPublishDateMomentum: null,
    bookPublishDate: null,
    bookAuthor: {bookAuthorName: ''},
    bookPublisher: {bookPublisherName: ''},
    bookQuantities: 0,
    bookVolumes: 0,
    tags: []
  };
  minDate = new Date(2018, 0, 1);
  visible = true;
  tags: Tags[] = [];

  constructor(private http: HttpConfig, snackBar: MatSnackBar, public datepipe: DatePipe) {
    super(snackBar)
  }

  ngOnInit() {
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
    if (this.tags.length !== 0) {
      this.book.tags = this.tags;
    }

    if (this.book.bookPublishDateMomentum != null) {
      this.book.bookPublishDate = this.datepipe.transform(this.book.bookPublishDateMomentum.toDate(), 'dd-MM-yyyy');
      delete this.book.bookPublishDateMomentum;
      delete this.book.bookId;
    }

    console.log(this.book);

    this.http.post("/books", this.book).subscribe(res => {
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
}
