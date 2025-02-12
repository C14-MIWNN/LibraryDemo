package nl.miwnn.se14.vincent.librarydemo.service.mappers;

import nl.miwnn.se14.vincent.librarydemo.model.Author;
import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.model.Copy;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookOverviewVM;

import java.util.HashSet;

/**
 * @author Vincent Velthuizen
 * Map books to BookOverviewVM objects
 */
public class BookOverviewMapper {
    public static BookOverviewVM toVM(Book book) {
        BookOverviewVM bookOverviewVM = new BookOverviewVM();

        bookOverviewVM.setId(book.getBookId());
        bookOverviewVM.setTitle(book.getTitle());
        bookOverviewVM.setAuthorIds(new HashSet<>());
        for (Author author : book.getAuthors()) {
            bookOverviewVM.getAuthorIds().add(author.getAuthorId());
        }
        bookOverviewVM.setCopyIds((new HashSet<>()));
        for (Copy copy : book.getCopies()) {
            bookOverviewVM.getCopyIds().add(copy.getCopyId());
        }

        return bookOverviewVM;
    }
}
