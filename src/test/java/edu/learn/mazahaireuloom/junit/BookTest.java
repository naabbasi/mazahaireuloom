package edu.learn.mazahaireuloom.junit;

import edu.learn.mazahaireuloom.entities.Book;
import edu.learn.mazahaireuloom.entities.BookAuthor;
import edu.learn.mazahaireuloom.entities.BookPublisher;
import edu.learn.mazahaireuloom.entities.Tag;
import edu.learn.mazahaireuloom.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EnableAutoConfiguration
public class BookTest {

    @Autowired
    private BookService bookService;
    private static Book savedCoreJavaBook;
    private static Book savedCoreJava1Book;
    private static Book savedCoreJava2Book;

    @Test
    public void pass_001() {
        Book book = new Book();
        book.setBookSource("Core Java");
        book.setBookName("Core Java");
        book.setBookPublishDate(LocalDate.now());
        book.setBookVolumes(10);
        book.setBookQuantities(1);

        book.setBookAuthor(new BookAuthor("Ivor Horton"));
        book.setBookPublisher(new BookPublisher("Oracle"));

        Tag tag = new Tag();
        tag.setName("Java");
        book.setTags(List.of(tag));
        savedCoreJavaBook = this.bookService.save(book).block();

        assertNotNull(savedCoreJavaBook);
    }

    @Test
    public void pass_002() {
        Book book = new Book();
        book.setBookSource("Core Java1");
        book.setBookName("Core Java1");
        book.setBookPublishDate(LocalDate.now());
        book.setBookVolumes(10);
        book.setBookQuantities(1);

        book.setBookPublisher(new BookPublisher("Sams"));
        book.setBookAuthor(new BookAuthor("Noman ali"));

        Tag tag = new Tag();
        tag.setName("Java");
        book.setTags(List.of(tag));
        savedCoreJava1Book = this.bookService.save(book).block();

        assertNotNull(savedCoreJava1Book);
    }

    @Test
    public void pass_003() {
        Book book = new Book();
        book.setBookSource("Core Java2");
        book.setBookName("Core Java2");
        book.setBookVolumes(10);
        book.setBookQuantities(1);
        book.setBookPublishDate(LocalDate.now());

        book.setBookPublisher(new BookPublisher("Packet Pub"));
        book.setBookAuthor(new BookAuthor("Farhan ali"));

        Tag tag = new Tag();
        tag.setName("Java");
        book.setTags(List.of(tag));
        savedCoreJava2Book = this.bookService.save(book).block();

        assertNotNull(savedCoreJava2Book);
    }

    @Test
    public void pass_004() {
        assertNotNull(this.bookService.findByBookName("Core Java").block());
        assertNull(this.bookService.findByBookName("Core").block());
    }

    @Test
    public void pass_005() {
        assertNotNull(this.bookService.findByBookAuthorName("Noman ali").block());
        assertNull(this.bookService.findByBookAuthorName("Noman").block());
    }

    @Test
    public void pass_006() {
        assertNotNull(this.bookService.findByBookPublisherName("Oracle").block());
        assertNull(this.bookService.findByBookPublisherName("Oracle1").block());
    }

    @Test
    public void pass_007() {
        Flux<Book> books = this.bookService.searchBookName("re");
        List<Book> bookList = books.collectList().block();
        assertFalse(bookList.isEmpty());
    }

    @Test
    public void pass_008() {
        Flux<Book> books = this.bookService.searchBookAuthor("man");
        List<Book> bookList = books.collectList().block();
        assertTrue(bookList.isEmpty());
    }

    @Test
    public void pass_009() {
        Flux<Book> books = this.bookService.searchBookPublisher("le");
        List<Book> bookList = books.collectList().block();
        assertTrue(bookList.isEmpty());
    }

    /**
     * Updating already existed record
     */
    @Test
    public void pass_010() {
        Optional<Book> book = this.bookService.findByBookPublisherName("Oracle").blockOptional();
        if (book.isPresent()) {
            Book updateBook = book.get();
            updateBook.setBookAuthor(new BookAuthor("Farhan Ali"));
            this.bookService.save(updateBook).block();
        }
        assertNotNull(this.bookService.findByBookAuthorName("Farhan Ali").block());
    }

    /**
     * Updating already existed record
     */
    @Test
    public void pass_011() {
        Optional<Book> book = this.bookService.findByBookName("Core Java").blockOptional();
        if (book.isPresent()) {
            Book updateBook = book.get();
            updateBook.setBookPublisher(new BookPublisher("xxxxxx"));
            updateBook.setBookAuthor(new BookAuthor("xxxxxx"));
            assertNotNull(this.bookService.updateBook(updateBook, updateBook.getBookId()).block());
        }
    }

    @Test
    public void pass_012() {
        Optional<Book> book = this.bookService.findByBookName("Core Java").blockOptional();
        book.ifPresent(value -> this.bookService.deleteByBookId(value.getBookId()).block());
        this.bookService.deleteByBookId(savedCoreJava1Book.getBookId()).block();
        this.bookService.deleteByBookId(savedCoreJava2Book.getBookId()).block();
    }
}
