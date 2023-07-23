import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatDialogModule } from "@angular/material/dialog";
import { MatDividerModule } from "@angular/material/divider";
import { MatProgressBarModule } from "@angular/material/progress-bar";
import { MatSnackBarModule } from "@angular/material/snack-bar";
import {MatMenuModule} from "@angular/material/menu";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import { GenericDialogComponent } from './generic-dialog/generic-dialog.component';
import {ArabicNumberKeyboardDirective} from "../directives/arabic-number-keyboard.directive";
import {ArabicLetterKeyboardDirective} from "../directives/arabic-letter-keyboard.directive";

@NgModule({
    imports: [
        MatButtonModule,
        MatMenuModule,
        MatToolbarModule,
        MatIconModule,
        MatCardModule,
        MatDividerModule,
        MatSnackBarModule,
        MatProgressBarModule,
        MatDialogModule
    ],
    exports: [
        MatButtonModule,
        MatMenuModule,
        MatToolbarModule,
        MatIconModule,
        MatCardModule,
        MatDividerModule,
        MatSnackBarModule,
        MatProgressBarModule,
        MatDialogModule,
        ArabicNumberKeyboardDirective,
        ArabicLetterKeyboardDirective
    ],
  declarations: [GenericDialogComponent, ArabicNumberKeyboardDirective, ArabicLetterKeyboardDirective]
})
export class MyMaterialModule { }
