import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BooksRoutingModule } from './books-routing.module';
import { BooksComponent } from './books.component';
import { AddBookComponent } from './add-book/add-book.component';
import {FormsModule} from "@angular/forms";
import {MyMaterialModule} from "../my-material/my-material.module";
import {MatInputModule} from "@angular/material/input";
import {
  MatChipsModule,
  MatDatepickerModule,
  MatFormFieldModule,
  MatPaginatorModule,
  MatTableModule
} from "@angular/material";
import {MatMomentDateModule} from "@angular/material-moment-adapter";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MyMaterialModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatMomentDateModule,
    MatChipsModule,
    MatTableModule,
    MatPaginatorModule,
    BooksRoutingModule
  ],
  declarations: [BooksComponent, AddBookComponent]
})
export class BooksModule { }
