package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "users")
@Data
public class Users {
    @Id
    private String username;

    private String password;
    private boolean enabled;  
}
