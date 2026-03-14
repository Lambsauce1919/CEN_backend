package Book_Browsing.controller;

import Book_Browsing.model.Book;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookBrowsingController {

    private final List<Book> books = List.of(
            new Book("Cool Programming Book", "Programming", "123", "Tech", 20.00, 4.5, 100),
            new Book("Cooler Programming Book", "Programming", "456", "Tech", 21.00, 10.0, 100),
            new Book("Awesome Cooking Book", "Cooking", "789", "Chef", 22.00, 9.0, 100),
            new Book("Great Book", "Programming", "1011", "Tech", 23.00, 5.0, 100),
            new Book("The Greatest Coding Book", "Programming", "1213", "Tech", 24.00, 4.0, 100),
            new Book("Pizza Book", "Cooking", "1415", "Chef", 25.00, 2.0, 100),
            new Book("Don't Read that Book, Read this Book", "Programming", "1617", "Tech", 26.00, 7.0, 100),
            new Book("Read This Book", "Programming", "1819", "Tech", 27.00, 8.0, 100),
            new Book("Cloudy with a Chance of Meatballs Book", "Cooking", "2021", "Chef", 28.00, 8.5, 100),
            new Book("Exceptionally Cool Coding Book", "Programming", "2223", "Tech", 29.00, 7.0, 100),
            new Book("Very Original Coding Book", "Programming", "2425", "Tech", 30.00, 6.5, 100)


    );

    @GetMapping("/test")
    public String test() {
        return "Browsing API is running";
    }

    @GetMapping("/genre/{genre}")
    public List<Book> getBooksByGenre(@PathVariable String genre) {
        return books.stream()
                .filter(b -> b.getGenre().equalsIgnoreCase(genre))
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
    public List<Book> getBooksByRating(@PathVariable double rating) {
        return books.stream()
                .filter(b -> b.getRating() >= rating)
                .toList();
    }

    @PutMapping("/discount/{publisher}/{percent}")
    public String discountBooksByPublisher(@PathVariable String publisher,
                                           @PathVariable double percent) {
        for (Book b : books) {
            if (b.getPublisher().equalsIgnoreCase(publisher)) {
                double newPrice = b.getPrice() * (1 - percent / 100.0);
                b.setPrice(newPrice);
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
