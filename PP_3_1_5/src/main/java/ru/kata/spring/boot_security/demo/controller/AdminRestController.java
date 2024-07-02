package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImp;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:8080") // Замените на URL вашего фронтенда
public class AdminRestController {

    private final UserServiceImp userServiceImp;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserServiceImp userServiceImp, RoleService roleService) {
        this.userServiceImp = userServiceImp;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userServiceImp.index());
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserById(@RequestParam Long id) {
        User user = userServiceImp.findById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/user/new")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user,
                                           BindingResult bindingResult,
                                           @RequestParam List<Long> selectedRoles) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        userServiceImp.save(user, selectedRoles);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/user/edit")
    public ResponseEntity<User> updateUser(@RequestParam Long id,
                                           @RequestBody @Valid User user,
                                           BindingResult bindingResult,
                                           @RequestParam List<Long> selectedRoles) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        userServiceImp.update(user, id, selectedRoles);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/user/delete")
    public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
        userServiceImp.delete(id);
        return ResponseEntity.ok().build();
    }
}