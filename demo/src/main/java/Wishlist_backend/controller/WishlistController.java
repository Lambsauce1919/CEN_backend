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
    public void createWishlist(@RequestBody Wishlist wishlistData) {
        String token = wishlistData.getToken();
        String sql = "SELECT id FROM users WHERE token = ?";

        try{
            Integer userId = jd.queryForObject(sql, Integer.class, token);
            if (userId != null) {
                jd.update("INSERT INTO wishlists (name, users_id) VALUES (?,?)", wishlistData.getName(), userId);
                System.out.println("Wishlist created!");
            }
        }
        catch (Exception e){
            System.out.println("There was an error while creating the wishlist!");
        }

    }

    /*
    @GetMapping("/all")
    public List<Wishlist> getAllWishlists(){
        return wishlistRepository.findAll();
    }
     */

}