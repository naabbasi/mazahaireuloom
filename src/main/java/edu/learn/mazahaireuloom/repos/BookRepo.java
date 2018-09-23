package edu.learn.mazahaireuloom.repos;

import edu.learn.mazahaireuloom.entities.Book;
import edu.learn.mazahaireuloom.entities.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public interface BookRepo extends GenericRepo<Book, String> {

    default List<Book> searchBookName(MongoTemplate mongoTemplate, String bookName) {
        Criteria regex = Criteria.where("bookName").regex(bookName, "i");
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(20);
        query.addCriteria(regex);
        return mongoTemplate.find(query, Book.class);
    }

    default List<Book> searchBookAuthor(MongoTemplate mongoTemplate, String bookName) {
        Criteria regex = Criteria.where("bookAuthor").regex(bookName, "i");
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(20);
        query.addCriteria(regex);
        return mongoTemplate.find(query, Book.class);
    }

    default List<Book> searchBookPublisher(MongoTemplate mongoTemplate, String bookName) {
        Criteria regex = Criteria.where("bookPublisher").regex(bookName, "i");
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(20);
        query.addCriteria(regex);
        return mongoTemplate.find(query, Book.class);
    }
}
