import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BooksRoutingModule } from './books-routing.module';
import { BooksComponent } from './books.component';
import { AddBookComponent } from './add-book/add-book.component';
import {FormsModule} from "@angular/forms";
import {MyMaterialModule} from "../my-material/my-material.module";
import {MatLegacyInputModule as MatInputModule} from "@angular/material/legacy-input";
import { MatLegacyChipsModule as MatChipsModule } from "@angular/material/legacy-chips";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatLegacyFormFieldModule as MatFormFieldModule } from "@angular/material/legacy-form-field";
import { MatLegacyPaginatorModule as MatPaginatorModule } from "@angular/material/legacy-paginator";
import { MatLegacyTableModule as MatTableModule } from "@angular/material/legacy-table";
import {MatMomentDateModule} from "@angular/material-moment-adapter";
import { EditBookComponent } from './edit-book/edit-book.component';
import { AllBooksComponent } from './all-books/all-books.component';

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
  declarations: [BooksComponent, AddBookComponent, EditBookComponent, AllBooksComponent]
})
export class BooksModule { }
