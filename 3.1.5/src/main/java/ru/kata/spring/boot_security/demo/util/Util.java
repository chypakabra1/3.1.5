package ru.kata.spring.boot_security.demo.util;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleSerivce;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class Util {

    private final UserService userService;
    private final RoleSerivce roleSerivce;

    @Autowired
    public Util(UserService userService, RoleSerivce roleSerivce) {
        this.userService = userService;
        this.roleSerivce = roleSerivce;
    }

    @PostConstruct
    public void init() {
        Role roleUser = new Role(1, "ROLE_USER");
        Role roleAdmin = new Role(2, "ROLE_ADMIN");
        roleSerivce.addRole(roleUser);
        roleSerivce.addRole(roleAdmin);

        userService.saveUser(new User("admin", "100", "adminsis", 44, "1@mail.ru"),
                new HashSet<Role>(Set.of(roleAdmin, roleUser)));
        userService.saveUser(new User("user", "100", "usersis", 42, "2@mail.ru"),
                new HashSet<Role>(Set.of(roleUser)));
    }
}
