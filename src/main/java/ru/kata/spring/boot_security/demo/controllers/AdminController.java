package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.reposiroty.RoleRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;

    }

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping()
    public String users(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/users/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("availableRoles", roleRepository.findAll());
        return "new";
    }

    @PostMapping("/users/addUser")
    public String createUser(@ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             @RequestParam(value = "roles") String[] roles,
                             @ModelAttribute("password") String pass) {
        userService.add(user, pass, roles);

        return "redirect:/admin";
    }

    @GetMapping("/users/edit")
    public String editUser(Model model, @RequestParam Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roles", userService.getUsers());
        model.addAttribute("availableRoles", roleRepository.findAll());
        return "edit";

    }

    @PostMapping("/users/edit")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") String[] roles,
                             @ModelAttribute("pass") String pass) {
        System.out.println(user.toString());
        userService.update(user, roles);
        return "redirect:/admin";
    }

    @PostMapping("/users/delete")
    public String deleteUser(Model model, @RequestParam Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}