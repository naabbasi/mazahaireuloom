package edu.learn.mazahaireuloom.repos;

import edu.learn.mazahaireuloom.entities.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public interface UserRepo extends GenericRepo<User, String> {

    User findByUsername(String username);

    default List<User> searchUser(MongoTemplate mongoTemplate, String username) {
        Criteria regex = Criteria.where("username").regex(username, "i");
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(10);
        query.addCriteria(regex);
        return mongoTemplate.find(query, User.class);
    }
}
