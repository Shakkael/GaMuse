package com.gamuse.gamuse.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gamuse.gamuse.model.Comment;
import com.gamuse.gamuse.repository.CommentRepository;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    public void createComment(Comment comment) {
        commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<>();
        commentRepository.findAll().forEach(comment -> comments.add(comment));
        return comments;
    }

    public List<Comment> getCommentsForPost(int postId) {
        List<Comment> comments = new ArrayList<Comment>();

        commentRepository.findAll().forEach(comment -> {
            if (comment.getPostId() == postId) {
                comments.add(comment);
            }
        });

        return comments;
    }

    public Comment getSingleComment(int commentId) {
        return commentRepository.findById(commentId).get();
    }

    public List<Comment> getUserComments(int userId) {
        List<Comment> comments = new ArrayList<Comment>();

        commentRepository.findAll().forEach(comment -> {
            if (comment.getUserId() == userId) {
                comments.add(comment);
            }
        });

        return comments;
    }

    public void editComment(int commentId,Comment comment){
        Comment oldComment = commentRepository.findById(commentId).get();
        String content = "";

        if(comment.getContent() == ""){
            content = oldComment.getContent();
        } else {
            content = comment.getContent();
        }

        oldComment.setContent(content);

        commentRepository.save(oldComment);
    }

    public void deleteComment(int postId) {
        commentRepository.deleteById(postId);
    }
}