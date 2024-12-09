package nl.miwnn.se14.vincent.librarydemo.controller;

import jakarta.validation.Valid;
import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.se14.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.se14.vincent.librarydemo.service.BookService;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookDetailVM;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookOverviewVM;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * @author Vincent Velthuizen
 * Handle all requests related primarily to books
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<BookOverviewVM> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("book/{title}")
    public BookDetailVM getBook(@PathVariable("title") String title) {
        return bookService.getBook(title);
    }
}
