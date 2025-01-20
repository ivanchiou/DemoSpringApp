package com.example.demo.service;

import com.example.demo.model.Users;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.model.UserDAOInterface;

@Service
public class UsersService {
    @Autowired
    private UserDAOInterface userDAO;

    public Users getUserByID(int id) {
        return userDAO.findById(id);
    }
    
    public boolean findUserByUsername(String username) {
        return userDAO.findUserByUsername(username) != null;
    }

    public boolean updatePassword(String username, String password) {
        return userDAO.updatePassword(username, password);
    }
}
