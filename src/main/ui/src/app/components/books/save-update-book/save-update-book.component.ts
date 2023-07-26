import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
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
import {ActivatedRoute} from "@angular/router";
import {MatDialog} from "@angular/material/dialog";
import {Observable} from "rxjs";
import {AppUtils} from "../../utils/app.utils";

declare var $: any;

@Component({
  selector: 'app-add-book',
  templateUrl: './save-update-book.component.html',
  styleUrls: ['./save-update-book.component.scss'],
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
export class SaveUpdateBookComponent extends GenericComponent implements OnInit {
  @ViewChild('dialogTemplate', { static: true }) dialogTemplate: TemplateRef<any>;
  
  book: Book = {
    bookId: '',
    bookSource: '',
    bookName: '',
    bookPublishDateMomentum: null,
    bookPublishDate: null,
    bookAuthor: {bookAuthorName: ''},
    bookPublisher: {bookPublisherName: ''},
    bookQuantities: null,
    bookVolumes: null,
    tags: []
  };
  minDate = new Date(2018, 0, 1);
  visible = true;
  tags: Tags[] = [];
  showKeyboardStatus: boolean = true;
  editMode: boolean = false;

  constructor(private http: HttpConfig, snackBar: MatSnackBar, public datepipe: DatePipe,
              private route: ActivatedRoute, private dialog: MatDialog, private appUtils: AppUtils) {
    super(snackBar)
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let {bookId} = params;
      
      if(bookId !== undefined){
        this.editMode = true;
        this.http.get<Book>(`/books/${bookId}`).subscribe((book: Book) => {
          this.book = book;
          this.book.bookPublishDateMomentum = this.appUtils.convertStringDateToDate(book.bookPublishDate);
          this.tags = this.book.tags;
        });
      } else {
        this.book.bookPublishDateMomentum = new Date();
      }
    });
  }

  showKeyboard() {
    super.showKeyboard(this.showKeyboardStatus);
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

    let bookHttpRequestObject: Book;
    if (this.book.bookPublishDateMomentum != null && typeof this.book.bookPublishDateMomentum !== "object") {
      this.book.bookPublishDate = this.datepipe.transform(this.book.bookPublishDateMomentum.toDate(), 'dd-MM-yyyy');
    }

    //Creating a clone due to the date input, Without clone if this.book.bookPublishDateMomentum is delete then date picker
    //will be shown as required
    bookHttpRequestObject = Object.assign({}, this.book);
    delete bookHttpRequestObject.bookPublishDateMomentum;
    
    let httpRequest: Observable<any> = null;
    if(!this.editMode){
      delete bookHttpRequestObject.bookId;
      httpRequest = this.http.post("/books", bookHttpRequestObject); 
    } else {
      httpRequest = this.http.put("/books", bookHttpRequestObject);
    }
    
    httpRequest.subscribe(res => {
      this.statusStyle = {
        "font-size": "0.5rem",
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
        this.http.delete(`/books/${this.book.bookId}`).subscribe(response => {
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
