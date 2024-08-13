package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


@Component
public class CreateTable {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired

    public CreateTable(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    Role adminRole = new Role("ROLE_ADMIN");
    Role userRole = new Role("ROLE_USER");


    private final User admin = new User("tommy@mail.ru", "J", "MIB", "admin");
    private final User user = new User("will@mail.ru", "K", "MIB", "user");
    String[] roleAdmin = new String[]{"ROLE_ADMIN", "ROLE_USER"};
    String[] roleUser = new String[]{"ROLE_USER"};

    public void create() {
        roleService.saveRole(adminRole);
        roleService.saveRole(userRole);
        userService.saveUser(admin, roleAdmin);
        userService.saveUser(user, roleUser);
    }

}