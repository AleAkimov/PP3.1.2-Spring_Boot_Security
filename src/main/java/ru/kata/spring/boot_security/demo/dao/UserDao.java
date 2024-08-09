package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserDao {

    User findUserByUsername(String username);

    List<User> getUsers();

    void add(User user);

    void update(User user);

    void delete(Long id);

    User getUserById(Long id);
}