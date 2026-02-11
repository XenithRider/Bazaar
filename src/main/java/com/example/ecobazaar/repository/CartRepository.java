package com.example.ecobazaar.repository;

import com.example.ecobazaar.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List ;

// JPA Repository gives CRUD method automatically
public interface CartRepository extends JpaRepository<CartItem , Long> {


    // custom finder
    List<CartItem> findByUserId(Long userId) ; // to get all cart items for one user
}
