package com.gamuse.gamuse.service;

import java.util.ArrayList;
import java.util.List;

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

    public void editPost(int postId, Post editedPost) {
        Post post = postRepository.findById(postId).get();

        String title = "";
        String content = "";

        if (editedPost.getTitle() == "") {
            title = post.getTitle();
        } else {
            title = editedPost.getTitle();
        }

        if (editedPost.getContent() == "") {
            content = post.getContent();
        } else {
            content = editedPost.getContent();
        }
        post.setTitle(title);
        post.setContent(content);

        postRepository.save(post);
    }
}
