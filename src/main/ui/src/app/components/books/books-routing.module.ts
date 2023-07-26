import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SaveUpdateBookComponent} from "./save-update-book/save-update-book.component";
import {AllBooksComponent} from "./all-books/all-books.component";
import {BooksComponent} from "./books.component";

const routes: Routes = [
  { path: '', component: AllBooksComponent },
  { path: 'books/add', component: SaveUpdateBookComponent},
  { path: 'books/edit/:bookId', component: SaveUpdateBookComponent},
  { path: 'books/search', component: BooksComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class BooksRoutingModule { }
