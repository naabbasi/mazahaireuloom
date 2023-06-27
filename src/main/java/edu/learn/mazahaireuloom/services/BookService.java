package edu.learn.mazahaireuloom.services;

import edu.learn.mazahaireuloom.entities.Book;
import edu.learn.mazahaireuloom.repos.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final BookRepo bookRepo;
    public Flux<Book> findAll() {
        return this.bookRepo.findAll();
    }

    public Mono<Book> findOne(String bookId) {
        var book = new Book();
        book.setBookId(bookId);
        return this.bookRepo.findOne(Example.of(book));
    }

    public Mono<Book> findByBookName(String bookName) {
        return this.bookRepo.findByBookName(bookName);
    }
    public Mono<Book> save(Book book) {
        return this.bookRepo.save(book);
    }

    public Mono<Book> updateBook(Book updateBook, String bookId) {
        return this.bookRepo.updateBook(updateBook, bookId);
    }

    public Mono<Void> deleteByBookId(String id) {
        return this.bookRepo.deleteById(id);
    }

    public Flux<Book> searchBook(String book, Map<String, Object> params) {
        return this.bookRepo.searchBook(this.reactiveMongoTemplate, book, params);
    }

    public Flux<Book> searchBookName(String bookName) {
        return this.bookRepo.searchBookName(this.reactiveMongoTemplate, bookName);
    }

    public Flux<Book> searchBookAuthor(String bookAuthor) {
        return this.bookRepo.searchBookAuthor(this.reactiveMongoTemplate, bookAuthor);
    }

    public Flux<Book> searchBookPublisher(String bookPublisher) {
        return this.bookRepo.searchBookPublisher(this.reactiveMongoTemplate, bookPublisher);
    }

    public Mono<Book> findByBookAuthorName(String bookAuthorName) {
        return this.bookRepo.findByBookAuthorName(bookAuthorName);
    }

    public Mono<Book> findByBookPublisherName(String bookPublisherName) {
        return this.bookRepo.findByBookPublisherName(bookPublisherName);
    }
}
