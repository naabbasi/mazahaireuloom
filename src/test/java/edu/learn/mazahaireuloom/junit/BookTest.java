package edu.learn.mazahaireuloom.junit;

import edu.learn.mazahaireuloom.config.AppConfig;
import edu.learn.mazahaireuloom.entities.Book;
import edu.learn.mazahaireuloom.entities.BookAuthor;
import edu.learn.mazahaireuloom.entities.BookPublisher;
import edu.learn.mazahaireuloom.repos.BookRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {AppConfig.class})
@EnableAutoConfiguration
public class BookTest {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    @Test
    public void pass_001() {
        Book book = new Book();
        book.setBookName("Core Java");
        assertNotNull(this.bookRepo.save(book).block());
    }

    @Test
    public void pass_002() {
        Book book = new Book();
        book.setBookName("Core Java1");
        book.setBookAuthor(new BookAuthor("Noman ali"));
        this.bookRepo.save(book).block();
        assertNotNull(this.bookRepo.save(book).block());
    }

    @Test
    public void pass_003() {
        Book book = new Book();
        book.setBookName("Core Java2");
        book.setBookPublisher(new BookPublisher("Oracle"));
        this.bookRepo.save(book).block();
        assertNotNull(this.bookRepo.save(book).block());
    }

    @Test
    public void pass_004() {
        assertNotNull(this.bookRepo.findByBookName("Core Java").block());
        assertNull(this.bookRepo.findByBookName("Core").block());
    }

    @Test
    public void pass_005() {
        assertNotNull(this.bookRepo.findByBookAuthor("Noman ali").block());
        assertNull(this.bookRepo.findByBookAuthor("Noman").block());
    }

    @Test
    public void pass_006() {
        assertNotNull(this.bookRepo.findByBookPublisher("Oracle").block());
        assertNull(this.bookRepo.findByBookPublisher("Oracle1").block());
    }

    @Test
    public void pass_007() {
        Flux<Book> books = this.bookRepo.searchBookName(mongoTemplate, "re");
        List<Book> bookList = books.collectList().block();
        assertFalse(bookList.isEmpty());
    }

    @Test
    public void pass_008() {
        Flux<Book> books = this.bookRepo.searchBookAuthor(mongoTemplate, "man");
        List<Book> bookList = books.collectList().block();
        assertFalse(bookList.isEmpty());
    }

    @Test
    public void pass_009() {
        Flux<Book> books = this.bookRepo.searchBookPublisher(mongoTemplate, "le");
        List<Book> bookList = books.collectList().block();
        assertFalse(bookList.isEmpty());
    }

    /**
     * Updating already existed record
     */
    @Test
    public void pass_010() {
        Optional<Book> book = this.bookRepo.findByBookPublisher("Oracle").blockOptional();
        if(book.isPresent()){
            Book updateBook = book.get();
            updateBook.setBookAuthor(new BookAuthor("Farhan Ali"));
            this.bookRepo.save(updateBook).block();
        }
        assertNotNull(this.bookRepo.findByBookAuthor("Farhan Ali").block());
    }

    /**
     * Updating already existed record
     */
    @Test
    public void pass_011() {
        Optional<Book> book = this.bookRepo.findByBookName("Core Java").blockOptional();
        if(book.isPresent()){
            Book updateBook = book.get();
            updateBook.setBookPublisher(new BookPublisher("xxxxxx"));
            updateBook.setBookAuthor(new BookAuthor("xxxxxx"));
            assertNotNull(this.bookRepo.updateBook(updateBook, updateBook.getBookId()).block());
        }
    }
}
