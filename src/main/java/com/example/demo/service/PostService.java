package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Post;
import com.example.demo.model.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(String content, String author) {
        Post post = new Post();
        post.setContent(content);
        post.setAuthor(author);
        return postRepository.save(post);
    }

    public Post getPostByContent(String content) {
        return this.postRepository.findByContent(content);
    }
}
