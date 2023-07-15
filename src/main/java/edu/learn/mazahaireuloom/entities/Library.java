package edu.learn.mazahaireuloom.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "library")
public class Library {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank
    @Field("libraryName")
    private String libraryName;

    @NotBlank
    @Field("libraryNumber")
    private String libraryNumber;

    @Field( value = "shelves")
    @DocumentReference
    private List<Shelf> shelves;
}
