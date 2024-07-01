package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.LoginDto;
import ru.kata.spring.boot_security.demo.service.AuthenticationService;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    private final AuthenticationService authenticationService;

    // Конструктор для внедрения сервиса
    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        try {
            // Обработка входа в систему
            String htmlContent = "<!DOCTYPE html>" +
                    "<html lang=\"en\">" +
                    "<head>" +
                    "<meta charset=\"UTF-8\">" +
                    "<title>Login</title>" +
                    "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css\" rel=\"stylesheet\">" +
                    "</head>" +
                    "<body>" +
                    "<div class=\"container\">" +
                    "<form class=\"form-signin\">" +
                    "<h2 class=\"form-signin-heading\">Please sign in</h2>" +
                    "<label for=\"inputEmail\" class=\"sr-only\">Email address</label>" +
                    "<input type=\"email\" id=\"inputEmail\" class=\"form-control\" placeholder=\"Email address\" required autofocus>" +
                    "<label for=\"inputPassword\" class=\"sr-only\">Password</label>" +
                    "<input type=\"password\" id=\"inputPassword\" class=\"form-control\" placeholder=\"Password\" required>" +
                    "<button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">Sign in</button>" +
                    "</form>" +
                    "</div>" +
                    "</body>" +
                    "</html>";// ваш HTML-контент здесь
            return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(htmlContent);
//            String token = authenticationService.authenticate(loginDto.getEmail(), loginDto.getPassword());
//            if (token != null) {
//                // В случае успеха возвращаем токен
//                return ResponseEntity.ok(token);
//            } else {
//                // Если аутентификация не удалась, возвращаем статус 401
//                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Неверные учетные данные");
//            }
        } catch (Exception e) {
            // В случае других ошибок возвращаем статус 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Внутренняя ошибка сервера");
        }
    }
}