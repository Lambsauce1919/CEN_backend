package Book_Details.repo;

import Book_Details.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByAuthor_AuthorId(Integer authorId);
}
