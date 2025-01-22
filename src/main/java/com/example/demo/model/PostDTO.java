package com.example.demo.model;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;

@Data
public class PostDTO {
    private String content;
    private String author; 

    public PostDTO (String content, String author) {
        this.content = content;
        this.author = author;
    }
}
