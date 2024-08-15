package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDaoImpl;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.annotation.PostConstruct;


@Component
public class CreateTable {

    private final UserService userService;
    private final RoleService roleService;
    private final RoleDaoImpl roleDaoImpl;
    private final UserDaoImpl userDaoImpl;

    @Autowired
    public CreateTable(UserService userService, RoleService roleService, RoleDaoImpl roleDaoImpl, UserDaoImpl userDaoImpl) {
        this.userService = userService;
        this.roleService = roleService;
        this.roleDaoImpl = roleDaoImpl;
        this.userDaoImpl = userDaoImpl;
    }

    Role adminRole = new Role("ROLE_ADMIN");
    Role userRole = new Role("ROLE_USER");

    private final User admin = new User("tommy@mail.ru", "J", "MIB", "admin");
    private final User user = new User("will@mail.ru", "K", "MIB", "admin");

    String[] roleAdmin = new String[]{"ROLE_ADMIN", "ROLE_USER"};
    String[] roleUser = new String[]{"ROLE_USER"};

    @PostConstruct
    public void create() {
        if (roleDaoImpl.findByRoleName(adminRole.getRole()) == null) {
            roleService.saveRole(adminRole);
        }
        if (roleDaoImpl.findByRoleName(userRole.getRole()) == null) {
            roleService.saveRole(userRole);
        }

        if (userDaoImpl.getUserByEmail(admin.getEmail()) == null) {
            userService.saveUser(admin, roleAdmin);
        }
        if (userDaoImpl.getUserByEmail(user.getEmail()) == null) {
            userService.saveUser(user, roleUser);
        }
    }
}