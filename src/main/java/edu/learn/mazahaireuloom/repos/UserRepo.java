package edu.learn.mazahaireuloom.repos;

import edu.learn.mazahaireuloom.entities.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Qualifier("userRepo")
@Repository
public interface UserRepo extends GenericRepo<User, String> {

    Mono<User> findByUsername(String username);

    Mono<User> findByUsernameAndPassword(String username, String password);

    default Flux<List> searchUser(ReactiveMongoTemplate reactiveMongoTemplate, String username) {
        Criteria regex = Criteria.where("username").regex(username, "i");
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(10);
        query.addCriteria(regex);
        return reactiveMongoTemplate.find(query, List.class);
    }
}
