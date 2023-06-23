package com.gamuse.gamuse.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private int userId;

    @Column
    private int postId;

    @Column(length = 10000)
    private String content;

    @Column
    private LocalDateTime createdAt;

    public Comment() {
        this.createdAt = LocalDateTime.now();
    }

    // CommentId
    public int getCommentId() {
        return this.id;
    }

    public void setCommentId(int commentId) {
        this.id = commentId;
    }

    // UserId
    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // PostId
    public int getPostId() {
        return this.postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    // Content
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}