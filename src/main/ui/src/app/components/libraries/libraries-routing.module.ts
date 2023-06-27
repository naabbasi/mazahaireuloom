import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AddLibraryComponent} from "./add-library/add-library.component";
import {AllLibrariesComponent} from "./all-libraries/all-libraries.component";
import {LibrariesComponent} from "./libraries.component";
import {EditLibraryComponent} from "./edit-library/edit-library.component";

const routes: Routes = [
  { path: '', component: AllLibrariesComponent },
  { path: 'libraries/add', component: AddLibraryComponent},
  { path: 'libraries/edit/:libraryId', component: EditLibraryComponent},
  { path: 'libraries/search', component: LibrariesComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LibrariesRoutingModule { }
