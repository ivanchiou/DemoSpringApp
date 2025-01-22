package com.example.demo.config;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.PostRepository;

@Configuration
public class MongoInitConfig {

    @Bean
    public ApplicationRunner init(PostRepository postRepository) {
        return args -> {
            System.out.println("MongoDB connection established!");

            System.out.print("MongoDB Post collection has " + postRepository.count() + " documents");
        };
    }
}
