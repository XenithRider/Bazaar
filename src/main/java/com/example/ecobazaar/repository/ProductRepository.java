package com.example.ecobazaar.repository;

import com.example.ecobazaar.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByEcoCertifiedTrue();
    List<Product> findByEcoCertifiedTrueOrderByCarbonImpactAsc();

    Optional<Product> findFirstByEcoCertifiedTrueAndNameContainingIgnoreCase(String namePart);

    Optional<Product> findFirstByEcoCertifiedFalseAndNameContainingIgnoreCase(String keyword);

    // REMOVED: List<Product> findByEcoRequestedTrue();
    // (ecoRequested field doesn't exist in Product model)

    // FIXED: Changed from findBySeller_Id to findBySellerId
    List<Product> findBySellerId(Long sellerId);

    List<Product> findByEcoCertifiedTrueAndNameContainingIgnoreCase(String name);

    List<Product> findByEcoCertifiedFalse();

    Optional<Product> findFirstByNameContainingAndEcoCertifiedTrue(String namePart);
}