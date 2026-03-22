package Book_Details.controller;

import Book_Details.model.Author;
import Book_Details.model.Book;
import Book_Details.repo.AuthorRepository;
import Book_Details.repo.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookDetailsController {

    private final AuthorRepository authorRepo;
    private final BookRepository bookRepo;

    public BookDetailsController(AuthorRepository authorRepo, BookRepository bookRepo) {
        this.authorRepo = authorRepo;
        this.bookRepo = bookRepo;
    }

    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestBody Author author) {
        return authorRepo.save(author);
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        Integer authorId = (book.getAuthor() == null) ? null : book.getAuthor().getAuthorId();
        if (authorId == null || authorRepo.findById(authorId).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author does not exist.");
        }
        return bookRepo.save(book);
    }

    @GetMapping("/books/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) {
        return bookRepo.findById(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found."));
    }

    @GetMapping("/authors/{authorId}/books")
    public List<Book> getBooksByAuthor(@PathVariable Integer authorId) {
        return bookRepo.findByAuthor_AuthorId(authorId);
    }
}
