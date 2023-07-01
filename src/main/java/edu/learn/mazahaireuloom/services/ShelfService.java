package edu.learn.mazahaireuloom.services;

import edu.learn.mazahaireuloom.entities.Shelf;
import edu.learn.mazahaireuloom.repos.ShelfRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShelfService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final ShelfRepo shelfRepo;

    public Mono<Shelf> findOne(String shelfId) {
        var shelf = new Shelf();
        shelf.setShelfId(shelfId);
        return this.shelfRepo.findOne(Example.of(shelf));
    }

    public Mono<Shelf> save(Shelf shelf) {
        return this.shelfRepo.save(shelf);
    }

    public Mono<Shelf> findByShelfName(String shelfName) {
        return this.shelfRepo.findByShelfName(shelfName);
    }

    public Mono<Shelf> findByShelfNumber(String shelfName) {
        return this.shelfRepo.findByShelfName(shelfName);
    }

    public Mono<Void> deleteByShelfId(String shelfId) {
        return this.shelfRepo.deleteById(shelfId);
    }

    public Flux<Shelf> findAll() {
        return this.shelfRepo.findAll();
    }

    public Flux<Shelf> findAllByLibraryId(String libraryId) {
        return this.shelfRepo.findAllShelfByLibrary(libraryId);
    }

    public Mono<Shelf> findShelfByLibraryIdAndShelfId(String libraryId, String shelfId) {
        return this.shelfRepo.findShelfByLibraryAndShelfId(libraryId, shelfId);
    }

    public Flux<Shelf> searchShelf(String shelfName, String shelfNumber) {
        return this.shelfRepo.searchLibrary(this.reactiveMongoTemplate, shelfName, shelfNumber);
    }
}
