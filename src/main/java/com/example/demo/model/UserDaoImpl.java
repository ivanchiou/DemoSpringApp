package com.example.demo.model;
import com.example.demo.model.Users;
import com.example.demo.model.UserDAOInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDAOInterface {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Users user) {
        entityManager.persist(user);
    }

    @Override
    public Users findById(int id) {
        return entityManager.find(Users.class, id);
    }

    @Override
    public List<Users> findAll() {
        return entityManager.createQuery("SELECT u FROM users u", Users.class).getResultList();
    }

    @Override
    public void update(Users user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(int id) {
        Users user = findById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public Users findUserByUsername(String username) {
        List<Users> users = entityManager.createQuery("SELECT u FROM users u WHERE u.username = :username", Users.class)
                .setParameter("username", username)
                .getResultList();
        return users.isEmpty() ? null : users.get(0); // 如果查詢結果為空，返回 null
    }

    @Override
    @Transactional
    public boolean updatePassword(String username, String password) {
        int updatedCount = entityManager.createQuery("UPDATE users u SET u.password = :password WHERE u.username = :username")
                .setParameter("password", password)
                .setParameter("username", username)
                .executeUpdate();
        return updatedCount > 0;
    }
}
