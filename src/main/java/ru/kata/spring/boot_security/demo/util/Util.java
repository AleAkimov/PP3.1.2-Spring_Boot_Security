package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class Util {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public Util(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");
        roleService.saveRole(admin);
        roleService.saveRole(user);
        User userAdmin = new User("Агент J", "MIB", (byte) 80, "Tommy");
        User userUser = new User("Агент K", "MIB", (byte) 25, "Will");
        String[] rolesAdmin = {"ROLE_ADMIN", "ROLE_USER"};
        String[] rolesUser = {"ROLE_USER"};
        userService.add(userAdmin, "White", rolesAdmin);
        userService.add(userUser, "Black", rolesUser);
    }
}