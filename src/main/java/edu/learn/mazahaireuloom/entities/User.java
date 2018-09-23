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
@ToString(exclude = "password")
@AllArgsConstructor
@EqualsAndHashCode
@Document(collection = "users")
public class User {
    @Id
    private String userId = UUID.randomUUID().toString();

    @NotNull
    @TextIndexed(weight = 2)
    @Indexed(unique = true)
    private String username;
    private String password;
    @TextScore
    private Float textScore = 1.0f;

    public User(@NotNull String username, String password) {
        this.username = username;
        this.password = password;
    }
}
