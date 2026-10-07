package com.portifolioproject.service;

import com.portifolioproject.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PortifolioService {

    private Map<String, User> users = new HashMap<>();


    // Add User
    public void addUser(User user) {

        users.put(user.getUserid(), user);
    }


    // Check whether user exists
    public boolean userExists(String userid) {

        return users.containsKey(userid);
    }


    // Get User by ID
    public User getUser(String userid) {

        return users.get(userid);
    }


    // Get all users
    public Collection<User> getAllUsers() {

        return users.values();
    }
    public void loadUsers(Collection<User> loadedUsers) {

        for (User user : loadedUsers) {

            users.put(user.getUserid(), user);
        }
    }
}