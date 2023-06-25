package edu.learn.mazahaireuloom.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document(collection = "shelf")
public class Shelf {
    private String libraryId;
    private String shelfName;
    private String shelfNumber;

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
