package edu.learn.mazahaireuloom.repos;

import edu.learn.mazahaireuloom.entities.Shelf;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface ShelfRepo extends GenericRepo<Shelf, String> {

    Mono<Shelf> findByShelfName(String shelfName);
    Mono<Shelf> findByShelfNumber(String shelfNumber);

    Flux<Shelf> findAllShelfByLibraryId(String libraryId);

    Mono<Shelf> findShelfByLibraryIdAndShelfId(String libraryId, String shelfId);

    default Flux<List> searchLibrary(ReactiveMongoTemplate reactiveMongoTemplate, String shelfName, String shelfNumber) {
        Criteria criteria = Criteria.where("shelfName").regex(shelfName, "i");

        if(shelfNumber != null){
            criteria.and("shelfNumber").regex("", "i");
        }

        org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
        query.limit(10);
        query.addCriteria(criteria);
        return reactiveMongoTemplate.find(query, List.class);
    }
}
