import {Directive, ElementRef, forwardRef, Host, HostBinding, HostListener, Renderer2} from '@angular/core';
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from "@angular/forms";
import {arabicLettersAndNumbers} from "../GenericUtils";

@Directive({
  selector: '[appArabicLetterKeyboard]',
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => ArabicLetterKeyboardDirective),
      multi: true
    }
  ]
})
export class ArabicLetterKeyboardDirective implements ControlValueAccessor {
  private onChange: (value: string) => void;
  private onTouched: () => void;
  private value: string;

  constructor(private _elementRef: ElementRef, private _renderer: Renderer2) {
    console.log("ArabicLetterKeyboardDirective has been initialized")
  }

  @HostListener('input', ['$event.target.value'])
  onInputChange(value: string) {
    const filteredValue: string = this.filterValue(value);
    this.updateTextInput(filteredValue, this.value !== filteredValue);
  }

  @HostListener('blur')
  onBlur() {
    this.onTouched();
  }

  private updateTextInput(value: string, propagateChange: boolean) {
    this._renderer.setProperty(this._elementRef.nativeElement, 'value', value);
    if (propagateChange) {
      this.onChange(value);
    }

    this.value = value;
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any) {
    this.onTouched = fn;
  }

  setDisabledState(isDisabled: boolean): void {
    this._renderer.setProperty(this._elementRef.nativeElement, 'disabled', isDisabled);
  }

  writeValue(value: any): void {
    this.value = value ? String(value) : '';
    this.updateTextInput(value, false);
  }

  filterValue(value: string): string {
    let arabicWords = '';
    let valueAsArray = value.split('');
    for (let c = 0; c < valueAsArray.length; c++) {
      let isArabic = arabicLettersAndNumbers[valueAsArray[c]];
      if (isArabic !== undefined) {
        arabicWords += isArabic;
      } else {
        arabicWords += valueAsArray[c];
      }
    }

    return arabicWords;
  }
}
