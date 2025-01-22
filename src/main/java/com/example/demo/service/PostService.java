package com.example.demo.service;

import com.example.demo.model.Post;
import com.example.demo.model.PostDTO;
import com.example.demo.model.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Post createPost(PostDTO postDto) {
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setAuthor(postDto.getAuthor());
        return postRepository.save(post);
    }

    // 根據 ID 尋找
    public Optional<Post> getPostById(String id) {
        return postRepository.findById(id);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostByContent(String content) {
        return postRepository.findByContent(content);
    }

    public List<Post> findPostsByKeyword(String keyword) {
        Query query = new Query();
        query.addCriteria(Criteria.where("content").regex(keyword, "i")); // 不區分大小寫
        return mongoTemplate.find(query, Post.class, "posts");
    }

    public void deletePostById(String id) {
        postRepository.deleteById(id);
    }
}
