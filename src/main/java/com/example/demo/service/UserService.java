package com.example.demo.service;

import com.example.demo.model.UserDTO;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.model.UserDAOInterface;

@Service
public class UserService {
    @Autowired
    private UserDAOInterface userDAO;

    public UserDTO getUserByID(int id) {
        User user = userDAO.findById(id);
        if (user == null) {
            return null;
        }
        UserDTO userDto = new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.isEnabled());
        return userDto;
    }
    
    public boolean findUserByUsername(String username) {
        return userDAO.findUserByUsername(username) != null;
    }

    public boolean updatePassword(String username, String password) {
        return userDAO.updatePassword(username, password);
    }
}
