package com.example.demo.model;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.persistence.NoResultException;

@Repository
public class UserDAOImpl implements UserDAOInterface {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Users findById(int id) {
        return entityManager.find(Users.class, id);
    }

    @Override
    public Users findUserByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM users u where u.username = :username", Users.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public boolean updatePassword(String username, String password) {
        int updateCount = entityManager.createQuery("UPDATE users u SET u.password = :password WHERE u.username = :username")
                .setParameter("password", password)
                .setParameter("username", username)
                .executeUpdate();
        return updateCount > 0;
    }
}