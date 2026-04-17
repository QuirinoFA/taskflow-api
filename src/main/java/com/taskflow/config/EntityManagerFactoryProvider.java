package com.taskflow.config;

import org.glassfish.jersey.internal.inject.DisposableSupplier;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

public class EntityManagerFactoryProvider implements DisposableSupplier<EntityManager> {

    private final JpaUnit jpaUnit;

    @Inject
    public EntityManagerFactoryProvider(JpaUnit jpaUnit) {
        this.jpaUnit = jpaUnit;
    }

    @Override
    public EntityManager get() {
        return jpaUnit.createEntityManager();
    }

    @Override
    public void dispose(EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}