package edu.learn.mazahaireuloom.restclient.test;

import edu.learn.mazahaireuloom.entities.Book;
import edu.learn.mazahaireuloom.entities.BookAuthor;
import edu.learn.mazahaireuloom.entities.BookPublisher;
import edu.learn.mazahaireuloom.entities.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAutoConfiguration
public class BookWebClientTest {
    @Autowired
    private WebTestClient webTestClient;
    private static String bookId;

    @Test
    public void pass_1() {
        Book book = new Book();
        book.setBookName("WebClient - Book");
        book.setBookAuthor(new BookAuthor("WebClient - Book Author"));
        book.setBookPublisher(new BookPublisher("WebClient - Book Publisher"));
        book.setTags(List.of(new Tag("WebClient - Tag1"),new Tag("WebClient - Tag2")));
        book.setBookVolumes(10);
        book.setBookQuantities(1);

        this.webTestClient.post().uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

    @Test
    public void pass_2() {
        EntityExchangeResult<List<Book>> books = this.webTestClient.get().uri("/api/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Book.class)
                .returnResult();

        assertThat(books.getStatus()).isEqualTo(HttpStatus.OK);

        List<Book> bookList = books.getResponseBody();
        assertThat(bookList).isNotEmpty();
        bookId = bookList.get(0).getBookId();
    }

    @Test
    public void pass_3() {
        Book book = new Book();
        book.setBookId(bookId);
        book.setBookName("Book Updated");
        book.setBookAuthor(new BookAuthor("WebClient - Book Author"));
        book.setBookPublisher(new BookPublisher("WebClient - Book Publisher"));
        book.setTags(List.of(new Tag("WebClient - Tag1"),new Tag("WebClient - Tag2")));
        book.setBookVolumes(10);
        book.setBookQuantities(1);

        this.webTestClient.put().uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus().isNoContent()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

    @Test
    public void pass_4() {
        this.webTestClient.get().uri("/api/books/{bookId}", bookId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

    @Test
    public void pass_10() {
        this.webTestClient.delete().uri("/api/books/"+ bookId)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody();
    }
}
