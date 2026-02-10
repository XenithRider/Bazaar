package com.example.ecobazaar.model;


import jakarta.persistence.*;

@Entity // tells hibernate this class maps to the DB
@Table(name="products") // set table name
public class Product {

    @Id  // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;
    private String name ;
    private String details ;
    private String price ;
    private String carbonImpact ;
    private Boolean ecoCertified ;
    private Long sellerId ;

    public Product() {
    }

    public Product(Long id, String name, String details, String price, String carbonImpact, Boolean ecoCertified, Long sellerId) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
        this.carbonImpact = carbonImpact;
        this.ecoCertified = ecoCertified;
        this.sellerId = sellerId;
    }

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCarbonImpact() {
        return carbonImpact;
    }

    public void setCarbonImpact(String carbonImpact) {
        this.carbonImpact = carbonImpact;
    }

    public Boolean getEcoCertified() {
        return ecoCertified;
    }

    public void setEcoCertified(Boolean ecoCertified) {
        this.ecoCertified = ecoCertified;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
}
