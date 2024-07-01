package ru.kata.spring.boot_security.demo.model;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

public class UserDTO {


    @Setter
    @Getter
    private Long id;

    //    @NotEmpty(message = "Имя не может быть пустым")
//    @Size(min = 2, max = 10, message = "Имя в пределах от 2 до 10 знаков")
//    @Pattern(regexp = "^[\\p{L}]+(?: [\\p{L}]+)*$", message = "Имя может содержать только буквы и пробелы")
    @Setter
    @Getter
    private String username;

    //    @NotEmpty(message = "Фамилия не может быть пустой")
//    @Size(min = 2, max = 15, message = "Фамилия в пределах от 2 до 15 знаков")
//    @Pattern(regexp = "^[\\p{L}]+(?: [\\p{L}]+)*$", message = "Фамилия может содержать только буквы и пробелы")
    @Setter
    @Getter
    private String lastname;

    //    @NotEmpty(message = "Почта не может быть пустой")
    //@Email(message = "Почта должна быть валидная")
    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String password;

    @Setter
    @Getter
    private Set <String> roles;
}
