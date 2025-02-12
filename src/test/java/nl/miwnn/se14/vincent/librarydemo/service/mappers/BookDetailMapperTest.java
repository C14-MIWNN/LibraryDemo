package nl.miwnn.se14.vincent.librarydemo.service.mappers;

import nl.miwnn.se14.vincent.librarydemo.model.Author;
import nl.miwnn.se14.vincent.librarydemo.model.Book;
import nl.miwnn.se14.vincent.librarydemo.model.Copy;
import nl.miwnn.se14.vincent.librarydemo.repositories.AuthorRepository;
import nl.miwnn.se14.vincent.librarydemo.repositories.CopyRepository;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookDetailVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Vincent Velthuizen
 * Purpose for the class
 */
@ExtendWith(MockitoExtension.class)
class BookDetailMapperTest {
    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private CopyRepository copyRepository;

    @InjectMocks
    private BookDetailMapper bookDetailMapper;

    private Author testAuthor;
    private Copy testCopy;
    private Book testBook;
    private BookDetailVM testVM;

    @BeforeEach
    void setUp() {
        // Setup test entities
        testAuthor = new Author();
        testAuthor.setAuthorId(1L);
        testAuthor.setName("J.R.R. Tolkien");

        testCopy = new Copy();
        testCopy.setCopyId(2L);
        testCopy.setAvailable(true);

        // Setup test Book
        testBook = new Book();
        testBook.setBookId(100L);
        testBook.setTitle("The Hobbit");
        testBook.setDescription("A hobbit's adventure");
        testBook.setImageUrl("hobbit.jpg");
        testBook.setAuthors(Set.of(testAuthor));
        testBook.setCopies(Set.of(testCopy));

        // Setup test VM
        testVM = new BookDetailVM();
        testVM.setId(100L);
        testVM.setTitle("Updated Title");
        testVM.setDescription("New Description");
        testVM.setImageUrl("new-image.jpg");
        testVM.setAuthorIds(Set.of(1L));
        testVM.setCopyIds(Set.of(2L));
    }

    @Test
    void toVM_mapsAllFieldsCorrectly() {
        // Act
        BookDetailVM testBookVM = bookDetailMapper.toVM(testBook);

        // Assert
        assertAll(
                () -> assertEquals(testBook.getBookId(), testBookVM.getId()),
                () -> assertEquals(testBook.getTitle(), testBookVM.getTitle()),
                () -> assertEquals(testBook.getDescription(), testBookVM.getDescription()),
                () -> assertEquals(testBook.getImageUrl(), testBookVM.getImageUrl()),
                () -> assertEquals(1, testBookVM.getAuthorIds().size()),
                () -> assertTrue(testBookVM.getAuthorIds().contains(1L)),
                () -> assertEquals(1, testBookVM.getCopyIds().size()),
                () -> assertTrue(testBookVM.getCopyIds().contains(2L))
        );
    }

    @Test
    void fromVM_mapsAllFieldsCorrectlyWhenIdsAreValid() {
        // Arrange
        when(authorRepository.findById(1L)).thenReturn(java.util.Optional.of(testAuthor));
        when(copyRepository.findById(2L)).thenReturn(java.util.Optional.of(testCopy));

        // Act
        Book result = bookDetailMapper.fromVM(testVM);

        // Assert
        assertAll(
                () -> assertEquals(testVM.getId(), result.getBookId()),
                () -> assertEquals(testVM.getTitle(), result.getTitle()),
                () -> assertEquals(testVM.getDescription(), result.getDescription()),
                () -> assertEquals(testVM.getImageUrl(), result.getImageUrl()),
                () -> assertEquals(1, result.getAuthors().size()),
                () -> assertTrue(result.getAuthors().contains(testAuthor)),
                () -> assertEquals(1, result.getCopies().size()),
                () -> assertTrue(result.getCopies().contains(testCopy))
        );
    }

    @Test
    void fromVM_throwsExceptionForInvalidAuthorId() {
        // Arrange
        testVM.setAuthorIds(Set.of(999L));
        when(authorRepository.findById(999L)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookDetailMapper.fromVM(testVM)
        );
        assertTrue(exception.getMessage().contains("No author found with id: 999"));
    }
}