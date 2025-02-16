package com.example.demo.model;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> { 

    Optional<User> findById(int id);

    @EntityGraph(attributePaths = {"posts"})
    @Query("SELECT u FROM users u WHERE u.id = :id")
    Optional<User> findByIdWithPosts(@Param("id") int id);
}