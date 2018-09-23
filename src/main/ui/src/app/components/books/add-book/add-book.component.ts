import { Component, OnInit } from '@angular/core';
import {HttpConfig} from "../../../config/httpconfig";
declare var $ : any;

@Component({
  selector: 'app-add-book',
  templateUrl: './add-book.component.html',
  styleUrls: ['./add-book.component.css']
})
export class AddBookComponent implements OnInit {
  constructor(private http: HttpConfig) { }

  ngOnInit() {
  }

  bindKeyboard(fieldName) {
    $('#' + fieldName.id).setUrduInput();
  }

  onSave() {
    let addBook = {
      "bookName": $('#bookName').val(),
      "bookAuthor": $('#bookAuthor').val(),
      "bookPublisher": $('#bookPublisher').val()
    };

    console.log($('#bookName').val())
    console.log($('#bookAuthor').val())
    console.log($('#bookPublisher').val())
    this.http.post("/books", addBook).subscribe(res => {
      console.log(res);
    }, error => {
      $('#status').html(error);
    });
    return false;
  }
}
