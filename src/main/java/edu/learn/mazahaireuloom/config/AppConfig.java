package edu.learn.mazahaireuloom.config;


import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Component
@ComponentScan(basePackages =  {"edu.learn.mazahaireuloom.rest", "edu.learn.mazahaireuloom.ui_controller"})
@EntityScan(basePackages = "edu.learn.mazahaireuloom.entities")
@EnableReactiveMongoRepositories( basePackages = "edu.learn.mazahaireuloom.repos")
public class AppConfig {

    @Bean
    public WebFluxConfigurer corsConfigurer() {
        List<String> hostnames = new ArrayList<>();
        try {
            hostnames.add("http://localhost:4200");
            hostnames.add("http://localhost:90");
            hostnames.add("http://b4ca4abf.ngrok.io");
            hostnames.add("http://" + InetAddress.getLocalHost().getHostName().toLowerCase() + ":90");
        } catch (UnknownHostException e) {
            e.printStackTrace();
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