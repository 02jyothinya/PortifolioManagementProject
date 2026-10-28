package com.inventorymanagementsystem.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Transaction {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String transactionId;
    private String type;
    private Product product;
    private int quantity;
    private String date;

    // Default constructor required by Jackson
    public Transaction() {
    }

    public Transaction(String transactionId, String type, Product product, int quantity) {
        if (transactionId == null || transactionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Transaction ID is required.");
        }
        if (product == null) {
            throw new IllegalArgumentException("Product is required.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (!"PURCHASE".equalsIgnoreCase(type) && !"SALE".equalsIgnoreCase(type)) {
            throw new IllegalArgumentException("Type must be PURCHASE or SALE.");
        }

        this.transactionId = transactionId;
        this.type = type.toUpperCase();
        this.product = product;
        this.quantity = quantity;
        this.date = LocalDateTime.now().format(FORMATTER);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId
                + ", Type: " + type
                + ", Product: " + (product != null ? product.getName() : "N/A")
                + ", Quantity: " + quantity
                + ", Date: " + date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Transaction other = (Transaction) obj;
        return Objects.equals(transactionId, other.transactionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId);
    }
}
