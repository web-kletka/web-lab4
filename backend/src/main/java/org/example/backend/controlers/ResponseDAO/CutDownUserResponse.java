package org.example.backend.controlers.ResponseDAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CutDownUserResponse {
    private boolean success;
    private String message;
    private CutDownUser user;
    private String token;

    public CutDownUserResponse(boolean success, String message, CutDownUser user) {
        this.success = success;
        this.message = message;
        this.user = user;
        this.token = "";
    }
}
