package edu.learn.mazahaireuloom.repos;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
interface GenericRepo<T, ID> extends ReactiveMongoRepository<T, ID> {
}
