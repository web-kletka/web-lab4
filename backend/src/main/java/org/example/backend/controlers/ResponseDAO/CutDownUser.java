package org.example.backend.controlers.ResponseDAO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.backend.data.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CutDownUser {
    private String login;
    private Integer balance;
    private Integer status;

    public static CutDownUser fromUser(User user) {
        if (user == null) return new CutDownUser();
        return new CutDownUser(user.getLogin(), user.getBalance(), user.getStatus());
    }
    // Getters and Setters
}
