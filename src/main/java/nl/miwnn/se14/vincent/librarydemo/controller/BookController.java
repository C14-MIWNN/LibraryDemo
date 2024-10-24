package nl.miwnn.se14.vincent.librarydemo.controller;

import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.se14.vincent.librarydemo.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Vincent Velthuizen
 * Handle all requests related primarily to books
 */
@Controller
public class BookController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookController(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping({"/", "/book/overview"})
    private String showBookOverview(Model datamodel) {
        datamodel.addAttribute("allBooks", bookRepository.findAll());

        return "bookOverview";
    }

    @GetMapping("/book/new")
    private String showBookForm(Model datamodel) {
        datamodel.addAttribute("newBook", new Book());
        datamodel.addAttribute("allAuthors", authorRepository.findAll());

        return "bookForm";
    }

    @PostMapping("/book/new")
    private String saveOrUpdateBook(@ModelAttribute("newBook") Book bookToBeSaved, BindingResult result) {
        if (result.hasErrors()) {
            System.err.println(result.getAllErrors());
            return "redirect:/book/overview";
        }

        bookRepository.save(bookToBeSaved);
        return "redirect:/book/overview";
    }

    @GetMapping("/book/delete/{bookId}")
    private String deleteBook(@PathVariable("bookId") Long bookId) {
        bookRepository.deleteById(bookId);
        return "redirect:/book/overview";
    }
}
