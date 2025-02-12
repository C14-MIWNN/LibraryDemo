package nl.miwnn.se14.vincent.librarydemo.controller;


import nl.miwnn.se14.vincent.librarydemo.service.BookService;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookDetailVM;
import nl.miwnn.se14.vincent.librarydemo.viewmodel.BookOverviewVM;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getAllBooksShouldReturnListFromService() throws Exception {
        // Arrange
        BookOverviewVM book1 = new BookOverviewVM();
        book1.setTitle("The Hobbit");
        BookOverviewVM book2 = new BookOverviewVM();
        book2.setTitle("The Lord of the Rings");
        when(bookService.getBooks()).thenReturn(List.of(book1, book2));

        // Act & Assert
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("The Hobbit"))
                .andExpect(jsonPath("$[1].title").value("The Lord of the Rings"));
    }

    @Test
    void getBookByTitleShouldReturnBookDetail() throws Exception {
        // Arrange
        BookDetailVM mockBook = new BookDetailVM();
        mockBook.setTitle("Existing Book");
        when(bookService.getBook("Existing Book")).thenReturn(mockBook);

        // Act & Assert
        mockMvc.perform(get("/book/Existing Book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Existing Book"));
    }

    @Test
    void getNonExistentBookShouldReturnNotFound() throws Exception {
        // Arrange
        when(bookService.getBook("NonExistent"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Act & Assert
        mockMvc.perform(get("/book/NonExistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBookShouldReturnDeletedBook() throws Exception {
        // Arrange
        BookDetailVM deletedBook = new BookDetailVM();
        deletedBook.setTitle("Deleted Book");
        when(bookService.delete("Deleted Book")).thenReturn(deletedBook);

        // Act & Assert
        mockMvc.perform(delete("/book/Deleted Book"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Deleted Book"));
    }

    @Test
    void deleteNonExistentBookShouldReturnNotFound() throws Exception {
        // Arrange
        when(bookService.delete("NonExistent"))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Act & Assert
        mockMvc.perform(delete("/book/NonExistent"))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveBookShouldPassCorrectParametersToService() throws Exception {
        // Arrange
        ArgumentCaptor<BookDetailVM> captor = ArgumentCaptor.forClass(BookDetailVM.class);

        // Act
        mockMvc.perform(post("/book/save")
                        .param("title", "New Book")
                        .param("description", "New Description")
                        .param("imageUrl", "new.jpg")
                        .param("authorIds", "1", "2")
                        .param("copyIds", "3"))
                .andExpect(status().isOk());

        // Assert
        verify(bookService).save(captor.capture());
        BookDetailVM savedBook = captor.getValue();
        assertEquals("New Book", savedBook.getTitle());
        assertEquals("New Description", savedBook.getDescription());
        assertEquals(Set.of(1L, 2L), savedBook.getAuthorIds());
        assertEquals(Set.of(3L), savedBook.getCopyIds());
    }

    @Test
    void updateBookShouldPassCorrectParametersToService() throws Exception {
        // Arrange
        ArgumentCaptor<BookDetailVM> captor = ArgumentCaptor.forClass(BookDetailVM.class);

        // Act
        mockMvc.perform(put("/book/update")
                        .param("id", "100")
                        .param("title", "Updated Book")
                        .param("description", "Updated Description")
                        .param("imageUrl", "updated.jpg")
                        .param("authorIds", "4")
                        .param("copyIds", "5"))
                .andExpect(status().isOk());

        // Assert
        verify(bookService).update(captor.capture());
        BookDetailVM updatedBook = captor.getValue();
        assertEquals(100L, updatedBook.getId());
        assertEquals("Updated Book", updatedBook.getTitle());
        assertEquals(Set.of(4L), updatedBook.getAuthorIds());
        assertEquals(Set.of(5L), updatedBook.getCopyIds());
    }
}