package com.taskflow.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUnit implements AutoCloseable {

    private final EntityManagerFactory emf;

    public JpaUnit() {
        this.emf = Persistence.createEntityManagerFactory("taskflowPU");
    }

    public EntityManager createEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}