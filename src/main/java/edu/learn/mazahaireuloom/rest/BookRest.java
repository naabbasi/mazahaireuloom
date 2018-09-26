package edu.learn.mazahaireuloom.rest;

import edu.learn.mazahaireuloom.entities.Book;
import edu.learn.mazahaireuloom.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@RequestMapping( path = "/api/books")
@RestController
public class BookRest {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private MongoTemplate mongoTemplate;


    @GetMapping
    public Flux<Book> all() {
        return this.bookRepo.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(@RequestBody Book book, UriComponentsBuilder builder) {
        try{
            this.bookRepo.save(book).subscribe();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }

        URI location = builder.path("{bookId}").buildAndExpand(book.getBookId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return this.bookRepo.deleteById(id);
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
