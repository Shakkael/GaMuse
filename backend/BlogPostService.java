// plik BlogPostService.java

import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogPostService {
    
    Page<BlogPost> getAllBlogPosts(int page);
    
    BlogPost getBlogPostById(Long id);
    
    void saveBlogPost(BlogPost blogPost);
    
    List<BlogPost> searchBlogPosts(String keyword);
}
