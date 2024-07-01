package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.UserDTO;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImp {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<User> index() {
        return userRepository.findAll();
    }

    public ResponseEntity<UserDTO> save(User user, List<Long> selectedRoles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> roles = roleRepository.findAllById(selectedRoles);
        user.setRoles(new HashSet<>(roles));
        User savedUser = userRepository.save(user);

        UserDTO userDTO = convertToDTO(savedUser);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<UserDTO> update(User user, Long id, List<Long> selectedRoles) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        existingUser.setUsername(user.getUsername());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        existingUser.setRoles(new HashSet<>(roleRepository.findAllById(selectedRoles)));
        User updatedUser = userRepository.save(existingUser);

        UserDTO userDTO = convertToDTO(updatedUser);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // Метод для конвертации User в UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setLastname(user.getLastname());
        // ... заполнение других полей
        return dto;
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User findById(Long id) {
        return userRepository.getById(id);
    }

    public Optional<User> findByUsername(String name) {
        Optional<User> user = userRepository.findByUsername(name);
        return user;
    }
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }
}