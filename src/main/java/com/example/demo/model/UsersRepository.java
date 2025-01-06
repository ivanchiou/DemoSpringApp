package com.example.demo.model;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UsersRepository {

    private final JdbcTemplate jdbcTemplate;
    
    public UsersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<UserDetailsDto> findUserByUsername(String username) {
        String query = "SELECT username, password, enabled FROM users WHERE username = ?";
        
        return jdbcTemplate.query(query, ps -> ps.setString(1, username), rs -> {
            if (rs.next()) {
                return Optional.of(new UserDetailsDto(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBoolean("enabled")
                ));
            } else {
                return Optional.empty();
            }
        });
    }

    public boolean updatePassword(String username, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        int rowsAffected = jdbcTemplate.update(query, newPassword, username);
        return rowsAffected > 0;
    }
}
