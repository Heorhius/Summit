package com.kaptsiug.project.dao;

import com.kaptsiug.project.model.User;

import java.util.List;

public interface UserDao {
    User getUser(String login, String password);

    void addUser(User user);

    List<User> getAllUsers();
}

