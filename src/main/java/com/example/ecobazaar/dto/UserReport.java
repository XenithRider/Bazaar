package com.example.ecobazaar.dto;

public class UserReport {
    private Long userId;
    private String userName;
    private Long totalPurchases;
    private Double totalSpent;
    private Double totalCarbonSaved;
    private String ecoBadge;

    public UserReport() {
    }

    public UserReport(Long userId, String userName, Long totalPurchases, Double totalSpent, Double totalCarbonSaved, String ecoBadge) {
        this.userId = userId;
        this.userName = userName;
        this.totalPurchases = totalPurchases;
        this.totalSpent = totalSpent;
        this.totalCarbonSaved = totalCarbonSaved;
        this.ecoBadge = ecoBadge;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(Long totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    public Double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(Double totalSpent) {
        this.totalSpent = totalSpent;
    }

    public Double getTotalCarbonSaved() {
        return totalCarbonSaved;
    }

    public void setTotalCarbonSaved(Double totalCarbonSaved) {
        this.totalCarbonSaved = totalCarbonSaved;
    }

    public String getEcoBadge() {
        return ecoBadge;
    }

    public void setEcoBadge(String ecoBadge) {
        this.ecoBadge = ecoBadge;
    }
}
