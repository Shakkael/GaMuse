package com.gamuse.gamuse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamuse.gamuse.model.Post;
import com.gamuse.gamuse.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<Post>();
        postRepository.findAll().forEach(post -> posts.add(post));
        return posts;
    }

    public Post getSinglePost(int postId) {
        return postRepository.findById(postId).get();
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public void deletePost(int postId) {
        postRepository.deleteById(postId);
    }
}
