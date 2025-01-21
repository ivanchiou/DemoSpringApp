package com.example.demo.model;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private int id;
    private String password;
    private boolean enabled;

    public UserDTO (String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
}
