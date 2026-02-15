package com.example.ecobazaar.dto;

public record PendingSellerDto(
        Long id,
        String name,
        String email,
        int productCount
) {}
