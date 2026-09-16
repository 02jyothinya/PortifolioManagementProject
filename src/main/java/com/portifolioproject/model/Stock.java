package com.portifolioproject.model;

public class Stock extends Asset {

    private double currentPrice;

    // Constructor
    public Stock(String assetId, String assetName,
                 double purchasePrice, double currentPrice) {

        super(assetId, assetName, purchasePrice);
        this.currentPrice = currentPrice;
    }

    // Getter
    public double getCurrentPrice() {
        return currentPrice;
    }

    // Setter
    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    // Method overriding
    @Override
    public double calculateCurrentValue() {
        return currentPrice;
    }

    // Display
    public void display() {
        System.out.println("Asset ID: " + getAssetId());
        System.out.println("Asset Name: " + getAssetName());
        System.out.println("Purchase Price: " + getPurchasePrice());
        System.out.println("Current Price: " + currentPrice);
    }

    // toString
    @Override
    public String toString() {
        return "Stock{" +
                "Asset ID='" + getAssetId() + '\'' +
                ", Asset Name='" + getAssetName() + '\'' +
                ", Purchase Price=" + getPurchasePrice() +
                ", Current Price=" + currentPrice +
                '}';
    }
}
