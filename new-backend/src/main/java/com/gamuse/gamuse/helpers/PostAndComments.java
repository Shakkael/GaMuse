package com.gamuse.gamuse.helpers;

import java.util.List;

import com.gamuse.gamuse.model.Comment;
import com.gamuse.gamuse.model.Post;

public class PostAndComments {
    private Post post;
    private List<Comment> comments;

    public PostAndComments(Post post, List<Comment> comments) {
        this.post = post;
        this.comments = comments;
    }
}
