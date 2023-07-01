import {Component, OnInit} from '@angular/core';
import {HttpConfig} from "../../../config/httpconfig";
import {MAT_MOMENT_DATE_FORMATS, MomentDateAdapter} from '@angular/material-moment-adapter';
import {DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE} from '@angular/material/core';
import {MatSnackBar} from "@angular/material/snack-bar";
import {GenericComponent} from "../../GenericComponent";
import {Shelf} from "../../entity/Shelf";
import {AsyncPipe, NgFor} from "@angular/common";
import {FormControl, ReactiveFormsModule} from "@angular/forms";
import {MatAutocompleteModule} from "@angular/material/autocomplete";
import {Observable} from "rxjs";
import {map, startWith} from "rxjs/operators";
import {Library} from "../../entity/Library";

declare var $: any;

@Component({
  selector: 'app-add-shelf',
  templateUrl: './add-shelf.component.html',
  styleUrls: ['./add-shelf.component.scss'],
  providers: [
    // The locale would typically be provided on the root module of your application. We do it at
    // the component level here, due to limitations of our example generation script.
    {provide: MAT_DATE_LOCALE, useValue: 'ar-SA'},

    // `MomentDateAdapter` and `MAT_MOMENT_DATE_FORMATS` can be automatically provided by importing
    // `MatMomentDateModule` in your applications root module. We provide it at the component level
    // here, due to limitations of our example generation script.
    {provide: DateAdapter, useClass: MomentDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
  ]
})
export class AddShelfComponent extends GenericComponent implements OnInit {
  minDate = new Date(2018, 0, 1);
  visible = true;
  shelf: Shelf = {} as Shelf;

  myControl = new FormControl('');
  options: Library[] = [{libraryName: 'لیب-۱', libraryNumber: '001', libraryId: '50d5f390-f25f-4d54-82d1-b36c81334737', shelves: null},{libraryName: 'لیب-۲', libraryNumber: '002', libraryId: '12123123', shelves: null}];
  filteredOptions: Observable<Library[]>;

  constructor(private http: HttpConfig, snackBar: MatSnackBar) {
    super(snackBar)
  }

  ngOnInit() {
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => this._filter(value || '')),
    );
  }

  private _filter(value: string): Library[] {
    const filterValue = value.toLowerCase();

    return this.options.filter(option => option.libraryName.toLowerCase().includes(filterValue));
  }

  onSave() {
    this.http.post(`/libraries/${this.shelf.libraryId}/shelves`, this.shelf).subscribe(res => {
      this.statusStyle = {
        "font-size": "12px",
        "font-weight": "normal",
        "color": "#0F0"
      };
      $('#status').html("لابیرری کا اندراج ہوچکا ہے");
    }, error => {
      $('#status').html(error);
    });
    return false;
  }
}
