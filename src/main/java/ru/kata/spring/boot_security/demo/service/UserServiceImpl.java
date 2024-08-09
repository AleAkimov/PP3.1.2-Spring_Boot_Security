package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.Arrays;
import java.util.List;

import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private RoleService roleService;

    private UserDao userDao;

    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    @Transactional
    public void add(User user, String password, String[] roles) {
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRoles(Arrays.stream(roles).map(r -> roleService.getRole(r)).collect(Collectors.toList()));
        userDao.add(user);
    }

    @Override
    @Transactional
    public void update(User user, String[] roles) {
        user.setRoles(Arrays.stream(roles).map(r -> roleService.getRole(r)).collect(Collectors.toList()));
        userDao.update(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
}