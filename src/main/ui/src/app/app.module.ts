import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BooksModule } from './components/books/books.module';
import { LibrariesModule } from './components/libraries/libraries.module';
import { provideHttpClient, withInterceptorsFromDi } from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MyMaterialModule} from "./components/utils/my-material.module";
import {DatePipe} from "@angular/common";

@NgModule({ declarations: [
        AppComponent
    ],
    bootstrap: [AppComponent], imports: [BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        BooksModule,
        LibrariesModule,
        MyMaterialModule], providers: [DatePipe, provideHttpClient(withInterceptorsFromDi())] })
export class AppModule { }
