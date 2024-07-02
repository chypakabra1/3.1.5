package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserDetailsDTO;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceConfig implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetailsDTO loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'", email)));

        return convertToDTO(user);
    }
    private UserDetailsDTO convertToDTO(User user) {
        UserDetailsDTO dto = new UserDetailsDTO();
        dto.setUsername(user.getUsername());
        dto.setAuthorities(mapRolesToAuthorities(user.getRoles()));
        dto.setEmail(user.getEmail());
        dto.setId(user.getId());
        // ... заполнение других полей
        return dto;
    }

    private Set<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toSet());
    }
}
