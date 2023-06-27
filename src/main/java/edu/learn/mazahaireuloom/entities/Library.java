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
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "library")
public class Library {
    @Id
    private String libraryId = UUID.randomUUID().toString();

    @Indexed(unique = true)
    @NotBlank
    @Field("libraryName")
    private String libraryName;

    @NotBlank
    @Field("libraryNumber")
    private String libraryNumber;

    @Field( value = "shelves")
    private List<Shelf> shelves;

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        String jsonToString = "";
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Shelf toString: ", e);
        }

        return jsonToString;
    }
}
