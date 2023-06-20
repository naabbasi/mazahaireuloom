import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BooksModule} from "./components/books/books.module";

const routes: Routes = [
  { path: '', redirectTo: 'books', pathMatch: 'full' },
  { path: 'books', loadChildren: () => import('./components/books/books.module').then(m => m.BooksModule) }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  //imports: [RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
