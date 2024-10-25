package nl.miwnn.se14.vincent.librarydemo.controller;

import nl.miwnn.se14.vincent.librarydemo.model.Author;
import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
        datamodel.addAttribute("formAuthor", new Author());

        return "authorOverview";
    }

    @PostMapping("/save")
    private String saveOrUpdateAuthor(@ModelAttribute("formAuthor") Author author, BindingResult result) {
        Optional<Author> sameName = authorRepository.findAuthorByName(author.getName());
        if (sameName.isPresent() && !sameName.get().getAuthorId().equals(author.getAuthorId())) {
            result.addError(new FieldError("formAuthor", "name", "there is already an author with this name"));
        }

        if (result.hasErrors()) {
            return "authorForm";
        }

        authorRepository.save(author);
        return "redirect:/author/overview";
    }

    @GetMapping("/edit/{authorName}")
    private String showEditAuthorForm(@PathVariable("authorName") String name, Model datamodel) {
        Optional<Author> authorOptional = authorRepository.findAuthorByName(name);

        if (authorOptional.isEmpty()) {
            return "redirect:/author/overview";
        }

        datamodel.addAttribute("formAuthor", authorOptional.get());
        return "authorForm";
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

    @GetMapping("/delete/{authorName}")
    private String deleteAuthor(@PathVariable("authorName") String name) {
        authorRepository.findAuthorByName(name).ifPresent(authorRepository::delete);

        return "redirect:/author/overview";
    }
}
