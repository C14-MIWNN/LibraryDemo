package nl.miwnn.se14.vincent.librarydemo.viewmodel;

import java.util.List;

/**
 * @author Vincent Velthuizen
 * Give limited information about a Book instead of all the details.
 */
public class BookOverviewVM {
    Long id;
    String title;
    List<Long> authors;
    List<Long> copies;

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

    public List<Long> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Long> authors) {
        this.authors = authors;
    }

    public List<Long> getCopies() {
        return copies;
    }

    public void setCopies(List<Long> copies) {
        this.copies = copies;
    }
}
