package edu.learn.mazahaireuloom.rest;

import edu.learn.mazahaireuloom.entities.Library;
import edu.learn.mazahaireuloom.entities.Shelf;
import edu.learn.mazahaireuloom.services.LibraryService;
import edu.learn.mazahaireuloom.services.ShelfService;
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

@Slf4j
@RequestMapping(path = "/api/libraries/{libraryId}/shelves")
@RestController
@RequiredArgsConstructor
public class ShelfRest {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final LibraryService libraryService;
    private final ShelfService shelfService;

    @GetMapping("/all")
    public Flux<Shelf> all() {
        return this.shelfService.findAll();
    }

    @GetMapping
    public Flux<Shelf> libraryShelves(@PathVariable("libraryId") String libraryId) {
        return this.shelfService.findAllByLibraryId(libraryId);
    }

    @GetMapping("{shelfId}")
    public Mono<Shelf> findShelfByLibraryIdAndShelfId(@PathVariable("libraryId") String libraryId, @PathVariable("shelfId") String shelfId) {
        return this.shelfService.findShelfByLibraryIdAndShelfId(libraryId, shelfId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@RequestBody Shelf shelf, UriComponentsBuilder builder) {
        try {
            this.shelfService.save(shelf).subscribe();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(shelf);
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
