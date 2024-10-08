package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleDao {
    void saveRole(Role role);
    List<Role> getAllRoles();
    Role findByRole(String role); // Перенесенный метод
}