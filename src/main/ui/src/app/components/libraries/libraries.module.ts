import { NgModule } from '@angular/core';
import {AsyncPipe, CommonModule, NgFor} from '@angular/common';

import { LibrariesRoutingModule } from './libraries-routing.module';
import { AddLibraryComponent } from './add-library/add-library.component';
import { LibrariesComponent } from './libraries.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MyMaterialModule} from "../my-material/my-material.module";
import {MatInputModule} from "@angular/material/input";
import { MatChipsModule } from "@angular/material/chips";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatTableModule } from "@angular/material/table";
import {MatMomentDateModule} from "@angular/material-moment-adapter";
import {AllLibrariesComponent} from "./all-libraries/all-libraries.component";
import { EditLibraryComponent } from './edit-library/edit-library.component';
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {AddShelfComponent} from "./add-shelf/add-shelf.component";


@NgModule({
  declarations: [
    AllLibrariesComponent,
    LibrariesComponent,
    AddLibraryComponent,
    EditLibraryComponent,
    AddShelfComponent
  ],
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
    MatAutocompleteModule,
    ReactiveFormsModule,
    NgFor,
    AsyncPipe,
    LibrariesRoutingModule
  ]
})
export class LibrariesModule { }
