package edu.learn.mazahaireuloom.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Setter @Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "book")
@CompoundIndexes(
    @CompoundIndex(unique = true, name = "name_author_publisher", def ="{'bookName': 1, 'author.authorName': 1, 'publisher.publisherName': 1}")
)
public class Book {
    @Id
    private String bookId;

    @NotBlank
    private String bookSource;

    @NotBlank
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate bookPublishDate;

    @NotNull
    @Field( value = "tags")
    private List<Tag> tags;

    @NotNull
    private Integer bookQuantities;

    @NotNull
    private Integer bookVolumes;
}
