package com.example.ecobazaar.repository;

import com.example.ecobazaar.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Allows fetching a user’s past orders.
    List<Order> findByUserId(Long userId);
}
