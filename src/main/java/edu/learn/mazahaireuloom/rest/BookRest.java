package edu.learn.mazahaireuloom.rest;

import edu.learn.mazahaireuloom.entities.Book;
import edu.learn.mazahaireuloom.repos.BookRepo;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

@RequestMapping( path = "/api/books")
@RestController
public class BookRest {

    private final MongoTemplate mongoTemplate;
    private final BookRepo bookRepo;

    public BookRest(BookRepo bookRepo, MongoTemplate mongoTemplate) {
        this.bookRepo = bookRepo;
        this.mongoTemplate = mongoTemplate;
    }

    @GetMapping
    public Flux<Book> all() {
        return this.bookRepo.findAll();
    }

    @GetMapping("{bookId}")
    public Mono<Book> findBook(@PathVariable("bookId") String bookId) {
        Book book = new Book();
        book.setBookId(bookId);
        return this.bookRepo.findOne(Example.of(book));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> save(@RequestBody Book book, UriComponentsBuilder builder) {
        try{
            this.bookRepo.save(book).subscribe();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }

        Locale arabicLocale = new Locale.Builder().setLanguageTag("ar-SA-u-nu-arab").build();
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(arabicLocale);

        String formatted = date.format(formatter);
        System.out.println(formatted);
        System.out.println(formatter.parse(formatted));

        //return new ResponseEntity<>(book, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") String id) {
        try{
            this.bookRepo.deleteById(id).subscribe();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/search/{book}")
    public List<Book> searchBook(@PathVariable String book) {
        return this.bookRepo.searchBook(this.mongoTemplate, book);
    }

    @GetMapping(path = "/search/bookName/{bookName}")
    public List<Book> searchBookName(@PathVariable String bookName) {
        return this.bookRepo.searchBookName(this.mongoTemplate, bookName);
    }

    @GetMapping(path = "/search/bookAuthor/{bookAuthor}")
    public List<Book> searchBookAuthor(@PathVariable String bookAuthor) {
        return this.bookRepo.searchBookAuthor(this.mongoTemplate, bookAuthor);
    }

    @GetMapping(path = "/search/bookPublisher/{bookPublisher}")
    public List<Book> searchBookPublisher(@PathVariable String bookPublisher) {
        return this.bookRepo.searchBookPublisher(this.mongoTemplate, bookPublisher);
    }
}
