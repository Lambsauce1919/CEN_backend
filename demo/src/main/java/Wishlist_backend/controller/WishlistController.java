package Wishlist_backend.controller;

import Wishlist_backend.model.Wishlist;
import Wishlist_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

//Main Class used in backend Wishlist to handle the POST, DELETE, and GET requests.
@RestController
@RequestMapping("/wishlists")
public class WishlistController{

    @Autowired
    private JdbcTemplate jd;

    //The POST request to make unique wishlists (Maximum of 3) of books for the user.
    @PostMapping("/create")
    public ResponseEntity<?> createWishlist(@RequestBody Wishlist wishlistData) {

        try{
            //From the request body, it gets the token given to the user.
            String token = wishlistData.getToken();
            //Finds the user's ID by their session token.
            Integer userId = jd.queryForObject("SELECT id FROM users WHERE token = ? OR stoken = ?", Integer.class, token, token);
            if (userId != null) {
                //Checks if the user has three wishlists already, if not, then it makes one.
                Integer count = jd.queryForObject("SELECT COUNT(*) FROM wishlists WHERE users_id = ?", Integer.class, userId);
                if (count < 3) {
                    int wishlistId = jd.queryForObject("INSERT INTO wishlists (name, users_id) VALUES (?,?) RETURNING id", Integer.class, wishlistData.getName(), userId);
                    return ResponseEntity.ok(wishlistId);
                }
                else{
                    System.out.println("The maximum number of wishlists has been reached!");
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The maximum number of wishlists has been reached!");
                }

            }
        }
        catch (Exception e){
            //PostgreSQL Unique Violation error so that all wishlists have unique names.
            if(e.getMessage().contains("23505")){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Wishlists must have unique names!");
            }
            //For any general error to debug.
            System.out.println("There was an error while creating the wishlist!");
            System.out.println(e.getMessage());
        }
        //In case the token didn't match to a user.
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
    }

    //POST request for users to be able to add books to their desired wishlist.
    @PostMapping("/addBook")
    public void addBook(@RequestParam int wishlistId, @RequestParam String bookId) {
        try {
            //In the wishlist_books table, a new record of the book the user wants to add is made
            //given the specific wishlistID and bookID.
            jd.update("INSERT INTO wishlist_books (wishlist_id, book_id) VALUES (?,?)", wishlistId, bookId);
        }
        catch (Exception e){
            //For any errors to debug.
            System.out.println("There was an error while adding book!");
            System.out.println(e.getMessage());
        }
    }

    //GET request to return the list of books in a specific wishlist.
    @GetMapping("/return")
    public List<String> returnBooksFromWishlist(@RequestParam int wishlistId) {
        try{
            //From the wishlist_books table, it finds and returns all bookIDs that belong to the specified wishlist.
            return jd.queryForList("SELECT book_id FROM wishlist_books WHERE wishlist_id = ?", String.class, wishlistId);
        }
        catch (Exception e){
            //For any errors that occurred to debug.
            System.out.println("There was an error while returning wishlist!");
            System.out.println(e.getMessage());
            //Returns empty list to avoid further errors like null.
            return new ArrayList<>();
        }
    }

    //DELETE request to delete a specific book by its ID from a specific wishlist into the shopping cart.
    @DeleteMapping("/removeToCart")
    public void removeBookToShoppingCart(@RequestParam String sessionToken, @RequestParam int wishlistId, @RequestParam String bookId) {
        try{
            //Inserting the selected bookID into the user's shopping cart in the database.
            jd.update("INSERT INTO shopping_cart (user_id, book_id) VALUES (?,?)", sessionToken, bookId);
            //Deleting the bookID from the database table with the wishlists.
            jd.update("DELETE FROM wishlist_books WHERE wishlist_id = ? AND book_id = ?", wishlistId, bookId);
        }
        catch (Exception e){
            //For debugging general errors that occured.
            System.out.println("There was an error while removing book to cart!");
            System.out.println(e.getMessage());
        }
    }


}