package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserServiceConfig userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserServiceConfig userDetailsService,
                                 PasswordEncoder passwordEncoder,
                                 JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public String authenticate(String email, String password) {
        // Проверяем, существует ли пользователь с таким именем пользователя
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        // Проверяем, совпадает ли предоставленный пароль с сохраненным
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            // Создаем токен аутентификации
            Authentication authentication = new UsernamePasswordAuthenticationToken(email, password, userDetails.getAuthorities());
            // Устанавливаем детали аутентификации в контекст безопасности
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Генерируем JWT токен
            return jwtTokenProvider.createToken(authentication);
        }
        return null;
    }
}
