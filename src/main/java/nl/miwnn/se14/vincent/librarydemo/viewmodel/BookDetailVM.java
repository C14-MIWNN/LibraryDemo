package nl.miwnn.se14.vincent.librarydemo.viewmodel;

import nl.miwnn.se14.vincent.librarydemo.model.Copy;
import nl.miwnn.se14.vincent.librarydemo.service.mappers.BookOverviewMapper;

import java.util.Set;

/**
 * @author Vincent Velthuizen
 * Give all information about a Book, but with references to other objects, instead of the full objects.
 */
public class BookDetailVM {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private Set<Long> copies;
    private Set<Long> authors;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Set<Long> getCopies() {
        return copies;
    }

    public void setCopies(Set<Long> copies) {
        this.copies = copies;
    }

    public Set<Long> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Long> authors) {
        this.authors = authors;
    }
}


