package com.taskflow.controller.user;

import com.taskflow.controller.user.dto.PrintUserDto;
import com.taskflow.model.User;
import com.taskflow.service.UserService;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/user")
public class UserController{

    private final UserService userService;

    @Inject
    public UserController(UserService injectedUserService) {
        this.userService = injectedUserService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response printUser(PrintUserDto printUserDto) {
        userService.printUser(printUserDto);
        return Response.noContent().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(PrintUserDto createUserDto) {
        userService.createUser(createUserDto);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(PrintUserDto updateUserDto) {
        User updatedUser =userService.updateUser(updateUserDto);
        return Response.ok(updatedUser).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUser(Long id) {
        userService.deleteUser(id);
        Response.ok().build();
    }
}
