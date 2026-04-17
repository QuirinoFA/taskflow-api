package com.taskflow.service;

import com.taskflow.controller.user.dto.PrintUserDto;

public class UserService {
    public void printUser(PrintUserDto printUserDto) {
        System.out.println(printUserDto.getName());
        System.out.println(printUserDto.getAge());
        System.out.println(printUserDto.getGender());
    }
}
