package com.example.demo.model;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity(name = "users")
@Data
public class Users {
    @Id
    private String username;

    @UniqueElements
    @NotNull
    private int id;

    private String password;
    private boolean enabled;  
}
