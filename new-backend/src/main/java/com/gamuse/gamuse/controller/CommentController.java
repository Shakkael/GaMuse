package com.gamuse.gamuse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamuse.gamuse.helpers.ServerMessage;
import com.gamuse.gamuse.model.Comment;
import com.gamuse.gamuse.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    CommentService commentService;

    // all comments
    @GetMapping("/comments")
    private ResponseEntity<List<Comment>> getAllComments() {
        return new ResponseEntity<List<Comment>>(commentService.getAllComments(), HttpStatus.OK);
    }

    // comments of post
    @GetMapping("/posts/{postId}/comments")
    private ResponseEntity<List<Comment>> getCommentsOfOnePost(@PathVariable int postId) {
        return new ResponseEntity<List<Comment>>(commentService.getCommentsForPost(postId), HttpStatus.OK);
    }

    // single comment
    @GetMapping("/comment/{commentId}")
    private ResponseEntity<Comment> getOneComment(@PathVariable int commentId) {
        return new ResponseEntity<Comment>(commentService.getSingleComment(commentId), HttpStatus.OK);
    }

    // user comments
    @GetMapping("/users/{userId}/comments")
    private ResponseEntity<List<Comment>> getAllCommentsOfOneUser(@PathVariable int userId) {
        return new ResponseEntity<List<Comment>>(commentService.getUserComments(userId), HttpStatus.OK);
    }

    /*
     * try {
     * Comment c = new Comment();
     * 
     * c.setUserId(userId);
     * c.setPostId(postId);
     * c.setContent(comment);
     * 
     * commentService.createComment(c);
     * return new ResponseEntity<>(c, HttpStatus.OK);
     * } catch (Exception e) {
     * return new ResponseEntity<>(comment, HttpStatus.OK);
     * }
     * 
     */
    // create comment
    @PostMapping("/comment")
    private ResponseEntity createComment(@RequestBody Comment comment) {
        commentService.createComment(comment);
        return new ResponseEntity<>(comment, HttpStatus.OK);

    }

    @PutMapping("/comment/{commentId}")
    private ResponseEntity<ServerMessage> editComment(@PathVariable int commentId, @RequestBody Comment comment) {
        commentService.editComment(commentId, comment);
        return new ResponseEntity<ServerMessage>(new ServerMessage("Comment created!", true), HttpStatus.OK);
    }
}