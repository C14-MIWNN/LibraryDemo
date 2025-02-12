package nl.miwnn.se14.vincent.librarydemo.controller;

import nl.miwnn.se14.vincent.librarydemo.service.BookService;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookDetailVM;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookOverviewVM;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("book/{title}")
    public BookDetailVM deleteBook(@PathVariable("title") String title) {
        return bookService.delete(title);
    }

    @PostMapping("book/save")
    public void saveNewBook(@ModelAttribute("book") BookDetailVM bookToBeSaved) {
        bookService.save(bookToBeSaved);
    }

    @PutMapping("book/update")
    public void updateBook(@ModelAttribute("book") BookDetailVM bookToBeUpdated) {
        bookService.update(bookToBeUpdated);
    }
}
