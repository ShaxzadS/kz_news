package news.kazakhstan.demo.service;

import news.kazakhstan.demo.models.Post;
import news.kazakhstan.demo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getAllPosts() {
        return (List<Post>) postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public void updatePost(Long id, Post updatedPost) {
        Optional<Post> existingPost = postRepository.findById(id);
        if (existingPost.isPresent()) {
            Post post = existingPost.get();
            post.setTitle(updatedPost.getTitle());
            post.setAuthor_name(updatedPost.getAuthor_name());
            post.setAuthor_surname(updatedPost.getAuthor_surname());
            post.setFull_text(updatedPost.getFull_text());
            postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found with id: " + id);
        }
    }

    public List<Post> searchPostsByTitle(String title) {
        return postRepository.findPostsByTitle(title);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
