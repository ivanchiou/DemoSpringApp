package com.example.demo.model;
import java.io.Serializable;

import lombok.Data;

@Data
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;
    private boolean enabled;

    public UserDTO (int id, String username, String password, boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }
}
