package edu.learn.mazahaireuloom.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MongoConfiguration {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final MongoConverter mongoConverter;

    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {

        log.info("Mongo InitIndicesAfterStartup init");
        var init = System.currentTimeMillis();

        var mappingContext = this.mongoConverter.getMappingContext();

        if (mappingContext instanceof MongoMappingContext) {
            MongoMappingContext mongoMappingContext = (MongoMappingContext) mappingContext;
            for (MongoPersistentEntity<?> persistentEntity : mongoMappingContext.getPersistentEntities()) {
                var clazz = persistentEntity.getType();
                if (clazz.isAnnotationPresent(Document.class)) {
                    var resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);

                    var indexOps = this.reactiveMongoTemplate.indexOps(clazz);
                    resolver.resolveIndexFor(clazz).forEach(indexOps::ensureIndex);
                }
            }
        }

        log.info("Mongo InitIndicesAfterStartup take: {}", (System.currentTimeMillis() - init));
    }
}
