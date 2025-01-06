package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.UsersRepository;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    public boolean findUserByUsername(String name) {
        return this.usersRepository.findUserByUsername(name).isPresent();
    }
    public boolean updatePassword(String name, String password) {
        return this.usersRepository.updatePassword(name, password);
    }
}
