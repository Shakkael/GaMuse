package com.gamuse.gamuse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gamuse.gamuse.model.User;
import com.gamuse.gamuse.service.UserService;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    // login

    @GetMapping("/user/test")
    private String test() {
        return "Hello test";
    }

    // register
    @PostMapping("/user/register")
    private void register(@RequestBody User user) {
        User newUser = new User();

        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setUsername(user.getUsername());

        userService.createUser(newUser);
    }

    // getUsers
    @GetMapping("/user")
    private List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    // changeEmail

    // changePassword
}