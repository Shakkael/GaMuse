package com.gamuse.gamuse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gamuse.gamuse.model.Post;
import com.gamuse.gamuse.service.PostService;

@RestController("/")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/post")
    private List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/post/{postId}")
    private Post getSinglePost(@PathVariable("postId") int postId) {
        return postService.getSinglePost(postId);
    }

    @PostMapping("/post")
    private Post createPost(@RequestBody Post post) {
        postService.savePost(post);
        return post;
    }

    @DeleteMapping("/post/{postId}")
    private int deletePost(@PathVariable("postId") int postId) {
        postService.deletePost(postId);
        return postId;
    }

    @PutMapping("/post/{postId}")
    private void editPost(@PathVariable("postId") int postId, @RequestBody Post editedPost) {
        Post post = postService.getSinglePost(postId);
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

        postService.savePost(post);
    }
}