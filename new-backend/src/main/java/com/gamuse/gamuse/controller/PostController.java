package com.gamuse.gamuse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamuse.gamuse.helpers.ServerMessage;
import com.gamuse.gamuse.model.Post;
import com.gamuse.gamuse.service.PostService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/posts")
    private ResponseEntity<List<Post>> getAllPosts() {
        return new ResponseEntity<List<Post>>(postService.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    private ResponseEntity<Post> getSinglePost(@PathVariable("postId") int postId) {
        return new ResponseEntity<Post>(postService.getSinglePost(postId), HttpStatus.OK);
    }

    @PostMapping("/posts")
    private ResponseEntity<ServerMessage> createPost(@RequestBody Post post) {
        postService.savePost(post);
        return new ResponseEntity<ServerMessage>(new ServerMessage("Post created!", true), HttpStatus.CREATED);
    }

    @DeleteMapping("/posts/{postId}")
    private ResponseEntity<ServerMessage> deletePost(@PathVariable("postId") int postId) {
        postService.deletePost(postId);
        return new ResponseEntity<ServerMessage>(new ServerMessage("Post " + postId + " deleted!", true),
                HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    private ResponseEntity<ServerMessage> editPost(@PathVariable("postId") int postId, @RequestBody Post editedPost) {
        postService.editPost(postId, editedPost);
        return new ResponseEntity<ServerMessage>(new ServerMessage("Post edited and saved!", true),
                HttpStatus.OK);
    }
}