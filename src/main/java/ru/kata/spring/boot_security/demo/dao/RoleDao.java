package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.entity.Role;

public interface RoleDao {
    void saveRole(Role role);

    Role getRole(String roleName);
}