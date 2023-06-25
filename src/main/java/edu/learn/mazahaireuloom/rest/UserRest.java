package edu.learn.mazahaireuloom.rest;

import edu.learn.mazahaireuloom.entities.User;
import edu.learn.mazahaireuloom.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping( path = "/api/users")
@RestController
@RequiredArgsConstructor
public class UserRest {
    private UserService userService;

    @GetMapping
    public Flux<User> all() {
        return this.userService.findAll();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean save(@RequestBody User user) {
        try{
            this.userService.save(user).subscribe(System.out::println);
        }catch (RuntimeException e){
            return false;
        }

        return true;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<User> login(@RequestBody User user) {
        Mono<User> isLoggedIn = Mono.empty();
        try{
            isLoggedIn = this.userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        }catch (RuntimeException e){
            return isLoggedIn;
        }

        return isLoggedIn;
    }

    @GetMapping(path = "/search/{username}")
    public Flux<List> search(@PathVariable String username) {
        return this.userService.searchUser(username);
    }
}
