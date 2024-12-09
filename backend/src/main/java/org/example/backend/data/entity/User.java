package org.example.backend.data.entity;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
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

    @Getter
    @Column(nullable = false)
    private Integer balance;

    @Getter
    @Column(nullable = false)
    private Integer status;

    public User() {}

    public User(String login, String password, String salt, Integer balance, Integer status) {
        this.login = login;
        this.password = password;
        this.salt = salt;
        this.balance = balance;
        this.status = status;
    }

    // Getters and Setters
}
