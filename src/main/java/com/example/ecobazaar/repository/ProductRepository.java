package com.example.ecobazaar.repository;

import com.example.ecobazaar.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product , Long> {

    // Get only eco-friendly products
    List<Product> findByEcoCertifiedTrue() ;

    // 2. Get eco-certified products sorted by carbon impact
    List<Product> findByEcoCertifiedTrueOrderByCarbonImpactAsc();

    Optional<Product> findFirstByNameContainingAndEcoCertifiedTrue(String name);
}
