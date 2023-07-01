package edu.learn.mazahaireuloom.junit;

import edu.learn.mazahaireuloom.entities.User;
import edu.learn.mazahaireuloom.services.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    public void pass_1() {
        User user = this.userService.save(new User("\uFDF2", "password")).block();
        assertNotNull(user);
    }

    @Test
    public void pass_2() {
        Iterable<User> users = this.userService.findAll().collectList().block();
        assert users != null;
        assertTrue(users.iterator().hasNext());
    }

    @Disabled
    @Test
    public void pass_3() {
        Query query = Query.query(TextCriteria.forDefaultLanguage().matching("nabbasi"));
        Flux<User> users = this.reactiveMongoTemplate.find(query, User.class);
        List<User> userList = users.collectList().block();
        assertFalse(Objects.requireNonNull(userList).isEmpty());
    }

    @Disabled
    @Test
    public void pass_4() {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny("nabbasi", "fabbasi");
        Query query = TextQuery.queryText(criteria)
                .sortByScore()
                .with(PageRequest.of(0, 5));

        Flux<User> users = this.reactiveMongoTemplate.find(query, User.class);
        List<User> userList = users.collectList().block();
        assertFalse(Objects.requireNonNull(userList).isEmpty());
    }

    @Test
    public void pass_5() {
        //Case insensitive searching
        Criteria regex = Criteria.where("username").regex("\uFDF2", "i");
        Query query = new Query();
        query.limit(10);
        query.addCriteria(regex);

        Flux<User> users = this.reactiveMongoTemplate.find(query, User.class);
        List<User> userList = users.collectList().block();
        assertFalse(Objects.requireNonNull(userList).isEmpty());
    }

    @Test
    public void pass_6() {
        //Case sensitive searching
        Criteria regex = Criteria.where("username").regex("\uFDF2");
        Query query = new Query();
        query.limit(10);
        query.addCriteria(regex);

        Flux<User> users = this.reactiveMongoTemplate.find(query, User.class);
        List<User> userList = users.collectList().block();
        assertFalse(Objects.requireNonNull(userList).isEmpty());
    }

    @Test
    public void pass_7() {
        //Searchin on unicode
        Criteria regex = Criteria.where("username").regex("\uFDF2");
        Query query = new Query();
        query.limit(10);
        query.addCriteria(regex);

        Flux<User> users = this.reactiveMongoTemplate.find(query, User.class);
        List<User> userList = users.collectList().block();
        assertFalse(Objects.requireNonNull(userList).isEmpty());
    }
}
