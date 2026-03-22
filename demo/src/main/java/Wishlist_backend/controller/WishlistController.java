package Wishlist_backend.controller;

import Wishlist_backend.model.Wishlist;
import Wishlist_backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/wishlists")
public class WishlistController{

    @Autowired
    private JdbcTemplate jd;

    @PostMapping("/create")
    public int createWishlist(@RequestBody Wishlist wishlistData) {

        try{
            String token = wishlistData.getToken();
            Integer userId = jd.queryForObject("SELECT id FROM users WHERE token = ? OR stoken = ?", Integer.class, token, token);
            if (userId != null) {
                Integer count = jd.queryForObject("SELECT COUNT(*) FROM wishlists WHERE users_id = ?", Integer.class, userId);

                if (count < 3) {
                    return jd.queryForObject("INSERT INTO wishlists (name, users_id) VALUES (?,?) RETURNING id", Integer.class, wishlistData.getName(), userId);
                }
                else{
                    System.out.println("The maximum number of wishlists has been reached!");
                    return -1;
                }

            }
        }
        catch (Exception e){
            System.out.println("There was an error while creating the wishlist!");
            System.out.println(e.getMessage());
            return -1;
        }
        return -1;
    }

    @PostMapping("/addBook")
    public void addBook(@RequestParam int wishlistId, @RequestParam int bookId) {
        jd.update("INSERT INTO wishlist_books (wishlist_id, book_id) VALUES (?,?)", wishlistId, bookId);
    }

    /*
    @GetMapping("/all")
    public List<Wishlist> getAllWishlists(){
        return wishlistRepository.findAll();
    }
     */

}