package edu.learn.mazahaireuloom.entities;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.TextScore;

@Slf4j
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"password"})
@Document(collection = "user")
public class User {
    @Id
    private String userId;

    @NotBlank
    @TextIndexed(weight = 2)
    @Indexed(unique = true)
    private String username;
    private String password;
    @TextScore
    private Float textScore = 1.0f;

    public User(User user) {
        this(user.getUsername(), user.getPassword());
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
