package edu.learn.mazahaireuloom.services;

import edu.learn.mazahaireuloom.entities.Library;
import edu.learn.mazahaireuloom.repos.LibraryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LibraryService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final LibraryRepo libraryRepo;

    public Mono<Library> findOne(String libraryId) {
        var library = new Library();
        library.setLibraryId(libraryId);
        return this.libraryRepo.findOne(Example.of(library));
    }

    public Mono<Library> save(Library library) {
        return this.libraryRepo.save(library);
    }

    public Mono<Library> findByLibraryName(String libraryName) {
        return this.libraryRepo.findByLibraryName(libraryName);
    }

    public Flux<Library> findByLibraryNameAndShelfNumber(String libraryName, String shelfNumber) {
        return Flux.just(new Library());//this.libraryRepo.findByLibraryNameAndShelfNumber(libraryName, shelfNumber);
    }

    public Mono<Void> deleteByLibraryId(String libraryId) {
        return this.libraryRepo.deleteById(libraryId);
    }

    public Flux<Library> findAll() {
        return this.libraryRepo.findAll();
    }

    public Flux<Library> searchLibrary(ReactiveMongoTemplate reactiveMongoTemplate, String library) {
        return this.libraryRepo.searchLibrary(reactiveMongoTemplate, library);
    }
}
