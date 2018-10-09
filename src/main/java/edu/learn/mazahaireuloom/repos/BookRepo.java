package edu.learn.mazahaireuloom.repos;

import edu.learn.mazahaireuloom.entities.Book;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookRepo extends GenericRepo<Book, String> {

    Mono<Book> findByBookName(String bookName);

    @Query(value = "{'author.name': ?0}")
    Mono<Book> findByBookAuthor(String bookAuthor);

    @Query(value = "{'publisher.name': ?0}")
    Mono<Book> findByBookPublisher(String bookPublisher);

    default Mono<Book> updateBook(Book book, String id){
        Book findBook = new Book();
        findBook.setBookId(id);

        return findOne(Example.of(findBook)).doOnSuccess( foundBook -> {
            foundBook.setBookAuthor(book.getBookAuthor());
            foundBook.setBookPublisher(book.getBookPublisher());
            save(foundBook);
        });
    }

    default Flux<Book> searchBook(ReactiveMongoTemplate mongoTemplate, String book) {

        Criteria regex = new Criteria();
        regex.orOperator(
            Criteria.where("bookName").regex(book, "i"),
            Criteria.where("author.name").regex(book, "i"),
            Criteria.where("publisher.name").regex(book, "i"),
            Criteria.where("tags.name").regex(book, "i")
        );

        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(20);
        query.addCriteria(regex);
        return mongoTemplate.find(query, Book.class);
    }

    default Flux<Book> searchBookName(ReactiveMongoTemplate mongoTemplate, String bookName) {
        Criteria regex = Criteria.where("bookName").regex(bookName, "i");
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(20);
        query.addCriteria(regex);
        return mongoTemplate.find(query, Book.class);
    }

    default Flux<Book> searchBookAuthor(ReactiveMongoTemplate mongoTemplate, String authorName) {
        Criteria regex = Criteria.where("author.name").regex(authorName, "i");
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(20);
        query.addCriteria(regex);
        return mongoTemplate.find(query, Book.class);
    }

    default Flux<Book> searchBookPublisher(ReactiveMongoTemplate mongoTemplate, String publisherName) {
        Criteria regex = Criteria.where("publisher.name").regex(publisherName, "i");
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(20);
        query.addCriteria(regex);
        return mongoTemplate.find(query, Book.class);
    }
}
