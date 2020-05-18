package ru.mycreation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mycreation.entities.Role;
import ru.mycreation.entities.User;
import ru.mycreation.service.RoleService;
import ru.mycreation.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) { this.userService = userService; }

    @Autowired
    public void setRoleService(RoleService roleService) { this.roleService = roleService; }

    @GetMapping("/users")
    public String getUsers(Model model, @RequestParam(required = false) String login){
        List<User> users = userService.findAll();
        User user = userService.findByLogin(login);
        List<Role> roles = roleService.findAll();
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.delete(id);
        return "redirect:/users";
    }

    @PostMapping("/users")
    public String editUser(@ModelAttribute(name = "user") @Valid User user,
                           BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            List<User> users = userService.findAll();
            List<Role> roles = roleService.findAll();
            model.addAttribute("user", user);
            model.addAttribute("users", users);
            model.addAttribute("roles", roles);
            return "users";
        } else {
            userService.save(user);
        }
        return "redirect:/users";
    }
}
