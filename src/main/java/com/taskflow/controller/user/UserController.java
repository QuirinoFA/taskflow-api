package com.taskflow.controller.user;

import com.taskflow.controller.user.dto.UpdateUserDto;
import com.taskflow.controller.user.dto.PrintUserDto;
import com.taskflow.model.User;
import com.taskflow.service.UserService;


import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
    public Response createUser(PrintUserDto printUserDto) {
        userService.createUser(printUserDto);
        return Response.ok().build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, UpdateUserDto updateUserDto) {
        User updatedUser = userService.updateUser(id, updateUserDto);
        return Response.ok(updatedUser).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserById(@PathParam("id") Long id) {
        User user = userService.findUserById(id);
        return Response.ok(user).build();
    }   

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteUser(Long id) {
        userService.deleteUser(id);
        Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllUsers() {
        return Response.ok(userService.findAllUsers()).build();
    }
}
