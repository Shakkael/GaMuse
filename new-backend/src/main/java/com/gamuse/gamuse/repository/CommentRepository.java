package com.gamuse.gamuse.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gamuse.gamuse.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
    public List<Comment> findAllCommentsByPostId(int postId);
}
