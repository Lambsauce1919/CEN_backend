package Wishlist_backend.model;
import java.util.List;

public class Wishlist {

    private int id;
    private String token;
    private String name;
    private User user;
    private List<Book> books;

    public Wishlist(){}

    public Wishlist(String name, User user){
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {this.token = token;}
    public List<Book> getBooks() {return books;}
}
