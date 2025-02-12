package nl.miwnn.se14.vincent.librarydemo.viewmodel;

/**
 * @author Vincent Velthuizen
 * Give all information about a Book, but with references to other objects, instead of the full objects.
 */
public class BookDetailVM extends BookOverviewVM {
    private String description;
    private String imageUrl;

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
}


