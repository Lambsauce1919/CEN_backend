package Book_Browsing.model;

public class Book{
    private String title;
    private String genre;
    private String isbn;
    private String publisher;
    private double price;
    private double rating;
    private int copiesSold;


    public Book(String title, String genre, String isbn, String publisher, double price, double rating, int copiesSold) {
        this.title = title;
        this.genre = genre;
        this.isbn = isbn;
        this.publisher = publisher;
        this.price = price;
        this.rating = rating;
        this.copiesSold = copiesSold;

    }

    public String getTitle() {

        return title;
    }

    public String getGenre() {

        return genre;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    public int getCopiesSold() {
        return copiesSold;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
