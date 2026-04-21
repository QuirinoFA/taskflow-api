package com.taskflow.service;

import com.taskflow.common.exceptions.ValidationException;
import com.taskflow.controller.user.dto.PrintUserDto;
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


    public void printUser(PrintUserDto printUserDto) {
        System.out.println(printUserDto.getName());
        System.out.println(printUserDto.getAge());
        System.out.println(printUserDto.getGender());
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

    public User updateUser(PrintUserDto updateUserDto) {
        validate(updateUserDto);

        User user = userRepository.findUserById(updateUserDto.getId());
        if (user != null) {
            user.setName(updateUserDto.getName().trim());
            user.setAge(updateUserDto.getAge());
            user.setGender(updateUserDto.getGender());
            userRepository.updateUser(user);
            return user;
        }
        else {
            throw new ValidationException("User with Id not found");
        }
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

