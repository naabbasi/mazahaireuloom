package edu.learn.mazahaireuloom.junit;

import edu.learn.mazahaireuloom.entities.Library;
import edu.learn.mazahaireuloom.entities.Shelf;
import edu.learn.mazahaireuloom.services.LibraryService;
import edu.learn.mazahaireuloom.services.ShelfService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ShelfTest {
    @Autowired
    private LibraryService libraryService;
    @Autowired
    private ShelfService shelfService;
    private static Library savedLibrary;
    private static Shelf savedShelf;

    @Test
    public void pass_001() {
        Library library = new Library();
        library.setLibraryName("Room-1");
        library.setLibraryNumber("Room-1");
        savedLibrary = this.libraryService.save(library).block();
        assertNotNull(savedLibrary);
    }

    @Test
    public void pass_002() {
        Library getLibraryById = this.libraryService.findOne(savedLibrary.getId()).block();
        assertNotNull(getLibraryById);

        Shelf shelf = new Shelf();
        shelf.setShelfName("Tafseer e Quran");
        shelf.setShelfNumber("TEQ-000");
        shelf.setLibrary(getLibraryById);
        savedShelf = this.shelfService.save(shelf).block();

        Flux<Shelf> shelves = this.shelfService.findAllByLibraryId(savedLibrary.getId());
        assert shelves != null;
        List<Shelf> shelfList = shelves.collectList().block();
        Assertions.assertFalse(Objects.requireNonNull(shelfList).isEmpty());
    }

    @Test
    public void pass_003() {
        Shelf getShelfByLibraryAndShelf = this.shelfService.findShelfByLibraryIdAndShelfId(savedLibrary.getId(), savedShelf.getShelfId()).block();
        assert getShelfByLibraryAndShelf != null;
        getShelfByLibraryAndShelf.setShelfNumber("TEQ-001");

        this.shelfService.save(getShelfByLibraryAndShelf).block();

        getShelfByLibraryAndShelf = this.shelfService.findOne(savedShelf.getShelfId()).block();
        assert getShelfByLibraryAndShelf != null;
        assertEquals("TEQ-001", getShelfByLibraryAndShelf.getShelfNumber());
    }

    @Test
    public void pass_004() {
        Shelf getShelfByLibraryAndShelf = this.shelfService.findShelfByLibraryIdAndShelfId(savedLibrary.getId(), savedShelf.getShelfId()).block();

        assertNotNull(getShelfByLibraryAndShelf);
        assertEquals("TEQ-001", getShelfByLibraryAndShelf.getShelfNumber());
    }

    @Test
    public void pass_005() {
        /*assertNotNull(this.libraryService.findByLibraryNameAndShelfNumber("Noman ali").block());
        assertNull(this.libraryService.findByLibraryNameAndShelfNumber("Noman").block());*/
    }

    @Test
    public void pass_006() {
        /*assertNotNull(this.libraryService.findByLibraryPublisher("Oracle").block());
        assertNull(this.libraryService.findByLibraryPublisher("Oracle1").block());*/
    }

    @Test
    public void pass_011() {
        Optional<Shelf> shelf = this.shelfService.findByShelfName(savedShelf.getShelfName()).blockOptional();
        shelf.ifPresent(value -> this.shelfService.deleteByShelfId(value.getShelfId()).block());
    }

    @Test
    public void pass_012() {
        Optional<Library> library = this.libraryService.findByLibraryName(savedLibrary.getLibraryName()).blockOptional();
        library.ifPresent(value -> this.libraryService.deleteByLibraryId(value.getId()).block());
    }
}
