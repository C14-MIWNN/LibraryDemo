package nl.miwnn.se14.vincent.librarydemo.controller;

import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.model.Copy;
import nl.miwnn.se14.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.se14.vincent.librarydemo.repositories.CopyRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

/**
 * @author Vincent Velthuizen
 * Handle all requests related primarily to copies
 */

@Controller
public class CopyController {
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;

    public CopyController(BookRepository bookRepository, CopyRepository copyRepository) {
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    @GetMapping("/copy/new/{bookId}")
    private String createNewCopy(@PathVariable("bookId") Long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isEmpty()) {
            System.err.printf("Could not retrieve book with ID: %d\n", bookId);
            return "redirect:/";
        }

        Copy copy = new Copy();
        copy.setBook(bookOptional.get());
        copyRepository.save(copy);

        return "redirect:/";
    }

    @GetMapping("/copy/form")
    private String showCopyForm(Model datamodel) {
        datamodel.addAttribute("newCopy", new Copy());
        datamodel.addAttribute("allBooks", bookRepository.findAll());

        return "copyForm";
    }

    @PostMapping("/copy/form")
    private String saveOrUpdateCopy(@ModelAttribute("newCopy") Copy copyToBeSaved, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/copy/form";
        }

        copyRepository.save(copyToBeSaved);
        return "redirect:/";
    }
}
