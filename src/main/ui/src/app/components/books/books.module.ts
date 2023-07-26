import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BooksRoutingModule } from './books-routing.module';
import { BooksComponent } from './books.component';
import {FormsModule} from "@angular/forms";
import {MyMaterialModule} from "../utils/my-material.module";
import {MatInputModule} from "@angular/material/input";
import { MatChipsModule } from "@angular/material/chips";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatTableModule } from "@angular/material/table";
import {MatMomentDateModule} from "@angular/material-moment-adapter";
import { EditBookComponent } from './edit-book/edit-book.component';
import { AllBooksComponent } from './all-books/all-books.component';
import {SaveUpdateBookComponent} from "./save-update-book/save-update-book.component";

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
  declarations: [BooksComponent, SaveUpdateBookComponent, EditBookComponent, AllBooksComponent]
})
export class BooksModule { }
