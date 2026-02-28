package Wishlist.repository;

import Wishlist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{

    User findBySessionToken(String sessionToken);
}