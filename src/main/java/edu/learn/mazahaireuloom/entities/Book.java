package edu.learn.mazahaireuloom.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "books")
@CompoundIndexes(
    @CompoundIndex(unique = true, name = "name_author_publisher", def ="{'bookName': 1, 'author.authorName': 1, 'publisher.publisherName': 1}")
)
public class Book {
    @Id
    private String bookId = UUID.randomUUID().toString();

    @NotNull
    @TextIndexed(weight = 5)
    @Indexed(unique = true)
    private String bookName;

    @NotNull
    @Field( value = "author")
    private BookAuthor bookAuthor;

    @NotNull
    @Field( value = "publisher")
    private BookPublisher bookPublisher;

    @NotNull
    @Field( value = "tags")
    private List<Tag> tags;

    private String bookQuantities;

    private String bookVolumes;

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        String jsonToString = "";
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Book toString: ", e);
        }

        return jsonToString;
    }
}
