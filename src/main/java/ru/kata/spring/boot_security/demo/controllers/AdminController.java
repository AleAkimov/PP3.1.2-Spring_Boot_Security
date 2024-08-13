package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String getAllUsers(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("role", roleService.getAllRoles());
        return "users";
    }

    @GetMapping("/users/addUser")
    public String getAddNewUserPage(@AuthenticationPrincipal User user, Model model) {
        User newUser = new User();
        model.addAttribute("user", user);
        model.addAttribute("newUser", newUser);
        model.addAttribute("role", roleService.getAllRoles());
        return "new";
    }

    @PostMapping("/users/addUser")
    public String addNewUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "select_role", required = false) String[] roles) {
        userService.saveUser(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/users/edit/{id}")
    public String updateUserById(@ModelAttribute("user") User user, Model model,
                                 @RequestParam(value = "select_role", required = false) String[] roles) {
        model.addAttribute("user", user);
        model.addAttribute("role", roleService.getAllRoles());
        userService.updateUser(user, roles);
        return "redirect:/admin";

    }

    @PostMapping("/users/edit")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam(value = "roles") String[] roles,
                             @ModelAttribute("pass") String pass) {
        System.out.println(user.toString());
        userService.updateUser(user, roles);
        return "redirect:/admin";
    }

    @PostMapping("/users/delete")
    public String deleteUser(Model model, @RequestParam int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}