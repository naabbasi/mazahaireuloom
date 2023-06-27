package edu.learn.mazahaireuloom.repos;

import edu.learn.mazahaireuloom.entities.Library;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LibraryRepo extends GenericRepo<Library, String> {

    Mono<Library> findByLibraryName(String libraryName);
    //Flux<Library> findByLibraryNameAndShelfNumber(String libraryName, String shelfNumber);

    default Flux<Library> searchLibrary(ReactiveMongoTemplate reactiveMongoTemplate, String searchString) {
        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("searchString").regex(searchString, "i"),
                Criteria.where("libraryNumber").regex(searchString, "i")
        );

        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(10);
        query.addCriteria(criteria);
        return reactiveMongoTemplate.find(query, Library.class);
    }
}
