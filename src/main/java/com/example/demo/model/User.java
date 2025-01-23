package com.example.demo.model;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import lombok.Data;

@Entity(name = "users")
@Table(name = "users", indexes = {
    @Index(name="idx_email", columnList = "email"),
    @Index(name="idx_age_salary", columnList = "age, salary")
})
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @UniqueElements
    @NotNull
    private String username;

    private String email;
    private String password;
    private int age;
    private int salary;
    private boolean enabled;
}
