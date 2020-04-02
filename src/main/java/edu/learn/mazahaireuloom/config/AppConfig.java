package edu.learn.mazahaireuloom.config;


import edu.learn.mazahaireuloom.entities.User;
import edu.learn.mazahaireuloom.repos.BookRepo;
import edu.learn.mazahaireuloom.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;

@Slf4j
@Component
@ComponentScan(basePackages =  {"edu.learn.mazahaireuloom.rest", "edu.learn.mazahaireuloom.ui_controller"})
@EntityScan(basePackages = "edu.learn.mazahaireuloom.entities")
@EnableReactiveMongoRepositories( basePackages = "edu.learn.mazahaireuloom.repos")
public class AppConfig {
    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(new LocalValidatorFactoryBean());
    }



    @Bean
    public CommandLineRunner init(@Qualifier("userRepo") UserRepo userRepo, @Qualifier("bookRepo") BookRepo bookRepo){
        return args -> {
            try{
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

            }catch (DataAccessException e){
                log.error("Exception:  " , e);
            }

        };
    }

    @Bean
    public WebFluxConfigurer corsConfigurer() {
        var hostnames = new ArrayList<>();
        try {
            hostnames.add("http://localhost");
            hostnames.add("http://localhost:90");
            hostnames.add("http://localhost:4200");
            hostnames.add("http://" + InetAddress.getLocalHost().getHostName().toLowerCase() + ":90");
        } catch (UnknownHostException e) {
            log.error("AppConfig-> corsConfigurer: " ,e);
        }
        return new WebFluxConfigurerComposite() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(hostnames.toArray(new String[0]))
                        .allowCredentials(true)
                        .allowedHeaders("Origin", "Cache-Control", "accept", "Content-Type", "X-Auth-Token", "X-Requested-With")
                        .allowedMethods("GET", "POST", "OPTIONS", "DELETE", "PUT")
                        .exposedHeaders("Content-Disposition");
            }
        };
    }
}
