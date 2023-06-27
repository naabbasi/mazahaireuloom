package edu.learn.mazahaireuloom.junit;

import edu.learn.mazahaireuloom.entities.Library;
import edu.learn.mazahaireuloom.entities.Shelf;
import edu.learn.mazahaireuloom.services.LibraryService;
import edu.learn.mazahaireuloom.services.ShelfService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
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
        Library getLibraryById = this.libraryService.findOne(savedLibrary.getLibraryId()).block();
        assertNotNull(getLibraryById);

        Shelf shelf = new Shelf();
        shelf.setShelfName("Tafseer e Quran");
        shelf.setShelfNumber("TEQ-000");
        shelf.setLibraryId(getLibraryById.getLibraryId());
        getLibraryById.setShelves(List.of(shelf));
        savedShelf = this.shelfService.save(shelf).block();
    }

    @Test
    public void pass_003() {
        Shelf getSavedShelf = this.shelfService.findOne(savedShelf.getShelfId()).block();
        assert getSavedShelf != null;
        getSavedShelf.setShelfNumber("TEQ-001");

        this.shelfService.save(getSavedShelf).block();

        getSavedShelf = this.shelfService.findOne(savedShelf.getShelfId()).block();
        assert getSavedShelf != null;
        assertEquals("TEQ-001", getSavedShelf.getShelfNumber());
    }

    @Test
    public void pass_004() {
        Shelf getShelfByLibraryAndShelf = this.shelfService.findShelfByLibraryIdAndShelfId(savedLibrary.getLibraryId(), savedShelf.getShelfId()).block();

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
        library.ifPresent(value -> this.libraryService.deleteByLibraryId(value.getLibraryId()).block());
    }
}
