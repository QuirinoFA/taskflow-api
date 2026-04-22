package com.taskflow.repositories.user;

import com.taskflow.model.User;

import java.util.List;

public interface UserRepository {
    void createUser(User user);
    void updateUser(Long id, User user);
    User findUserById(Long id);
    void deleteUser(Long id);
    List<User> findAllUsers();
}