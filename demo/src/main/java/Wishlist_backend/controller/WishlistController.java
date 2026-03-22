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

@RestController
@RequestMapping("/wishlists")
public class WishlistController{

    @Autowired
    private JdbcTemplate jd;

    @PostMapping("/create")
    public ResponseEntity<?> createWishlist(@RequestBody Wishlist wishlistData) {

        try{
            String token = wishlistData.getToken();
            Integer userId = jd.queryForObject("SELECT id FROM users WHERE token = ? OR stoken = ?", Integer.class, token, token);
            if (userId != null) {
                Integer count = jd.queryForObject("SELECT COUNT(*) FROM wishlists WHERE users_id = ?", Integer.class, userId);

                if (count <= 3) {
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
            if(e.getMessage().contains("23505")){
                //PostgreSQL Unique Violation error
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Wishlists must have unique names!");
            }
            System.out.println("There was an error while creating the wishlist!");
            System.out.println(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found!");
    }

    @PostMapping("/addBook")
    public void addBook(@RequestParam int wishlistId, @RequestParam String bookId) {
        try {
            jd.update("INSERT INTO wishlist_books (wishlist_id, book_id) VALUES (?,?)", wishlistId, bookId);
        }
        catch (Exception e){
            System.out.println("There was an error while adding book!");
            System.out.println(e.getMessage());
        }
    }

    @GetMapping("/return")
    public List<String> returnBooksFromWishlist(@RequestParam int wishlistId) {
        try{
            return jd.queryForList("SELECT book_id FROM wishlist_books WHERE wishlist_id = ?", String.class, wishlistId);

        }
        catch (Exception e){
            System.out.println("There was an error while returning wishlist!");
            System.out.println(e.getMessage());
            //Returns empty list to avoid further errors like null.
            return new ArrayList<>();
        }
    }


}