package nl.miwnn.se14.vincent.librarydemo.service;

import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.se14.vincent.librarydemo.service.mappers.BookDetailMapper;
import nl.miwnn.se14.vincent.librarydemo.service.mappers.BookOverviewMapper;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookDetailVM;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookOverviewVM;
import org.springframework.stereotype.Service;
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

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookOverviewVM> getBooks() {
        List<BookOverviewVM> vmbooks = new ArrayList<>();

        for (Book book : bookRepository.findAll()) {
            vmbooks.add(BookOverviewMapper.toVM(book));
        }

        return vmbooks;
    }

    public BookDetailVM getBook(String title) {
        return BookDetailMapper.toVM(bookRepository.findByTitle(title)
                .orElseThrow(() -> new NotFoundException(String.format("Book with title: %s was not found.", title))));
    }
}
