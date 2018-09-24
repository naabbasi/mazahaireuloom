package edu.learn.mazahaireuloom.rest;

import edu.learn.mazahaireuloom.entities.User;
import edu.learn.mazahaireuloom.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RequestMapping( path = "/api/users")
@RestController
public class UserRest {
    private UserRepo userRepo;
    private MongoTemplate mongoTemplate;

    public UserRest(UserRepo userRepo, MongoTemplate mongoTemplate) {
        this.userRepo = userRepo;
        this.mongoTemplate = mongoTemplate;
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

    @GetMapping(path = "/search/{username}")
    public List<User> search(@PathVariable String username) {
        return this.userRepo.searchUser(this.mongoTemplate, username);
    }
}
