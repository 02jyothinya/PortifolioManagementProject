package com.portifolioproject.model;

public class Asset {

    private String assetId;
    private String assetName;
    private double purchasePrice;

    // Constructor
    public Asset(String assetId, String assetName, double purchasePrice) {
        this.assetId = assetId;
        this.assetName = assetName;
        this.purchasePrice = purchasePrice;
    }

    // Getter for Asset ID
    public String getAssetId() {
        return assetId;
    }

    // Setter for Asset ID
    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    // Getter for Asset Name
    public String getAssetName() {
        return assetName;
    }

    // Setter for Asset Name
    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    // Getter for Purchase Price
    public double getPurchasePrice() {
        return purchasePrice;
    }

    // Setter for Purchase Price
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    // Calculate Current Value
    public double calculateCurrentValue() {
        return purchasePrice;
    }

    // Display Asset
    public void display() {
        System.out.println("Asset ID: " + assetId);
        System.out.println("Asset Name: " + assetName);
        System.out.println("Purchase Price: " + purchasePrice);
    }
}