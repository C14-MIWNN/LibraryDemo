package nl.miwnn.se14.vincent.librarydemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

/**
 * @author Vincent Velthuizen
 * Someone who has written something that can be borrowed from the library
 */

@Entity
public class Author {

    @Id @GeneratedValue
    private Long authorId;

    @NotBlank
    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL) // Cancelling an author means cancelling all of their books?
    private Set<Book> books;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
