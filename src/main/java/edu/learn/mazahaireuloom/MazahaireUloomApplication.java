package edu.learn.mazahaireuloom;

import edu.learn.mazahaireuloom.entities.User;
import edu.learn.mazahaireuloom.repos.BookRepo;
import edu.learn.mazahaireuloom.repos.UserRepo;
import edu.learn.mazahaireuloom.services.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Slf4j
@EntityScan(basePackages = "edu.learn.mazahaireuloom.entities")
@EnableReactiveMongoRepositories(basePackages = "edu.learn.mazahaireuloom.repos")
@SpringBootApplication(scanBasePackages = "edu.learn.mazahaireuloom")
public class MazahaireUloomApplication {

    @Bean
    public CommandLineRunner init(UserRepo userRepo, BookRepo bookRepo, LibraryService libraryService) {
        return args -> {
            try {
                var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
                userRepo.deleteAll().block();
                userRepo.save(new User("اسد", encoder.encode("اسد"))).block();
                userRepo.save(new User("noman", encoder.encode("786"))).block();
                userRepo.save(new User("اصغر", encoder.encode("اصغر"))).block();
                userRepo.save(new User("نعمان", encoder.encode("نعمان"))).block();

                var arabicLocale = new Locale.Builder().setLanguageTag("ar-SA-u-nu-arab").build();
                var date = LocalDate.now();
                var formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(arabicLocale);

                String formatted = date.format(formatter);
                System.out.println(formatted);
                System.out.println(formatter.parse(formatted));

                /*for(int i = 0 ; i < 50000 ; i++){
                    var book = new Book();
                    book.setBookName( i + "اصغر");
                    book.setBookAuthor(new BookAuthor("نعمان"));
                    book.setBookPublisher(new BookPublisher("نعمان"));
                    var tags = List.of(new Tag("اصغرنعماننعمان"));
                    book.setTags(tags);
                    bookRepo.save(book).block();

                    book = new Book();
                    book.setBookName(i + "نعمان");
                    book.setBookAuthor(new BookAuthor("اصغر"));
                    book.setBookPublisher(new BookPublisher("اصغر"));
                    tags = List.of(new Tag("ا صغ رنع مانن عمان"));
                    book.setTags(tags);
                    bookRepo.save(book).block();
                }*/

            } catch (DataAccessException e) {
                log.error("Exception:  ", e);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(MazahaireUloomApplication.class, args);
    }
}

