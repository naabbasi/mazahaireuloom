package edu.learn.mazahaireuloom.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Setter
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "shelf")
public class Shelf {
    @Id
    private String shelfId;
    @NotBlank
    private String shelfName;
    @NotBlank
    private String shelfNumber;
    @NonNull
    private String libraryId;

    public Shelf() {
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();

        String jsonToString = "";
        try {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error("Tag toString: ", e);
        }

        return jsonToString;
    }
}
