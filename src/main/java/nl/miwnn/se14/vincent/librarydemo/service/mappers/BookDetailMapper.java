package nl.miwnn.se14.vincent.librarydemo.service.mappers;

import nl.miwnn.se14.vincent.librarydemo.model.Author;
import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.model.Copy;
import nl.miwnn.se14.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.se14.vincent.librarydemo.repositories.CopyRepository;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookDetailVM;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Vincent Velthuizen
 * Map Books to BookDetailVM objects (and back when that time comes)
 */
@Component
public class BookDetailMapper {
    private final AuthorRepository authorRepository;
    private final CopyRepository copyRepository;

    public BookDetailMapper(AuthorRepository authorRepository, CopyRepository copyRepository) {
        this.authorRepository = authorRepository;
        this.copyRepository = copyRepository;
    }

    public BookDetailVM toVM(Book book) {
        BookDetailVM vm = new BookDetailVM();
        vm.setId(book.getBookId());
        vm.setTitle(book.getTitle());
        vm.setDescription(book.getDescription());
        vm.setImageUrl(book.getImageUrl());

        vm.setAuthorIds(mapAuthorsToIds(book.getAuthors()));
        vm.setCopyIds(mapCopiesToIds(book.getCopies()));

        return vm;
    }

    public Book fromVM(BookDetailVM vm) {
        Book book = new Book();
        book.setBookId(vm.getId());
        book.setTitle(vm.getTitle());
        book.setDescription(vm.getDescription());
        book.setImageUrl(vm.getImageUrl());

        book.setAuthors(mapIdsToAuthors(vm.getAuthorIds()));
        book.setCopies(mapIdsToCopies(vm.getCopyIds()));

        return book;
    }

    private Set<Long> mapAuthorsToIds(Set<Author> authors) {
        return authors.stream()
                .map(Author::getAuthorId)
                .collect(Collectors.toSet());
    }

    private Set<Long> mapCopiesToIds(Set<Copy> copies) {
        return copies.stream()
                .map(Copy::getCopyId)
                .collect(Collectors.toSet());
    }

    private Set<Author> mapIdsToAuthors(Set<Long> authorIds) {
        return authorIds.stream()
                .map(id -> authorRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("No author found with id: " + id)))
                .collect(Collectors.toSet());
    }

    private Set<Copy> mapIdsToCopies(Set<Long> copyIds) {
        return copyIds.stream()
                .map(id -> copyRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("No copy found with id: " + id)))
                .collect(Collectors.toSet());
    }
}
