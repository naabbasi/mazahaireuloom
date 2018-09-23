package edu.learn.mazahaireuloom.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "books")
public class Book {
    @Id
    private String bookId = UUID.randomUUID().toString();

    @NotNull
    @TextIndexed(weight = 2)
    @Indexed(unique = true)
    private String bookName;

    @NotNull
    @TextIndexed(weight = 2)
    @Indexed(unique = true)
    private String bookAuthor;

    @NotNull
    @TextIndexed(weight = 2)
    @Indexed(unique = true)
    private String bookPublisher;
}
