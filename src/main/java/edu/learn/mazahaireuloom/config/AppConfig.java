package edu.learn.mazahaireuloom.config;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurerComposite;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

@Slf4j
@Configuration
public class AppConfig extends AbstractReactiveMongoConfiguration {
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(final LocalValidatorFactoryBean factory) {
        return new ValidatingMongoEventListener(factory);
    }

    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create();
    }

    @Override
    protected String getDatabaseName() {
        return "mazahireuloom";
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient(), "mazahireuloom");
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
