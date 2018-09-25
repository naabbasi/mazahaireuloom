import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BooksRoutingModule } from './books-routing.module';
import { BooksComponent } from './books.component';
import { AddBookComponent } from './add-book/add-book.component';
import {FormsModule} from "@angular/forms";
import {MyMaterialModule} from "../my-material/my-material.module";
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule, MatTableModule} from "@angular/material";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    MyMaterialModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    BooksRoutingModule
  ],
  declarations: [BooksComponent, AddBookComponent]
})
export class BooksModule { }
