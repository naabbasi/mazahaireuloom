package edu.learn.mazahaireuloom.rest;

import edu.learn.mazahaireuloom.entities.Library;
import edu.learn.mazahaireuloom.services.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
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
import java.util.List;
import java.util.Locale;

@Slf4j
@RequestMapping(path = "/api/libraries")
@RestController
@RequiredArgsConstructor
public class LibraryRest {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final LibraryService libraryService;

    @GetMapping
    public Flux<Library> all() {
        return this.libraryService.findAll();
    }

    @GetMapping("{libraryId}")
    public Mono<Library> findLibrary(@PathVariable("libraryId") String libraryId) {
        return this.libraryService.findOne(libraryId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Library> save(@RequestBody Library library, UriComponentsBuilder builder) {
        try {
            this.libraryService.save(library).subscribe();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(library);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteLibrary(@PathVariable("id") String id) {
        try {
            this.libraryService.deleteByLibraryId(id).subscribe();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(path = "/search/{library}")
    public Flux<Library> searchLibrary(@PathVariable String library) {
        return this.libraryService.searchLibrary(this.reactiveMongoTemplate, library);
    }
}
