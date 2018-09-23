package edu.learn.mazahaireuloom.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@ComponentScan(basePackages = {"edu.learn.mazahaireuloom.rest", "edu.learn.mazahaireuloom.ui_controller"})
@EntityScan(basePackages = "edu.learn.mazahaireuloom.entities")
@EnableReactiveMongoRepositories( basePackages = "edu.learn.mazahaireuloom.repos")
public class AppConfig {
}