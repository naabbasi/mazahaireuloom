package edu.learn.mazahaireuloom.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

@Component
@ComponentScan(basePackages =  {"edu.learn.mazahaireuloom.rest", "edu.learn.mazahaireuloom.ui_controller"})
@EntityScan(basePackages = "edu.learn.mazahaireuloom.entities")
@EnableReactiveMongoRepositories( basePackages = "edu.learn.mazahaireuloom.repos")
public class AppConfig {

    @Bean
    public WebFluxConfigurer corsConfigurer() {
        return new WebFluxConfigurerComposite() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200","http://localhost")
                        .allowCredentials(true)
                        .allowedHeaders("Origin", "Cache-Control", "accept", "Content-Type", "X-Auth-Token", "X-Requested-With")
                        .allowedMethods("GET", "POST", "OPTIONS")
                        .exposedHeaders("Content-Disposition");


            }
        };
    }
}