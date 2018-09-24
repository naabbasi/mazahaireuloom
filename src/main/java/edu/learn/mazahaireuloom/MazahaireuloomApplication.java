package edu.learn.mazahaireuloom;

import edu.learn.mazahaireuloom.entities.User;
import edu.learn.mazahaireuloom.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Slf4j
@SpringBootApplication(scanBasePackages = "edu.learn.mazahaireuloom.config")
public class MazahaireuloomApplication {
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public CommandLineRunner init(UserRepo userRepo){
        return args -> {
            try{
                userRepo.deleteAll().block();
                userRepo.save(new User("اصغر", "786")).block();
                userRepo.save(new User("نعمان", "786")).block();
            }catch (DataAccessException e){
                log.error("Exception:  " , e);
            }

        };
    }
	public static void main(String[] args) {
		SpringApplication.run(MazahaireuloomApplication.class, args);
	}
}

