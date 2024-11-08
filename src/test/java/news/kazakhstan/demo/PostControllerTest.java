package news.kazakhstan.demo;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import news.kazakhstan.demo.controller.BlogController;
import news.kazakhstan.demo.models.Post;
import news.kazakhstan.demo.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class PostControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PostService postService;

    @InjectMocks
   private BlogController blogController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
    }

    @Test
    public void addPost_ShouldReturnFormWithErrors_WhenValidationFails() throws Exception {
        // Имитация ошибки валидации
        Post post = new Post();
        post.setTitle("Short");
        post.setAuthor_name("A");
        post.setAuthor_surname("B");
        post.setFull_text("Text");

        // Выполняем запрос с невалидными данными
        mockMvc.perform(post("/blog/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", post.getTitle())
                        .param("author_name", post.getAuthor_name())
                        .param("author_surname", post.getAuthor_surname())
                        .param("full_text", post.getFull_text()))
                .andExpect(status().isOk())
                .andExpect(view().name("blog-add")); // Ожидаем, что вернётся форма
    }

    @Test
    public void addPost_ShouldRedirect_WhenValidationSucceeds() throws Exception {
        Post post = new Post();
        post.setTitle("Valid Title");
        post.setAuthor_name("John");
        post.setAuthor_surname("Doe");
        post.setFull_text("This is a valid post.");

        // Выполняем запрос с валидными данными
        mockMvc.perform(post("/blog/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", post.getTitle())
                        .param("author_name", post.getAuthor_name())
                        .param("author_surname", post.getAuthor_surname())
                        .param("full_text", post.getFull_text()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog")); // Ожидаем перенаправление на список постов

        // Проверяем, что метод savePost был вызван один раз с правильными данными
        verify(postService, times(1)).savePost(any(Post.class));
    }
}

