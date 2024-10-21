package nl.miwnn.se14.vincent.librarydemo.repositories;

import nl.miwnn.se14.vincent.librarydemo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
