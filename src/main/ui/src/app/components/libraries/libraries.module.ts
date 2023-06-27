import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LibrariesRoutingModule } from './libraries-routing.module';
import { AddLibraryComponent } from './add-library/add-library.component';
import { LibrariesComponent } from './libraries.component';
import {FormsModule} from "@angular/forms";
import {MyMaterialModule} from "../my-material/my-material.module";
import {MatInputModule} from "@angular/material/input";
import { MatChipsModule } from "@angular/material/chips";
import { MatDatepickerModule } from "@angular/material/datepicker";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatTableModule } from "@angular/material/table";
import {MatMomentDateModule} from "@angular/material-moment-adapter";


@NgModule({
  declarations: [
    LibrariesComponent,
    AddLibraryComponent
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
    LibrariesRoutingModule
  ]
})
export class LibrariesModule { }
