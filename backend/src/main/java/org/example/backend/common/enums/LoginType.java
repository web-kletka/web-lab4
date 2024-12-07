package org.example.backend.common.enums;

import lombok.Getter;

public enum LoginType {
    SUCCESS("", true),
    INVALID_LOGIN_OR_PASSWORD("Неправильный логин или пароль", false),;


    @Getter
    final String result;

    @Getter
    final boolean success;

    LoginType(String result, boolean success) {
        this.result = result;
        this.success = success;
    }

}
