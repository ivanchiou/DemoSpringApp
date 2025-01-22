package com.example.demo.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
    // 如果需要自定義查詢，可以在這裡添加
    Post findByContent(String content);
}
