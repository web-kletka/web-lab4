package org.example.backend.controlers.ResponseDAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.backend.data.entity.User;

@Setter
@Getter
@AllArgsConstructor
public class UserResponse {
    private boolean success;
    private String message;
    private User user;
}
