package com.example.ecobazaar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String details;
    private Double price;
    private Double carbonImpact;
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean ecoCertified = false;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean ecoRequested = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    // Constructors
    public Product() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCarbonImpact() {
        return carbonImpact;
    }

    public void setCarbonImpact(Double carbonImpact) {
        this.carbonImpact = carbonImpact;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getEcoCertified() {
        return ecoCertified;
    }

    public void setEcoCertified(Boolean ecoCertified) {
        this.ecoCertified = ecoCertified != null ? ecoCertified : false;
    }

    public Boolean getEcoRequested() {
        return ecoRequested;
    }

    public void setEcoRequested(Boolean ecoRequested) {
        this.ecoRequested = ecoRequested != null ? ecoRequested : false;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    // Helper method for getSellerId
    public Long getSellerId() {
        return seller != null ? seller.getId() : null;
    }

    // Helper method for setSellerId (for backward compatibility)
    public void setSellerId(Long sellerId) {
        // This is a placeholder - use setSeller(User) instead
    }
}