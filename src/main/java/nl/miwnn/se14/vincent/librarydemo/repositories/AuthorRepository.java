package nl.miwnn.se14.vincent.librarydemo.repositories;

import nl.miwnn.se14.vincent.librarydemo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
