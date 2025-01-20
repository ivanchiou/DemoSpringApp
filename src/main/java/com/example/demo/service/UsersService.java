package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserDAOInterface;
import com.example.demo.model.Users;
import com.example.demo.model.UserDetailsDto;

@Service
public class UsersService {
    @Autowired
    private UserDAOInterface userDao;

    public UsersService(UserDAOInterface userDao) {
        this.userDao = userDao;
    }

    // 查找用戶名是否存在的方法
    public boolean findUserByUsername(String name) {
        return userDao.findUserByUsername(name) != null; // 如果返回非空，表示用戶存在
    }

    public boolean updatePassword(String name, String password) {
        // 使用 DAO 更新密碼
        return userDao.updatePassword(name, password);
    }

    public UserDetailsDto getUserDtoById(int id) {
        Users user = userDao.findById(id); // 從 DAO 獲取資料
        UserDetailsDto userDto = new UserDetailsDto(user.getName(), user.getPassword(), user.isEnabled());
        return userDto; // 返回 DTO
    }
}
