package Wishlist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController{
    @GetMapping("/test-user")
    public String testUser(){
        return "The User Controller is working!";
    }


}