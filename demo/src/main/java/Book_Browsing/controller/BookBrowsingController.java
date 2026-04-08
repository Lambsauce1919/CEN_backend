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

    private final List<Book> books = new ArrayList<>(List.of(
            makeBook("123", "Cool Programming Book", "Programming", "Tech", 20.00, 4.5, 100),
            makeBook("456", "Cooler Programming Book", "Programming", "Tech", 21.00, 10.0, 100),
            makeBook("789", "Awesome Cooking Book", "Cooking", "Chef", 22.00, 9.0, 100),
            makeBook("1011", "Great Book", "Programming", "Tech", 23.00, 5.0, 100),
            makeBook("1213", "The Greatest Coding Book", "Programming", "Tech", 24.00, 4.0, 100),
            makeBook("1415", "Pizza Book", "Cooking", "Chef", 25.00, 2.0, 100),
            makeBook("1617", "Don't Read that Book, Read this Book", "Programming", "Tech", 26.00, 7.0, 100),
            makeBook("1819", "Read This Book", "Programming", "Tech", 27.00, 8.0, 100),
            makeBook("2021", "Cloudy with a Chance of Meatballs Book", "Cooking", "Chef", 28.00, 8.5, 100),
            makeBook("2223", "Exceptionally Cool Coding Book", "Programming", "Tech", 29.00, 7.0, 100),
            makeBook("2425", "Very Original Coding Book", "Programming", "Tech", 30.00, 6.5, 100)
    ));

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
    public List<Book> getBooksByRating(@PathVariable double rating) {
        return books.stream()
                .filter(b -> b.getRating() != null && b.getRating() >= rating)
                .toList();
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

    private static Book makeBook(String isbn, String name, String genre, String publisher,
                                 double price, double rating, int copiesSold) {
        Book b = new Book();
        b.setIsbn(isbn);
        b.setBookName(name);
        b.setGenre(genre);
        b.setPublisher(publisher);
        b.setPrice(BigDecimal.valueOf(price));
        b.setRating(rating);
        b.setCopiesSold(copiesSold);
        return b;
    }
}
/*
 http://localhost:8080/api/books/genre/Programming //
 http://localhost:8080/api/books/genre/Cooking //
 http://localhost:8080/api/books/rating/4.5
*/
