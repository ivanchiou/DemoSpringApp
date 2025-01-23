package com.example.demo.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String>{ 
    Post findByContent(String content);
}
