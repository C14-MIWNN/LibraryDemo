package nl.miwnn.se14.vincent.librarydemo.model;

import jakarta.persistence.*;

/**
 * @author Vincent Velthuizen
 * A physical copy of a book that someone might borrow from our library
 */

@Entity
public class Copy {
    @Id @GeneratedValue
    private Long copyId;

    private Boolean available;

    @ManyToOne
    private Book book;

    public Copy() {
        available = true;
    }

    public Long getCopyId() {
        return copyId;
    }

    public void setCopyId(Long copyId) {
        this.copyId = copyId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
