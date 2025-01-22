package com.example.demo.model;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "posts")
@Data
public class Post {
    @Id
    private String id;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt; // 自動填充創建時間

    @LastModifiedDate
    private LocalDateTime updatedAt; // 自動填充更新時間

    private String author;
}