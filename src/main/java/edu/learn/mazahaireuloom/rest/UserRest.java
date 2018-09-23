package edu.learn.mazahaireuloom.rest;

import edu.learn.mazahaireuloom.entities.User;
import edu.learn.mazahaireuloom.exception.RestException;
import edu.learn.mazahaireuloom.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true", exposedHeaders = "true")
@RequestMapping( path = "/api/users")
@RestController
public class UserRest {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MongoTemplate mongoTemplate;


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
