package nl.miwnn.se14.vincent.librarydemo.service;

import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.se14.vincent.librarydemo.service.mappers.BookDetailMapper;
import nl.miwnn.se14.vincent.librarydemo.service.mappers.BookOverviewMapper;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookDetailVM;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookOverviewVM;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vincent Velthuizen
 * Serve books to the controller layer
 */
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookDetailMapper bookDetailMapper;

    public BookService(BookRepository bookRepository, BookDetailMapper bookDetailMapper) {
        this.bookRepository = bookRepository;
        this.bookDetailMapper = bookDetailMapper;
    }

    public List<BookOverviewVM> getBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookOverviewMapper::toVM)
                .toList();
    }

    public BookDetailVM getBook(String title) {
        return bookDetailMapper.toVM(bookRepository.findByTitle(title)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with title: " + title)));
    }

    public void save(BookDetailVM bookToBeSaved) {
        if (bookRepository.existsByTitle(bookToBeSaved.getTitle())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Title already exists");
        }

        Book book = bookDetailMapper.fromVM(bookToBeSaved);
        bookRepository.save(book);
    }

    public BookDetailVM delete(String title) {
        Book bookToDelete = bookRepository.findByTitle(title)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "No book with title: " + title));
        bookRepository.delete(bookToDelete);
        return bookDetailMapper.toVM(bookToDelete);
    }

    public void update(BookDetailVM bookToBeUpdated) {
        Book existingBook = bookRepository.findById(bookToBeUpdated.getId())
                .orElseThrow(() -> new ResponseStatusException( HttpStatus.NO_CONTENT,
                                                                "No book with id: " + bookToBeUpdated.getId()));
        if (!existingBook.getTitle().equals(bookToBeUpdated.getTitle())
                && bookRepository.existsByTitle(bookToBeUpdated.getTitle())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Title already exists");
        }

        bookRepository.save(bookDetailMapper.fromVM(bookToBeUpdated));
    }
}
