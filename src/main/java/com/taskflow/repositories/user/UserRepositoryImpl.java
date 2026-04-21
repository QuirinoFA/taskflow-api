package com.taskflow.repositories.user;

import com.taskflow.model.User;
import com.taskflow.repositories.exceptions.UserPersistenceException;

import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;

public class UserRepositoryImpl implements UserRepository {

    private final Provider<EntityManager> em;

    @Inject
    public UserRepositoryImpl(Provider<EntityManager> em) {
        this.em = em;
    }

    @Override
    public void createUser(User user) {
        EntityManager entityManager = em.get();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new UserPersistenceException("Error creating user", e);
        }
    }

    @Override
    public void updateUser(User user) {
        EntityManager entityManager = em.get();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(user);
            transaction.commit();
        } catch (PersistenceException e ) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new UserPersistenceException("Error updating user", e);
        }
    }
    @Override
    public User findUserById(Long id) {
        EntityManager entityManager = em.get();
        try {
            return entityManager.find(User.class, id);
        } catch (PersistenceException e) {
            throw new UserPersistenceException ("Error finding user with id: " + id, e);
        }
    }

    @Override
    public void deleteUser(Long id) {
        EntityManager entityManager = em.get();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            User user = entityManager.find(User.class, id);
            if (user != null) {
                entityManager.remove(user);
                transaction.commit();
            }
            else {
                transaction.rollback();
                throw new UserPersistenceException("User with id: " + id + " not found", null);
            }
        }
        catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new UserPersistenceException("Error deleting user with id: " + id, e);
        }
    }
}