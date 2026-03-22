package Book_Details.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "isbn", length = 20)
    private String isbn;

    @Column(name = "book_name", nullable = false, length = 255)
    private String bookName;

    @Column(name = "book_description")
    private String bookDescription;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "genre", length = 100)
    private String genre;

    @Column(name = "publisher", length = 255)
    private String publisher;

    @Column(name = "year_published")
    private Integer yearPublished;

    @Column(name = "copies_sold", nullable = false)
    private Integer copiesSold = 0;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id", referencedColumnName = "author_id")
    private Author author;

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getBookDescription() { return bookDescription; }
    public void setBookDescription(String bookDescription) { this.bookDescription = bookDescription; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getYearPublished() { return yearPublished; }
    public void setYearPublished(Integer yearPublished) { this.yearPublished = yearPublished; }

    public Integer getCopiesSold() { return copiesSold; }
    public void setCopiesSold(Integer copiesSold) { this.copiesSold = copiesSold; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}

