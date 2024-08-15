package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.validation.OnCreate;
import ru.kata.spring.boot_security.demo.validation.OnUpdate;

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
    public String addNewUser(@Validated(OnCreate.class) @ModelAttribute("newUser") User newUser,
                             BindingResult bindingResult,
                             @RequestParam(value = "select_role", required = false) String[] roles,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Пожалуйста, исправьте ошибки.");
            return "redirect:/admin/users/addUser";
        }

        if (roles == null || roles.length == 0) {
            redirectAttributes.addFlashAttribute("error", "Пожалуйста, выберите хотя бы одну роль.");
            return "redirect:/admin/users/addUser";
        }

        userService.saveUser(newUser, roles);
        redirectAttributes.addFlashAttribute("success", "Пользователь успешно добавлен!");
        return "redirect:/admin";
    }

    @GetMapping("/users/edit/{id}")
    public String updateUserById(@PathVariable("id") int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("role", roleService.getAllRoles());
        return "edit";
    }

    @PostMapping("/users/edit/{id}")
    public String updateUser(@Validated(OnUpdate.class) @ModelAttribute("user") User user,
                             BindingResult bindingResult,
                             @RequestParam(value = "select_role", required = false) String[] roles,
                             RedirectAttributes redirectAttributes) {
        System.out.println(user.toString());
        if (bindingResult.hasErrors()) {
            return "edit";
        }

//        if (password != null && !password.isEmpty()) {
//            user.setPassword(password);
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//        }

        if (roles == null || roles.length == 0) {
            redirectAttributes.addFlashAttribute("error", "Пожалуйста, выберите хотя бы одну роль.");
            return "redirect:/admin/users/edit/" + user.getId(); // Возвращаем обратно на страницу редактирования
        }

        userService.updateUser(user, roles);
        redirectAttributes.addFlashAttribute("success", "Пользователь успешно обновлен!");
        return "redirect:/admin";
    }

    @PostMapping("/users/delete")
    public String deleteUser(Model model, @RequestParam int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}