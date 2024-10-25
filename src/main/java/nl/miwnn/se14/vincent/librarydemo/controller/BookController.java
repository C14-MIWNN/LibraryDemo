package nl.miwnn.se14.vincent.librarydemo.controller;

import jakarta.validation.Valid;
import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.se14.vincent.librarydemo.repositories.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

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

    @GetMapping("/book/detail/{title}")
    private String showBookDetailPage(@PathVariable("title") String title, Model datamodel) {
        Optional<Book> bookOptional = bookRepository.findByTitle(title);

        if (bookOptional.isEmpty()) {
            return "redirect:/book/overview";
        }

        datamodel.addAttribute("book", bookOptional.get());
        return "bookDetails";
    }

    @GetMapping("/book/new")
    private String showBookForm(Model datamodel) {
        return setupBookForm(datamodel, new Book());
    }

    @GetMapping("/book/edit/{title}")
    private String showBookEditPage(@PathVariable("title") String title, Model datamodel) {
        Optional<Book> bookOptional = bookRepository.findByTitle(title);

        if (bookOptional.isEmpty()) {
            return "redirect:/book/overview";
        }

        return setupBookForm(datamodel, bookOptional.get());
    }

    private String setupBookForm(Model datamodel, Book bookOptional) {
        datamodel.addAttribute("formBook", bookOptional);
        datamodel.addAttribute("allAuthors", authorRepository.findAll());

        return "bookForm";
    }

    @PostMapping("/book/save")
    private String saveBook(@ModelAttribute("formBook") @Valid Book bookToBeSaved, BindingResult result, Model datamodel) {
        Optional<Book> sameName = bookRepository.findByTitle(bookToBeSaved.getTitle());
        if (sameName.isPresent() && !sameName.get().getBookId().equals(bookToBeSaved.getBookId())) {
            result.addError(new FieldError("formBook", "title", "this title is already in use"));
        }

        if (result.hasErrors()) {
            System.err.println(result.getAllErrors());
            return setupBookForm(datamodel, bookToBeSaved);
        }

        bookRepository.save(bookToBeSaved);
        return "redirect:/book/detail/" + bookToBeSaved.getTitle();
    }

    @GetMapping("/book/delete/{bookTitle}")
    private String deleteBook(@PathVariable("bookTitle") String title) {
        bookRepository.findByTitle(title).ifPresent(bookRepository::delete);
        return "redirect:/book/overview";
    }
}
