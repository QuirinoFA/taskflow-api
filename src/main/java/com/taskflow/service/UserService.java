package com.taskflow.service;

import java.util.List;

import com.taskflow.common.exceptions.ValidationException;
import com.taskflow.controller.user.dto.PrintUserDto;
import com.taskflow.controller.user.dto.UpdateUserDto;
import com.taskflow.model.User;
import com.taskflow.repositories.user.UserRepository;

import jakarta.inject.Inject;

public class UserService {

        private static final int NAME_MIN_LENGTH = 2;
    private static final int NAME_MAX_LENGTH = 100;
    private static final int AGE_MIN = 0;
    private static final int AGE_MAX = 150;
    private static final int GENDER_MAX_LENGTH = 30;

    private final UserRepository userRepository;

    @Inject
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void createUser(PrintUserDto createUserDto) {
        validate(createUserDto);

        User user = User.builder()
                .name(createUserDto.getName().trim())
                .age(createUserDto.getAge())
                .gender(createUserDto.getGender() == null ? null : createUserDto.getGender().trim())
                .build();
            userRepository.createUser(user);
    }

    public User updateUser(Long id, UpdateUserDto updateUserDto) {
        if (updateUserDto == null || id == null) {
            throw new ValidationException("Request body and User ID are required");
        }

        User user = userRepository.findUserById(id);
        
        if (user == null) {
            throw new ValidationException("User with Id not found");
        }

        user.setName(updateUserDto.getName() != null ? updateUserDto.getName().trim() : user.getName());
        user.setAge(updateUserDto.getAge() != null ? updateUserDto.getAge() : user.getAge());
        user.setGender(updateUserDto.getGender() != null ? updateUserDto.getGender().trim() : user.getGender());
        userRepository.updateUser(id, user);
        return user;
    }

    public User findUserById(Long id) {
        if (id == null) {
            throw new ValidationException("User ID is required");
        }
        else {
            User user = userRepository.findUserById(id);
                if (user == null) {
                    throw new ValidationException("User with Id not found");
                }
            return user;
        }
    }

    public void deleteUser(Long id) {
        if (id == null) {
            throw new ValidationException("User ID is required");
        }
        else {
            userRepository.deleteUser(id);
        }
    }

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    private void validate(PrintUserDto dto) {
        if (dto == null) {
            throw new ValidationException("Request body is required");
        }

        if (dto.getName() == null) {
            throw new ValidationException("Name is required");
        }
        
        int lengthName = dto.getName().trim().length();
        if (lengthName < NAME_MIN_LENGTH || lengthName > NAME_MAX_LENGTH) {
            throw new ValidationException(
            "Name must be between " + NAME_MIN_LENGTH + " and " + NAME_MAX_LENGTH + " characters");
        }

        if (dto.getAge() < AGE_MIN || dto.getAge() > AGE_MAX) {
            throw new ValidationException("Age must be between " + AGE_MIN + " and " + AGE_MAX);
        }

        int lengthGender = dto.getGender().trim().length();
        if (lengthGender < NAME_MIN_LENGTH || lengthGender > GENDER_MAX_LENGTH) {
            throw new ValidationException("Gender must be between " + NAME_MIN_LENGTH + " and " + GENDER_MAX_LENGTH + " characters");
        }

    }
}

