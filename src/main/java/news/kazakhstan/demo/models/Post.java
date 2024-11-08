package news.kazakhstan.demo.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;



@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq_gen")
    @SequenceGenerator(name = "my_seq_gen", sequenceName = "my_sequence", allocationSize = 1)
    private Long id;


    @NotBlank(message = "Заголовок не должен быть пустым")
    @Size(min = 5, max = 100, message = "Заголовок должен содержать от 5 до 100 символов")
    private String title;

    @NotBlank(message = "Имя автора не должно быть пустым")
    @Size(min = 2, max = 50, message = "Имя автора должно содержать от 2 до 50 символов")
    private String author_name;

    @NotBlank(message = "Фамилия автора не должна быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия автора должна содержать от 2 до 50 символов")
    private String author_surname;

    @Size(max = 255, message = "Текст слишком длинный, допустимо не более 255 символов.")
    private String full_text;
    private int views;



    public Post() {
    }

    public Post(String title, String fullText, String authorName, String authorSurname) {
        this.title = title;
        this.full_text = fullText;
        this.author_name = authorName;
        this.author_surname = authorSurname;
    }


    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
       this.author_name = author_name;
    }

    public String getAuthor_surname() {
        return author_surname;
    }

    public void setAuthor_surname(String author_surname) {
        this.author_surname = author_surname;
    }

    public String getFull_text() {
        return full_text;
    }

    public void setFull_text(String full_text) {
        this.full_text = full_text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
