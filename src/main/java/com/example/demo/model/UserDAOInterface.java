package com.example.demo.model;

public interface UserDAOInterface {
    Users findById(int id);
    Users findUserByUsername(String username);
    boolean updatePassword(String username, String password);
}