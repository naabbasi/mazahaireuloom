package edu.learn.mazahaireuloom.rest;

import edu.learn.mazahaireuloom.entities.Book;
import edu.learn.mazahaireuloom.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Boolean save(@RequestBody Book book) {
        try{
            this.bookRepo.save(book).subscribe(System.out::println);
        }catch (RuntimeException e){
            return false;
        }

        return true;
    }

    @DeleteMapping(path = "/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String id) {
        return this.bookRepo.deleteById(id);
    }

    @GetMapping(path = "/search/bookName/{bookName}")
    public List<Book> searchBookName(@PathVariable String username) {
        return this.bookRepo.searchBookName(this.mongoTemplate, username);
    }

    @GetMapping(path = "/search/bookAuthor/{bookAuthor}")
    public List<Book> searchBookAuthor(@PathVariable String username) {
        return this.bookRepo.searchBookAuthor(this.mongoTemplate, username);
    }

    @GetMapping(path = "/search/bookPublisher/{bookPublisher}")
    public List<Book> searchBookPublisher(@PathVariable String username) {
        return this.bookRepo.searchBookPublisher(this.mongoTemplate, username);
    }
}
