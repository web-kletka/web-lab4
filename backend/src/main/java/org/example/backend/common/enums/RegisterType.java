package org.example.backend.common.enums;

import lombok.Getter;

public enum RegisterType {
    SUCCESSFUL("Пользователь успешно зарегистрирован", true),
    LOGIN_ALREADY_EXISTS("Данный пользователь уже существует", false),
    INVALID_DB("Ошибка!", false),
    INVALID_REGISTRATION("Ошибка авторизации", false),;

    @Getter
    final String result;

    @Getter
    final boolean success;

    RegisterType(String result, boolean success) {
        this.result = result;
        this.success = success;
    }
}
