package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void saveUser(User user, String[] roles);

    User getUserById(int id);

    void updateUser(User user, String[] roles);

    void deleteUserById(int id);

}