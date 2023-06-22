package com.gamuse.gamuse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamuse.gamuse.model.User;
import com.gamuse.gamuse.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void createUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }

    public User getSingleUser(int userId) {
        return userRepository.findById(userId).get();
    }

    public void updateUser(int userId, User updatedUser) {
        String email, password, username = "";
        User oldUser = userRepository.findById(userId).get();

        if (updatedUser.getEmail() == "") {
            email = oldUser.getEmail();
        } else {
            email = updatedUser.getEmail();
        }
        if (updatedUser.getUsername() == "") {
            username = oldUser.getUsername();
        } else {
            username = updatedUser.getUsername();
        }
        if (updatedUser.getPassword() == "") {
            password = oldUser.getPassword();
        } else {
            password = updatedUser.getPassword();
        }

        oldUser.setEmail(email);
        oldUser.setUsername(username);
        oldUser.setPassword(password);

        userRepository.save(oldUser);
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}
