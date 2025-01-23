package com.example.demo.model;

import lombok.Data;

@Data
public class PostDTO {
    private String content;
    private String author;

    public PostDTO(String content, String author) {
        this.content = content;
        this.author = author;
    }
}
