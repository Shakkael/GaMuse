// plik CommentService.java

import java.util.List;

public interface CommentService {
    
    List<Comment> getCommentsByBlogPostId(Long blogPostId);
    
    void saveComment(Comment comment);
}
