package com.taskflow.config;

import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.process.internal.RequestScoped;

import com.taskflow.repository.UserRepository;
import com.taskflow.service.UserService;

import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;

public class DependencyBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(new JpaUnit()).to(JpaUnit.class);
        bindFactory(EntityManagerFactoryProvider.class)
                .to(EntityManager.class)
                .in(RequestScoped.class);

        bindAsContract(UserService.class).in(Singleton.class);

        bindAsContract(UserRepository.class).in(Singleton.class);
    }
}

