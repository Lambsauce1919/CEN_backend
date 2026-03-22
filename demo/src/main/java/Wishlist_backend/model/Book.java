package Wishlist_backend.model;

public class Book {
    private int bookId;
    private String title;

    public Book(int bookId, String title) {
        this.bookId = bookId;
        this.title = title;
    }
    public int getBookId() {return bookId;}
    public String getTitle() {return title;}
}
