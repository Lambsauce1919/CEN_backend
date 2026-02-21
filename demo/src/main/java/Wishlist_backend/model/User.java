package Wishlist_backend.model;

public class User {

    private int id;
    private String name;

    public User(){}
    public User(String name){
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }



}
