export class Book {
  bookId : string;
  bookSource : string;
  bookName : string;
  bookAuthor: { bookAuthorName: string };
  bookPublisher: { bookPublisherName: string };
  bookPublishDateMomentum : any;
  bookPublishDate : string;
  tags: any;
  bookQuantities : number | string;
  bookVolumes : number | string;
}