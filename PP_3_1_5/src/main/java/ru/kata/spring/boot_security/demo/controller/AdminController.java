package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImp userServiceImp;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserServiceImp userServiceImp, RoleService roleService) {
        this.userServiceImp = userServiceImp;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getUsers(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("usersList", userServiceImp.index());
        model.addAttribute("roles", roleService.findAll());
        return "users";
    }

    @GetMapping("/{id}")
    public String show(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userServiceImp.findById(id));
        return "show";
    }

    @GetMapping("/create")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "new";
    }

    @PostMapping("/new")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @RequestParam("selectedRoles") List<Long> selectedRoles) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        userServiceImp.save(user, selectedRoles);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam("id") Long id) {
        model.addAttribute("user", userServiceImp.findById(id));
        model.addAttribute("allRoles", roleService.findAll());
        return "edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("user") @Valid User user,BindingResult bindingResult,
                         @RequestParam("id") Long id, @RequestParam("selectedRoles") List<Long> selectedRoles) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userServiceImp.update(user, id, selectedRoles);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        userServiceImp.delete(id);
        return "redirect:/admin";
    }
}