package edu.learn.mazahaireuloom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Slf4j
@EntityScan(basePackages = "edu.learn.mazahaireuloom.entities")
@EnableReactiveMongoRepositories( basePackages = "edu.learn.mazahaireuloom.repos")
@SpringBootApplication(scanBasePackages = "edu.learn.mazahaireuloom")
public class MazahaireuloomApplication {

    public static void main(String[] args) {
        SpringApplication.run(MazahaireuloomApplication.class, args);
	}
}

