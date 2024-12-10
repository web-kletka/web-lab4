package org.example.backend.controlers;

import org.example.backend.common.PasswordGeneration;
import org.example.backend.common.enums.LoginType;
import org.example.backend.common.enums.RegisterType;
import org.example.backend.controlers.ResponseDAO.CutDownUser;
import org.example.backend.controlers.ResponseDAO.CutDownUserResponse;
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
    public CutDownUserResponse registration(@RequestBody Map<String, String> requestBody) {
        String login = requestBody.get("login");
        Integer status = Objects.equals(requestBody.get("code_word"), "лучше хуем бить орехи чем учиться в политехе") ? 1: 0;
        String salt = PasswordGeneration.generateSalt();
        String password = PasswordGeneration.generatePassword(requestBody.get("password"), salt);
        if (userService.getUserExists(login))
            return new CutDownUserResponse(
                    RegisterType.LOGIN_ALREADY_EXISTS.isSuccess(),
                    RegisterType.LOGIN_ALREADY_EXISTS.getResult(),
                    CutDownUser.fromUser(null));
        User user = new User(login, password, salt, 0,status);
        userService.saveUser(user);
        return new CutDownUserResponse(
                RegisterType.SUCCESSFUL.isSuccess(),
                RegisterType.SUCCESSFUL.getResult(),
                CutDownUser.fromUser(user));
    }


    @PostMapping("/api/login")
    public CutDownUserResponse login(@RequestBody Map<String, String> requestBody) {

        String login = requestBody.get("login");
        String password = requestBody.get("password");

        User user = userService.getUserByUsername(login);
        if (Objects.isNull(user))
            return new CutDownUserResponse(
                    LoginType.INVALID_LOGIN_OR_PASSWORD.isSuccess(),
                    LoginType.INVALID_LOGIN_OR_PASSWORD.getResult(),
                    CutDownUser.fromUser(null));
        if (PasswordGeneration.checkPassword(user.getPassword(), password, user.getSalt()))
            return new CutDownUserResponse(
                    LoginType.SUCCESS.isSuccess(),
                    LoginType.SUCCESS.getResult(),
                    CutDownUser.fromUser(user));
        return new CutDownUserResponse(
                LoginType.INVALID_LOGIN_OR_PASSWORD.isSuccess(),
                LoginType.INVALID_LOGIN_OR_PASSWORD.getResult(),
                CutDownUser.fromUser(null));
    }

}
