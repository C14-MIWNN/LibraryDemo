package nl.miwnn.se14.vincent.librarydemo.viewmodel;

import java.util.Set;

/**
 * @author Vincent Velthuizen
 * Give limited information about a Book instead of all the details.
 */
public class BookOverviewVM {
    Long id;
    String title;
    Set<Long> authorIds;
    Set<Long> copyIds;

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

    public Set<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(Set<Long> authorIds) {
        this.authorIds = authorIds;
    }

    public Set<Long> getCopyIds() {
        return copyIds;
    }

    public void setCopyIds(Set<Long> copyIds) {
        this.copyIds = copyIds;
    }
}
