package com.gamuse.gamuse.controller;

import java.util.List;
import java.util.Optional;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamuse.gamuse.helpers.Login;
import com.gamuse.gamuse.helpers.ServerMessage;
import com.gamuse.gamuse.model.User;
import com.gamuse.gamuse.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    // create user
    @PostMapping("/users/register")
    private ResponseEntity<ServerMessage> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return new ResponseEntity<ServerMessage>(new ServerMessage("User created!", true), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<ServerMessage>(new ServerMessage("Username or Email already taken", false),
                    HttpStatus.OK);
        }
    }

    // login
    @PostMapping("/users/login")
    private ResponseEntity loginUser(@RequestBody Login login) {
        try {
            User u = userService.findByEmail(login.getEmail());
            if (u.getPassword().equals(login.getPassword())) {
                return new ResponseEntity<User>(u, HttpStatus.OK);
            } else {
                return new ResponseEntity<ServerMessage>(new ServerMessage("Invalid Credentials", false),
                        HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<ServerMessage>(new ServerMessage("Invalid Credentials", false), HttpStatus.OK);
        }

    }

    // get all users
    @GetMapping("/users")
    private ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
    }

    // get single user
    @GetMapping("/users/{userId}")
    private ResponseEntity<User> getSingleUser(@PathVariable int userId) {
        return new ResponseEntity<User>(userService.getSingleUser(userId), HttpStatus.OK);
    }

    // change Username
    @PutMapping("/users/{userId}")
    private ResponseEntity<ServerMessage> changeUsername(@PathVariable int userId, @RequestBody User user) {
        try {
            userService.updateUser(userId, user);
            return new ResponseEntity<ServerMessage>(new ServerMessage("User changed!", true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<ServerMessage>(new ServerMessage("Some problem", false),
                    HttpStatus.CONFLICT);
        }
    }

    // delete user
    @DeleteMapping("/users/{userId}")
    private ResponseEntity<ServerMessage> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<ServerMessage>(new ServerMessage("User " + userId + " deleted!", true),
                HttpStatus.OK);
    }

}
