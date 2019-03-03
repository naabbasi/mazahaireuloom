import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {AddBookComponent} from "./add-book/add-book.component";
import {EditBookComponent} from "./edit-book/edit-book.component";
import {AllBooksComponent} from "./all-books/all-books.component";
import {BooksComponent} from "./books.component";

const routes: Routes = [
  { path: '', component: AllBooksComponent },
  { path: 'books/add', component: AddBookComponent},
  { path: 'books/edit/:bookId', component: EditBookComponent},
  { path: 'books/search', component: BooksComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BooksRoutingModule { }
