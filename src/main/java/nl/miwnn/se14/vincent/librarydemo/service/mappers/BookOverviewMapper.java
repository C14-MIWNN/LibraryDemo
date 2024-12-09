package nl.miwnn.se14.vincent.librarydemo.service.mappers;

import nl.miwnn.se14.vincent.librarydemo.model.Author;
import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.model.Copy;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookOverviewVM;

import java.util.ArrayList;

/**
 * @author Vincent Velthuizen
 * Map books to BookOverviewVM objects
 */
public class BookOverviewMapper {
    public static BookOverviewVM toVM(Book book) {
        BookOverviewVM bookOverviewVM = new BookOverviewVM();

        bookOverviewVM.setId(book.getBookId());
        bookOverviewVM.setTitle(book.getTitle());
        bookOverviewVM.setAuthors(new ArrayList<>());
        for (Author author : book.getAuthors()) {
            bookOverviewVM.getAuthors().add(author.getAuthorId());
        }
        bookOverviewVM.setCopies((new ArrayList<>()));
        for (Copy copy : book.getCopies()) {
            bookOverviewVM.getCopies().add(copy.getCopyId());
        }

        return bookOverviewVM;
    }
}
