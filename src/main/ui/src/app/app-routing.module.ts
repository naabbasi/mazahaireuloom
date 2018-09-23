import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BooksModule} from "./components/books/books.module";

const routes: Routes = [
  { path: '', redirectTo: 'books', pathMatch: 'full' },
  { path: 'books', loadChildren: './components/books/books.module#BooksModule' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
