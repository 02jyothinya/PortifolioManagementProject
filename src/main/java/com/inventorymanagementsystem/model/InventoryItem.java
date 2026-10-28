package com.inventorymanagementsystem.model;

import java.util.Objects;

public class InventoryItem {

    private Product product;
    private int quantity;

    // Default constructor required by Jackson
    public InventoryItem() {
    }

    public InventoryItem(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Product is required.");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product is required.");
        }
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public synchronized void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    public synchronized void addStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to add cannot be negative.");
        }
        this.quantity += amount;
    }

    public synchronized void deductStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to deduct cannot be negative.");
        }
        if (this.quantity < amount) {
            throw new IllegalArgumentException("Insufficient stock. Available: " + this.quantity + ", Requested: " + amount);
        }
        this.quantity -= amount;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "product=" + (product != null ? product.getName() : "null") +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InventoryItem other = (InventoryItem) obj;
        if (product == null || other.product == null) return false;
        return Objects.equals(product.getId(), other.product.getId());
    }

    @Override
    public int hashCode() {
        return product != null ? Objects.hash(product.getId()) : 0;
    }
}
