export interface Book {
  bookId : string;
  bookName : string;
  bookAuthor: { bookAuthorName: "" };
  bookPublisher: { bookPublisherName: "" };
  tags: any;
  bookQuantities : number;
  bookVolumes : number;
}
