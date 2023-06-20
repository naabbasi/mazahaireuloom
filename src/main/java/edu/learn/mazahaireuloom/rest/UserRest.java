package edu.learn.mazahaireuloom.rest;

import edu.learn.mazahaireuloom.entities.User;
import edu.learn.mazahaireuloom.repos.UserRepo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping( path = "/api/users")
@RestController
public class UserRest {
    private UserRepo userRepo;
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public UserRest(UserRepo userRepo, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.userRepo = userRepo;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @GetMapping
    public Flux<User> all() {
        return this.userRepo.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean save(@RequestBody User user) {
        try{
            this.userRepo.save(user).subscribe(System.out::println);
        }catch (RuntimeException e){
            return false;
        }

        return true;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> login(@RequestBody User user) {
        Mono<User> isLoggedIn = Mono.empty();
        try{
            isLoggedIn = this.userRepo.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        }catch (RuntimeException e){
            return isLoggedIn;
        }

        return isLoggedIn;
    }

    @GetMapping(path = "/search/{username}")
    public Flux<List> search(@PathVariable String username) {
        return this.userRepo.searchUser(this.reactiveMongoTemplate, username);
    }
}
