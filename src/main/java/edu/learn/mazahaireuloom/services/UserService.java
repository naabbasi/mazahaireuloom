package edu.learn.mazahaireuloom.services;

import edu.learn.mazahaireuloom.entities.User;
import edu.learn.mazahaireuloom.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final UserRepo userRepo;

    public Flux<User> findAll() {
        return this.userRepo.findAll();
    }

    public Mono<User> save(User user) {
        return this.userRepo.save(user);
    }

    public Mono<User> findByUsernameAndPassword(String username, String password) {
        return this.userRepo.findByUsernameAndPassword(username, password);
    }

    public Flux<List> searchUser(String username) {
        return this.userRepo.searchUser(reactiveMongoTemplate, username);
    }
}
