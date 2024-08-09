package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;


public interface UserService {
    List<User> getUsers();

    void add(User user, String password, String[] roles);

    void update(User user, String[] roles);

    void delete(Long id);

    User getUserById(Long id);

    User findUserByUsername(String username);

}