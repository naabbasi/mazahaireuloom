package edu.learn.mazahaireuloom.repos;

import edu.learn.mazahaireuloom.entities.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRepo extends GenericRepo<User, String> {

    Mono<User> findByUsername(String username);

    Mono<User> findByUsernameAndPassword(String username, String password);

    default List<User> searchUser(MongoTemplate mongoTemplate, String username) {
        Criteria regex = Criteria.where("username").regex(username, "i");
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(10);
        query.addCriteria(regex);
        return mongoTemplate.find(query, User.class);
    }
}
