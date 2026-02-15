package com.example.ecobazaar.repository;

import com.example.ecobazaar.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Get all orders placed by a specific user
    List<Order> findByUserId(Long userId);

    // Calculate total amount spent by that user
    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.userId = :userId")
    Double getTotalSpentByUser( Long userId);

    // Calculate total carbon saved by that user
    @Query("SELECT SUM(o.totalCarbon) FROM Order o WHERE o.userId = :userId")
    Double getTotalCarbonByUser(Long userId);


}
