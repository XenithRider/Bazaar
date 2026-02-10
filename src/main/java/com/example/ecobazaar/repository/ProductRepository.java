package com.example.ecobazaar.repository;

import com.example.ecobazaar.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long> {
}
