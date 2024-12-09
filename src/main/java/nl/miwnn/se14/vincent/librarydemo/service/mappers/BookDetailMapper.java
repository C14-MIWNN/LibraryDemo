package nl.miwnn.se14.vincent.librarydemo.service.mappers;

import nl.miwnn.se14.vincent.librarydemo.model.Author;
import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.model.Copy;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookDetailVM;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Vincent Velthuizen
 * Map Books to BookDetailVM objects (and back when that time comes)
 */
public class BookDetailMapper {
    public static BookDetailVM toVM(Book book) {
        BookDetailVM bookvm = new BookDetailVM();

        bookvm.setId(book.getBookId());
        bookvm.setTitle(book.getTitle());
        bookvm.setDescription(book.getDescription());
        bookvm.setImageUrl(book.getImageUrl());
        bookvm.setAuthors(new HashSet<>());
        for (Author author : book.getAuthors()) {
            bookvm.getAuthors().add(author.getAuthorId());
        }
        bookvm.setCopies((new HashSet<>()));
        for (Copy copy : book.getCopies()) {
            bookvm.getCopies().add(copy.getCopyId());
        }

        return bookvm;
    }
}
