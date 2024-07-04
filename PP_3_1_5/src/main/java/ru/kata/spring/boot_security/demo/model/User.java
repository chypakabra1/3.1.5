package ru.kata.spring.boot_security.demo.model;


import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotEmpty(message = "Имя не может быть пустым")
//    @Size(min = 2, max = 10, message = "Имя в пределах от 2 до 10 знаков")
//    @Pattern(regexp = "^[\\p{L}]+(?: [\\p{L}]+)*$", message = "Имя может содержать только буквы и пробелы")
    @Column(name = "username")
    private String username;

//    @NotEmpty(message = "Фамилия не может быть пустой")
//    @Size(min = 2, max = 15, message = "Фамилия в пределах от 2 до 15 знаков")
//    @Pattern(regexp = "^[\\p{L}]+(?: [\\p{L}]+)*$", message = "Фамилия может содержать только буквы и пробелы")
    @Column(name = "last_name")
    private String lastname;

//    @NotEmpty(message = "Почта не может быть пустой")
    @Email(message = "Почта должна быть валидная")
    @Column(name = "email")
    private String email;

    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @Cascade(org.hibernate.annotations.CascadeType.PERSIST)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set <Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getRolesFormatted() {
        return roles.stream()
                .map(role -> role.getName().replace("ROLE_", "")) // Удаление префикса
                .collect(Collectors.joining(" ")); // Соединение ролей через пробел
    }
}
