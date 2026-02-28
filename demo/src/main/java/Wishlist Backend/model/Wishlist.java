package Wishlist.model;
import jakarta.persistence.*;

//@Entity
//@Table(name = "wishlists")
public class Wishlist {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private int id;

    //@Column(unique = true, nullable = false)
    private String name;

   //@ManyToOne
    //@JoinColumn(name = "users_id")
    private String token;

    public Wishlist(){}

    public Wishlist(String name, String token){
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token = token;
    }

    /*
    public int getId() {
        return id;
    }
    //public void setId(int id) {
        this.id = id;
    }
    */
}
