export interface Book {
  bookId : string;
  bookName : string;
  bookAuthor: { name: "" };
  bookPublisher: { name: "" };
  tags: any;
  bookQuantities : number;
  bookVolumes : number;
}
