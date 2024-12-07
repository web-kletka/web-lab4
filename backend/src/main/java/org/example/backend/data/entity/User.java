package org.example.backend.data.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "users") // Имя таблицы в базе данных
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматическое увеличение ID
    private Long id;

    @Getter
    @Column(nullable = false, unique = true) // Поле не может быть пустым и должно быть уникальным
    private String login;

    @Getter
    @Column(nullable = false)
    private String password;

    @Getter
    @Column(nullable = false)
    private String salt;

    @Column(nullable = false)
    private Integer balance;

    public User() {}

    public User(String login, String password, String salt, Integer balance) {
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.balance = balance;
    }

    // Getters and Setters
}
