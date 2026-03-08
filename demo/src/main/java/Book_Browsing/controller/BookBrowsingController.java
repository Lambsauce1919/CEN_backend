package Book_Browsing.controller;

import Book_Browsing.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookBrowsingController {

    private final List<Book> books = List.of(
            new Book("Cool Book", "Programming"),
            new Book("Cooler Book", "Programming"),
            new Book("Awesome Book", "Cooking")

    );

    @GetMapping("/api/test")
    public String test() {
        return "Browsing API is running";
    }

    @GetMapping("/api/books/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return books.stream()
                .filter(b -> b.getGenre().equalsIgnoreCase(genre))
                .toList();
    }
}

/*
 http://localhost:8080/api/books/genre/Programming //
 http://localhost:8080/api/books/genre/Cooking //
*/
