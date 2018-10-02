import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {MatSnackBar} from "@angular/material";
import {TemplateRef, ViewChild} from "@angular/core";

declare var $ : any;
export class GenericComponent {
  @ViewChild('template') template: TemplateRef<any>;
  constructor(public snackBar?: MatSnackBar){}

  value: string;
  selectable = true;
  removable = true;
  addOnBlur = true;
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];

  bindKeyboard(fieldName) {
    $('#' + fieldName.id).setUrduInput({urduNumerals: true});
    if(this.snackBar != undefined){
      this.snackBar.openFromTemplate(this.template);
    }
  }

  hideKeyboard(){
    if(this.snackBar != undefined){
      this.snackBar.dismiss();
    }
  }

  onEnter(value: string) {
    this.value = value;
  }

  statusStyle = {
    "font-size": "12px",
    "font-weight": "bold",
    "color": "#F00"
  };
}