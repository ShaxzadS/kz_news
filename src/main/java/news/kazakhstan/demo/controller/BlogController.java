package news.kazakhstan.demo.controller;

import news.kazakhstan.demo.models.Post;
import news.kazakhstan.demo.models.PostRepositoryImpl;
import news.kazakhstan.demo.repository.PostRepository;
import news.kazakhstan.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final PostService postService;

    @Autowired
    private  PostRepository postRepository;

    @Autowired
    public BlogController (PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String blog(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "blog";
    }
    @GetMapping("/search")
    public String searchBlog(@RequestParam String query, Model model) {
        List<Post> posts = postService.searchPostsByTitle(query); // Используем postService вместо postRepository
        model.addAttribute("posts", posts);
        return "blog";
    }






    @GetMapping("/add")
    public String addBlogForm(Model model) {
        model.addAttribute("post", new Post());
        return "blog-add";
    }

    @PostMapping("/add")
    public String addPost(@Valid @ModelAttribute("post") Post post, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "blog-add";
        }

        postService.savePost(post);
        return "redirect:/blog";
    }

    @GetMapping("/{id}")
    public String blogDetails(@PathVariable("id") Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "blog-details";
        } else {
            return "redirect:/blog";
        }
    }

    @GetMapping("/edit/{id}")
    public String editBlogForm(@PathVariable("id") Long id, Model model) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            model.addAttribute("post", post.get());
            return "blog-edit";
        } else {
            return "redirect:/blog";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateBlog(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("post") Post updatedPost,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "blog-edit";
        }

        postService.updatePost(id, updatedPost); // Обновление поста через сервис
        return "redirect:/blog/" + id;
    }


    @PostMapping("/delete/{id}")
    public String deleteBlog(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return "redirect:/blog";
    }
}
