package nl.miwnn.se14.vincent.librarydemo.controller;

import nl.miwnn.se14.vincent.librarydemo.model.Author;
import nl.miwnn.se14.vincent.librarydemo.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author Vincent Velthuizen
 * Handle all requests related primarily to authors
 */

@Controller
@RequestMapping("/author")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/overview")
    private String showAuthorOverview(Model datamodel) {
        datamodel.addAttribute("allAuthors", authorRepository.findAll());
        datamodel.addAttribute("newAuthor", new Author());

        return "authorOverview";
    }

    @PostMapping("/new")
    private String saveOrUpdateAuthor(@ModelAttribute("newAuthor") Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/author/overview";
        }

        authorRepository.save(author);
        return "redirect:/author/overview";
    }

    @GetMapping("/detail/{authorName}")
    private String showAuthorDetailPage(@PathVariable("authorName") String authorName, Model model) {
        Optional<Author> author = authorRepository.findAuthorByName(authorName);

        if (author.isEmpty()) {
            return "redirect:/author";
        }

        model.addAttribute("authorToBeShown", author.get());

        return "authorDetail";
    }
}
