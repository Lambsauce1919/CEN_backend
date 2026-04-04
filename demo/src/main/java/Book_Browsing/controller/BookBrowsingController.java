package Book_Browsing.controller;

import Book_Details.model.Book;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookBrowsingController {

    private final List<Book> books = new ArrayList<>();

    @GetMapping("/test")
    public String test() {
        return "Browsing API is running";
    }

    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return books.stream()
                .filter(b -> b.getGenre() != null && b.getGenre().equalsIgnoreCase(genre))
                .toList();
    }

    @GetMapping("/top-sellers")
    public List<Book> getTopSellers() {
        return books.stream()
                .sorted(Comparator.comparingInt(Book::getCopiesSold).reversed())
                .limit(10)
                .toList();
    }

    @GetMapping("/rating/{rating}")
    public String getBooksByRating(@PathVariable double rating) {
        return "Rating filter requires rating data in Book model.";
    }

    @PutMapping("/discount/{publisher}/{percent}")
    public String discountBooksByPublisher(@PathVariable String publisher,
                                           @PathVariable double percent) {
        for (Book b : books) {
            if (b.getPublisher() != null
                    && b.getPublisher().equalsIgnoreCase(publisher)
                    && b.getPrice() != null) {

                double newPrice = b.getPrice().doubleValue() * (1 - percent / 100.0);
                b.setPrice(BigDecimal.valueOf(newPrice));
            }
        }

        return "Discount applied to books from publisher: " + publisher;
    }
}

/*
 http://localhost:8080/api/books/genre/Programming //
 http://localhost:8080/api/books/genre/Cooking //
 http://localhost:8080/api/books/rating/4.5
*/
