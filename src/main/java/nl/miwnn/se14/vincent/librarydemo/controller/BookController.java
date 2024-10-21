package nl.miwnn.se14.vincent.librarydemo.controller;

import nl.miwnn.se14.vincent.librarydemo.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Vincent Velthuizen
 * Handle all requests related primarily to books
 */
@Controller
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/book/overview")
    private String showBookOverview(Model datamodel) {
        datamodel.addAttribute("allBooks", bookRepository.findAll());

        return "bookOverview";
    }
}
