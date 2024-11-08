package news.kazakhstan.demo.models;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import news.kazakhstan.demo.repository.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PostRepositoryImpl implements PostRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Post save(Post post) {
        if (post.getId() == null) {
            entityManager.persist(post);  // Если id == null, создаем новую запись
            return post;
        } else {
            return entityManager.merge(post);  // Если id != null, обновляем существующую запись
        }
    }

    @Override
    public Optional<Post> findById(Long id) {
        Post post = entityManager.find(Post.class, id);  // Поиск записи по id
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findAll() {
        return entityManager.createQuery("SELECT p FROM Post p", Post.class)
                .getResultList();  // Получение всех записей
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Post post = entityManager.find(Post.class, id);
        if (post != null) {
            entityManager.remove(post);  // Удаление записи по id
        }
    }

    @Override
    public List<Post> findPostsByTitle(String title) {
        // Используйте JPQL или Criteria API для поиска по заголовку
        return entityManager.createQuery("SELECT p FROM Post p WHERE p.title LIKE :title", Post.class)
                .setParameter("title", "%" + title + "%")  // Используйте LIKE для поиска по частичному совпадению
                .getResultList();
    }

    @Override
    public Optional<Post> findPostByTitle(String title) {
        return Optional.empty();
    }



}
