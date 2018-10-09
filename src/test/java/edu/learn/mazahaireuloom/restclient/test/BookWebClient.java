package edu.learn.mazahaireuloom.restclient.test;

import edu.learn.mazahaireuloom.config.AppConfig;
import edu.learn.mazahaireuloom.entities.Book;
import edu.learn.mazahaireuloom.entities.BookAuthor;
import edu.learn.mazahaireuloom.entities.BookPublisher;
import edu.learn.mazahaireuloom.entities.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@EnableAutoConfiguration
public class BookWebClient {
    @Autowired
    private WebTestClient webTestClient;
    private static String bookId;

    @Test
    public void pass_1() {
        this.webTestClient.get().uri("/api/books")
            .exchange()
            .expectStatus()
            .isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
            .expectBodyList(Book.class);
    }

    @Test
    public void pass_2() {
        Book book = new Book();
        bookId = book.getBookId();
        book.setBookName("WebClient - Book");
        book.setBookAuthor(new BookAuthor("WebClient - Book Author"));
        book.setBookPublisher(new BookPublisher("WebClient - Book Publisher"));
        book.setTags(List.of(new Tag("WebClient - Tag1"),new Tag("WebClient - Tag2")));

        this.webTestClient.post().uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(book), Book.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody();
    }

    @Test
    public void pass_3() {
        this.webTestClient.get().uri("/api/books/{bookId}", bookId)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody();
    }

    @Test
    public void pass_4() {
        this.webTestClient.delete().uri("/api/books/"+ bookId)
                .exchange()
                .expectStatus().isNoContent()
                .expectBody();
    }
}
