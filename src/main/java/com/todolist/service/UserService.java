package com.todolist.service;

import com.todolist.unit.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUserById(long id);

    User createUser(User user);

    void updateUser(long id, User user);

    void deleteUser(long id);
}
