package nl.miwnn.se14.vincent.librarydemo.model;

import jakarta.persistence.*;

import java.util.Set;

/**
 * @author Vincent Velthuizen
 * A book for which our library may have some copies
 */
@Entity
public class Book {
    @Id @GeneratedValue
    private Long bookId;
    @Column(unique = true)
    private String title;

    @OneToMany(mappedBy = "book")
    private Set<Copy> copies;

    @ManyToMany
    private Set<Author> authors;

    public String getAuthorString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Author author : authors) {
            stringBuilder.append(author.getName()).append(" ");
        }

        return stringBuilder.toString();
    }

    public int getNumberOfAvailableCopies() {
        int count = 0;

        for (Copy copy : copies) {
            if (copy.getAvailable()) {
                count++;
            }
        }

        return count;
    }

    public int getNumberOfCopies() {
        return copies.size();
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }
}
