package ru.kata.spring.boot_security.demo.model;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String email;
    private String password;

    // Геттеры и сеттеры

}
