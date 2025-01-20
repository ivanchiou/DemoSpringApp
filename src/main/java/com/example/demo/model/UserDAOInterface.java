package com.example.demo.model;
import java.util.List;

public interface UserDAOInterface {
    void save(Users user);
    Users findById(int id);
    List<Users> findAll();
    void update(Users user);
    void delete(int id);

    // 新增方法：根據用戶名查找用戶
    Users findUserByUsername(String name);

    // 新增更新密碼方法
    boolean updatePassword(String name, String password);
}