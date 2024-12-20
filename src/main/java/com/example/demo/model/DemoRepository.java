package com.example.demo.model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoRepository extends JpaRepository<DemoModel, Long> {
    DemoModel findByName(String name);
}