declare var $ : any;
export class GenericComponent {
  value: string;

  bindKeyboard(fieldName) {
    $('#' + fieldName.id).setUrduInput({urduNumerals: true});
  }

  onEnter(value: string) {
    this.value = value;
  }


}
