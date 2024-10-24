package nl.miwnn.se14.vincent.librarydemo.controller;

import nl.miwnn.se14.vincent.librarydemo.model.Author;
import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.model.Copy;
import nl.miwnn.se14.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.se14.vincent.librarydemo.repositories.BookRepository;
import nl.miwnn.se14.vincent.librarydemo.repositories.CopyRepository;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Vincent Velthuizen
 * Set some intial data in the database for testing purposes
 **/
@Controller
public class InitializeController {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final CopyRepository copyRepository;


    public InitializeController(AuthorRepository authorRepository, BookRepository bookRepository, CopyRepository copyRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.copyRepository = copyRepository;
    }

    @EventListener
    private void seed(ContextRefreshedEvent event) {
        if (authorRepository.findAll().isEmpty()) {
            initializeDB();
        }
    }

    private void initializeDB() {
        Author brandon = makeAuthor("Brandon Sanderson");
        Author tolkien = makeAuthor("J.R.R. Tolkien");
        Author patrick = makeAuthor("Patrick Rothfuss");

        Book hobbit = makeBook("The Hobbit", tolkien);
        makeCopy(hobbit, false);
        makeCopy(hobbit);
        makeCopy(hobbit);

        Book lotr = makeBook("The Lord of the Rings", tolkien);
        makeCopy(lotr);
        makeCopy(lotr, false);
        makeCopy(lotr);

        Book finalEmpire = makeBook("The Final Empire", brandon);
        makeCopy(finalEmpire);

        Book well = makeBook("The Well of Ascension", brandon);
        makeCopy(well);

        Book hero = makeBook("The Hero of Ages", brandon);
        makeCopy(hero, false);

        Book name = makeBook("The Name of the Wind", patrick);
        makeCopy(name);

        Book fear = makeBook("The Wise Man's Fear", patrick);
        makeCopy(fear);

        makeBook("The Doors of Stone", patrick);

        Book collaboration = makeBook("The Final Name of the Rings", tolkien, brandon, patrick);
        makeCopy(collaboration, false);
    }

    private Author makeAuthor(String name) {
        Author author = new Author();
        author.setName(name);
        authorRepository.save(author);
        return author;
    }

    private Book makeBook(String title, Author ... authors) {
        Book book = new Book();
        book.setTitle(title);

        Set<Author> authorSet = new HashSet<>(Arrays.asList(authors));
        book.setAuthors(authorSet);

        bookRepository.save(book);
        return book;
    }

    private Copy makeCopy(Book book, boolean available) {
        Copy copy = new Copy();
        copy.setBook(book);
        copy.setAvailable(available);
        copyRepository.save(copy);
        return copy;
    }

    private Copy makeCopy(Book book) {
        return makeCopy(book, true);
    }


}
