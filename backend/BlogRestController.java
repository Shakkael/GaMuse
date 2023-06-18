import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogRestController {

    @Autowired
    private BlogPostService blogPostService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MediaService mediaService;

    // API do pobierania wszystkich postów
    @GetMapping("/blogPosts")
    public ResponseEntity<List<BlogPost>> getAllBlogPosts(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<BlogPost> blogPosts = blogPostService.getAllBlogPosts(page);
        return ResponseEntity.ok(blogPosts.getContent());
    }

    // API do pobierania pojedynczego postu
    @GetMapping("/blogPosts/{id}")
    public ResponseEntity<BlogPost> getBlogPostById(@PathVariable("id") Long id) {
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        if (blogPost != null) {
            return ResponseEntity.ok(blogPost);
        }
        return ResponseEntity.notFound().build();
    }

    // API do tworzenia nowego postu
    @PostMapping("/blogPosts")
    public ResponseEntity<String> createBlogPost(@Valid @RequestBody BlogPost blogPost, BindingResult bindingResult,
                                                 @RequestParam("media") MultipartFile mediaFile) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid blog post data");
        }

        try {
            Media media = mediaService.saveMedia(mediaFile);
            blogPost.setMedia(media);
            blogPostService.saveBlogPost(blogPost);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save media file");
        }
    }

    // API do pobierania komentarzy dla danego postu
    @GetMapping("/blogPosts/{id}/comments")
    public ResponseEntity<List<Comment>> getCommentsByBlogPostId(@PathVariable("id") Long blogPostId) {
        List<Comment> comments = commentService.getCommentsByBlogPostId(blogPostId);
        return ResponseEntity.ok(comments);
    }

    // API do dodawania komentarza dla danego postu
    @PostMapping("/blogPosts/{id}/comments")
    public ResponseEntity<String> addComment(@PathVariable("id") Long blogPostId, @Valid @RequestBody Comment comment,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid comment data");
        }

        BlogPost blogPost = blogPostService.getBlogPostById(blogPostId);

        if (blogPost == null) {
            return ResponseEntity.notFound().body("Post not found");
        }

        comment.setBlogPost(blogPost);
        commentService.saveComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment added successfully");
    }

    // Obsługa wyjątków

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
    }
}