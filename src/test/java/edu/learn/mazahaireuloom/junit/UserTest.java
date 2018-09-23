package edu.learn.mazahaireuloom.junit;

import edu.learn.mazahaireuloom.config.AppConfig;
import edu.learn.mazahaireuloom.entities.User;
import edu.learn.mazahaireuloom.repos.UserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppConfig.class})
@EnableAutoConfiguration
public class UserTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void pass_1() {
        User user = this.userRepo.save(new User("\uFDF2", "password")).block();
        assertNotNull(user);
        user = this.userRepo.save(new User("nabbasi", "password")).block();
        assertNotNull(user);
        user = this.userRepo.save(new User("farhan", "password")).block();
        assertNotNull(user);
        user = this.userRepo.save(new User("arsalan", "password")).block();
        assertNotNull(user);
        user = this.userRepo.save(new User("waqar", "password")).block();
        assertNotNull(user);
        user = this.userRepo.save(new User("waqas", "password")).block();
        assertNotNull(user);
    }

    @Test
    public void pass_2() {
        Iterable<User> users = this.userRepo.findAll().collectList().block();
        users.forEach(System.out::println);
        assertTrue(users.iterator().hasNext());
    }

    @Test
    public void pass_3() {
        Query query = Query.query(TextCriteria.forDefaultLanguage().matching("nabbasi"));
        List<User> users = this.mongoTemplate.find(query, User.class);
        assertFalse(users.isEmpty());
    }

    @Test
    public void pass_4() {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny("nabbasi", "fabbasi");
        Query query = TextQuery.queryText(criteria)
                .sortByScore()
                .with(PageRequest.of(0, 5));

        List<User> users = this.mongoTemplate.find(query, User.class);
        assertFalse(users.isEmpty());
    }

    @Test
    public void pass_5() {
        //Case insensitive searching
        Criteria regex = Criteria.where("username").regex("S", "i");
        Query query = new Query();
        query.limit(10);
        query.addCriteria(regex);
        List<User> users = mongoTemplate.find(query, User.class);
        assertFalse(users.isEmpty());
    }

    @Test
    public void pass_6() {
        //Case sensitive searching
        Criteria regex = Criteria.where("username").regex("abb");
        Query query = new Query();
        query.limit(10);
        query.addCriteria(regex);
        List<User> users = mongoTemplate.find(query, User.class);
        assertFalse(users.isEmpty());
    }

    @Test
    public void pass_7() {
        //Searchin on unicode
        Criteria regex = Criteria.where("username").regex("\uFDF2");
        Query query = new Query();
        query.limit(10);
        query.addCriteria(regex);
        List<User> users = mongoTemplate.find(query, User.class);
        assertFalse(users.isEmpty());
    }
}
