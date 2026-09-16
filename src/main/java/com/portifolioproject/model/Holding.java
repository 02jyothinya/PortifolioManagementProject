package com.portifolioproject.model;

public class Holding {

    private String holdingId;
    private Asset asset;
    private int quantity;

    // Constructor
    public Holding(String holdingId, Asset asset, int quantity) {
        this.holdingId = holdingId;
        this.asset = asset;
        this.quantity = quantity;
    }

    // Getter
    public String getHoldingId() {
        return holdingId;
    }

    // Setter
    public void setHoldingId(String holdingId) {
        this.holdingId = holdingId;
    }

    // Getter
    public Asset getAsset() {
        return asset;
    }

    // Setter
    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    // Getter
    public int getQuantity() {
        return quantity;
    }

    // Setter
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Calculate total current value
    public double getCurrentValue() {
        return asset.calculateCurrentValue() * quantity;
    }

    // toString
    @Override
    public String toString() {
        return "Holding{" +
                "Holding ID='" + holdingId + '\'' +
                ", Asset=" + asset +
                ", Quantity=" + quantity +
                ", Current Value=" + getCurrentValue() +
                '}';
    }
}


