package com.taskflow.repositories.user;

import com.taskflow.model.User;

public interface UserRepository {
    void createUser(User user);
    void updateUser(User user);
    User findUserById(Long id);
    void deleteUser(Long id);
}