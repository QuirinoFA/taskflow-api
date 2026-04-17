package com.taskflow.config;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.taskflow.controller.user.UserController;

public class ApiResourceConfig extends ResourceConfig {
    public ApiResourceConfig() {
        register(JacksonFeature.class);
        register(UserController.class);
        register(new DependencyBinder());
    }
}
