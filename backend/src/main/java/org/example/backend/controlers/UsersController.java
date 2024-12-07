package org.example.backend.controlers;

import org.example.backend.common.PasswordGeneration;
import org.example.backend.common.enums.LoginType;
import org.example.backend.common.enums.RegisterType;
import org.example.backend.controlers.ResponseDAO.UserResponse;
import org.example.backend.data.entity.User;
import org.example.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UsersController {

    private final UserService userService;


    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/reg")
    public UserResponse registration(@RequestBody Map<String, String> requestBody) {
        String login = requestBody.get("login");
        String salt = PasswordGeneration.generateSalt();
        String password = PasswordGeneration.generatePassword(requestBody.get("password"), salt);
        if (userService.getUserExists(login))
            return new UserResponse(
                    RegisterType.LOGIN_ALREADY_EXISTS.isSuccess(),
                    RegisterType.LOGIN_ALREADY_EXISTS.getResult(),
                    new User());
        User user = new User(login, password, salt, 0);
        userService.saveUser(user);
        return new UserResponse(
                RegisterType.SUCCESSFUL.isSuccess(),
                RegisterType.SUCCESSFUL.getResult(),
                user);
    }


    @PostMapping("/api/login")
    public UserResponse login(@RequestBody Map<String, String> requestBody) {

        String login = requestBody.get("login");
        String password = requestBody.get("password");

        User user = userService.getUserByUsername(login);
        if (Objects.isNull(user))
            return new UserResponse(
                    LoginType.INVALID_LOGIN_OR_PASSWORD.isSuccess(),
                    LoginType.INVALID_LOGIN_OR_PASSWORD.getResult(),
                    new User());
        if (PasswordGeneration.checkPassword(user.getPassword(), password, user.getSalt()))
            return new UserResponse(
                    LoginType.SUCCESS.isSuccess(),
                    LoginType.SUCCESS.getResult(),
                    user);
        return new UserResponse(
                LoginType.INVALID_LOGIN_OR_PASSWORD.isSuccess(),
                LoginType.INVALID_LOGIN_OR_PASSWORD.getResult(),
                new User());
    }

}
