package Wishlist.controller;

import API_CONTROLLERS.ApiServices;
import API_CONTROLLERS.Api_Client;
import Wishlist.model.Wishlist;
import Wishlist.model.User;
import Wishlist.repository.UserRepository;
import Wishlist.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


import java.util.List;

@RestController
@RequestMapping("/wishlists")
public class WishlistController{

    public static void createWishlist(String name, String token){

        try{
            Wishlist wl = new Wishlist(name, token);
            Api_Client.getApi().createWishlist(wl).execute();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private UserRepository userRepository;



    @PostMapping("/create")
    public void createWishlist(@RequestBody Wishlist wishlistData) {
        User owner = userRepository.findBySessionToken(wishlistData.getToken());
        if (owner != null) {
            Wishlist wishlist = new Wishlist(wishlistData.getName(), owner);
            wishlistRepository.save(wishlist);
        }
    }


    @GetMapping("/all")
    public List<Wishlist> getAllWishlists(){
        return wishlistRepository.findAll();
    }
     */
}