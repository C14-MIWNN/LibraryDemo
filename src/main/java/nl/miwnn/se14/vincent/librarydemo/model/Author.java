package nl.miwnn.se14.vincent.librarydemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Vincent Velthuizen
 * Someone who has written something that can be borrowed from the library
 */

@Entity
public class Author {

    @Id @GeneratedValue
    private Long authorId;
    private String name;

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
}
