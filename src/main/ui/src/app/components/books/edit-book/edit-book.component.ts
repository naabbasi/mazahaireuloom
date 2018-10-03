import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {GenericComponent} from "../../GenericComponent";
import {HttpConfig} from "../../../config/httpconfig";
import {ActivatedRoute} from "@angular/router";
import {Book} from "../entity/Book";
import {
  DateAdapter,
  MAT_DATE_FORMATS,
  MAT_DATE_LOCALE,
  MatChipInputEvent,
  MatDialog,
  MatSnackBar
} from "@angular/material";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from "@angular/material-moment-adapter";
import {Tags} from "../entity/Tags";
import {GenericDialogComponent} from "../../my-material/generic-dialog/generic-dialog.component";

declare var $ : any;

@Component({
  selector: 'app-edit-book',
  templateUrl: './edit-book.component.html',
  styleUrls: ['./edit-book.component.css'],
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
export class EditBookComponent extends GenericComponent implements OnInit {
  book : Book;
  @ViewChild('dialogTemplate') dialogTemplate: TemplateRef<any>;

  constructor(private http : HttpConfig, private route : ActivatedRoute,
              snackBar: MatSnackBar, private dialog: MatDialog) {
    super(snackBar);
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      let {bookId} = params;
      this.http.get<Book>(`/books/${bookId}`).subscribe( (book : Book) => {
        this.book = book;
        console.log(this.book);
      });
    })
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    // Add our tags
    if ((value || '').trim()) {
      this.book.tags.push({name: value.trim()});
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(tag: Tags): void {
    const index = this.book.tags.indexOf(tag);

    if (index >= 0) {
      this.book.tags.splice(index, 1);
    }
  }

  onUpdate() {
    console.log(this.book);
    this.book.bookAuthor['name'] = $('#bookAuthor').val();
    this.book.bookPublisher['name'] = $('#bookPublisher').val();
    this.book.date = $('#date').val();

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

  onDelete() {
    //const dialogRef = this.dialog.open(GenericDialogComponent);
    const dialogRef = this.dialog.open(this.dialogTemplate);
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this.http.delete(`/books/${this.book.bookId}`).subscribe( response => {
          $('#status').html("کتاب حذف ہوچکی ہے");
        }, error => {
          $('#status').html(error);
        });
      }
    });
  }
}
