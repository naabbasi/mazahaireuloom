package edu.learn.mazahaireuloom.repos;

import edu.learn.mazahaireuloom.entities.Library;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface LibraryRepo extends GenericRepo<Library, String> {

    Mono<Library> findByLibraryName(String libraryName);
    //Flux<Library> findByLibraryNameAndShelfNumber(String libraryName, String shelfNumber);

    default Flux<List> searchLibrary(ReactiveMongoTemplate reactiveMongoTemplate, String libraryName) {
        Criteria regex = Criteria.where("libraryName").regex(libraryName, "i");
        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(10);
        query.addCriteria(regex);
        return reactiveMongoTemplate.find(query, List.class);
    }
}
