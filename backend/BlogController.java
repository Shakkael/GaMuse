// plik BlogController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class BlogController {
    
    @Autowired
    private BlogPostService blogPostService;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private MediaService mediaService;
    
    // Strona główna bloga
    @GetMapping("/")
    public String home(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<BlogPost> blogPosts = blogPostService.getAllBlogPosts(page);
        model.addAttribute("blogPosts", blogPosts);
        return "home";
    }
    
    // Logowanie
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    // Tworzenie nowego postu
    @GetMapping("/create")
    public String createBlogPostForm(Model model) {
        model.addAttribute("blogPost", new BlogPost());
        return "create";
    }
    
    @PostMapping("/create")
    public String createBlogPost(@Valid @ModelAttribute("blogPost") BlogPost blogPost, BindingResult bindingResult,
                                 @RequestParam("media") MultipartFile mediaFile, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        
        try {
            Media media = mediaService.saveMedia(mediaFile);
            blogPost.setMedia(media);
            blogPostService.saveBlogPost(blogPost);
            redirectAttributes.addFlashAttribute("successMessage", "Post created successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save media file!");
        }
        
        return "redirect:/";
    }
    
    // Wyświetlanie pojedynczego postu
    @GetMapping("/post/{id}")
    public String viewBlogPost(@PathVariable("id") Long id, Model model) {
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        List<Comment> comments = commentService.getCommentsByBlogPostId(id);
        model.addAttribute("blogPost", blogPost);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new Comment());
        return "post";
    }
    
    // Dodawanie komentarza
    @PostMapping("/post/{id}/comment")
    public String addComment(@PathVariable("id") Long id, @Valid @ModelAttribute("comment") Comment comment,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid comment data!");
            return "redirect:/post/" + id;
        }
        
        BlogPost blogPost = blogPostService.getBlogPostById(id);
        
        if (blogPost == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Post not found!");
            return "redirect:/";
        }
        
        comment.setBlogPost(blogPost);
        commentService.saveComment(comment);
        redirectAttributes.addFlashAttribute("successMessage", "Comment added successfully!");
        return "redirect:/post/" + id;
    }
    
    // Stronicowanie
    @GetMapping("/page/{page}")
    public String showPage(@PathVariable("page") int page, Model model) {
        Page<BlogPost> blogPosts = blogPostService.getAllBlogPosts(page);
        model.addAttribute("blogPosts", blogPosts);
        return "home";
    }
    
    // Wyszukiwanie
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        List<BlogPost> searchResults = blogPostService.searchBlogPosts(keyword);
        model.addAttribute("searchResults", searchResults);
        return "search";
    }
    
    // Personalizacja, opcje sortowania, statystyki, edytor WYSIWYG (do zaimplementowania)
    
    // Obsługa mediów
    @GetMapping("/media/{id}")
    public ResponseEntity<byte[]> getMedia(@PathVariable("id") Long id) {
        Media media = mediaService.getMediaById(id);
        if (media != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(media.getType()));
            headers.setContentDisposition(ContentDisposition.builder("inline").filename(media.getFilename()).build());
            return new ResponseEntity<>(media.getData(), headers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    // Kontroler REST (do zaimplementowania)
    
    // Obsługa wyjątków (do zaimplementowania)
}
