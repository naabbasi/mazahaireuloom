package edu.learn.mazahaireuloom.junit;

import edu.learn.mazahaireuloom.entities.Library;
import edu.learn.mazahaireuloom.services.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LibraryTest {
    @Autowired
    private LibraryService libraryService;
    private static Library savedLibrary;
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
        getLibraryById.setLibraryNumber("Room-001");
        this.libraryService.save(getLibraryById).block();

        getLibraryById = this.libraryService.findOne(savedLibrary.getLibraryId()).block();
        assertEquals("Room-001", getLibraryById.getLibraryNumber());
    }

    @Test
    public void pass_003() {
        /*Library library = new Library();
        library.setLibraryName("Core Java2");
        library.setShelves(new LibraryPublisher("Oracle"));
        this.libraryService.save(library).block();
        assertNotNull(this.libraryService.save(library).block());*/
    }

    @Test
    public void pass_004() {
        assertNotNull(this.libraryService.findByLibraryName("Room-1").block());
        assertNull(this.libraryService.findByLibraryName("Room").block());
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
    public void pass_012() {
        Optional<Library> library = this.libraryService.findByLibraryName("Room-1").blockOptional();
        library.ifPresent(value -> this.libraryService.deleteByLibraryId(value.getLibraryId()).block());
    }
}
