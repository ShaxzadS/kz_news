package news.kazakhstan.demo.repository;

import news.kazakhstan.demo.models.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository  {
    Post save(Post post);
    Optional<Post> findById(Long id);
    List<Post> findAll();
    void deleteById(Long id);
    List<Post> findPostsByTitle(String title);
    Optional<Post> findPostByTitle(String title);
}
