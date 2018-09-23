import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BooksComponent} from "./books.component";
import {AddBookComponent} from "./add-book/add-book.component";

const routes: Routes = [
  { path: '', component: BooksComponent },
  { path: 'books/add', component: AddBookComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BooksRoutingModule { }
