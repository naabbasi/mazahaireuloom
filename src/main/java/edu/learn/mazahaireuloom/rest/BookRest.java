package edu.learn.mazahaireuloom.rest;

import edu.learn.mazahaireuloom.entities.Book;
import edu.learn.mazahaireuloom.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequestMapping( path = "/api/books")
@RestController
@RequiredArgsConstructor
public class BookRest {
    private final BookService bookService;

    @GetMapping
    public Flux<Book> all() {
        return this.bookService.findAll();
    }

    @GetMapping("{bookId}")
    public Mono<Book> findBook(@PathVariable("bookId") String bookId) {
        return this.bookService.findOne(bookId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> save(@Valid @RequestBody Book book, UriComponentsBuilder builder) {
        AtomicReference<Book> savedBook = new AtomicReference<>();
        try{
            this.bookService.save(book).doOnError( error -> {
                log.error("save(...) ", error);
            }).subscribe(getSavedBook -> {
                savedBook.set(getSavedBook);
            });
        }catch (RuntimeException e){
            log.error("save(...) ", e);
            return ResponseEntity.badRequest().build();
        }

        var arabicLocale = new Locale.Builder().setLanguageTag("ar-SA-u-nu-arab").build();
        var date = LocalDate.now();
        var formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(arabicLocale);

        var formatted = date.format(formatter);
        System.out.println(formatted);
        System.out.println(formatter.parse(formatted));

        //return new ResponseEntity<>(book, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> update(@Valid @RequestBody Book book) {
        try{
            this.bookService.save(book);
        }catch (RuntimeException e){
            log.error("update(...) ", e);
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(book);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteBook(@PathVariable("id") String id) {
        try{
            this.bookService.deleteByBookId(id).subscribe();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/search/{book}")
    public Flux<Book> searchBook(@PathVariable String book, @RequestParam Map<String, Object> params) {
        System.out.println(params);
        return this.bookService.searchBook(book, params);
    }

    @GetMapping(path = "/search/bookName/{bookName}")
    public Flux<Book> searchBookName(@PathVariable String bookName) {
        return this.bookService.searchBookName(bookName);
    }

    @GetMapping(path = "/search/bookAuthor/{bookAuthor}")
    public Flux<Book> searchBookAuthor(@PathVariable String bookAuthor) {
        return this.bookService.searchBookAuthor(bookAuthor);
    }

    @GetMapping(path = "/search/bookPublisher/{bookPublisher}")
    public Flux<Book> searchBookPublisher(@PathVariable String bookPublisher) {
        return this.bookService.searchBookPublisher(bookPublisher);
    }
}
