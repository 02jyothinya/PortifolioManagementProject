package com.inventorymanagementsystem.model;

public class Product extends Asset implements Comparable<Product> {

    private double price;
    private String category;

    // Default constructor required by Jackson
    public Product() {
        super();
    }

    public Product(String id, String name, String description, double price, String category) {
        super(id, name, description);

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }

        this.price = price;
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getDetails() {
        return "Product ID: " + id
                + ", Name: " + name
                + ", Category: " + category
                + ", Price: $" + String.format("%.2f", price)
                + ", Description: " + description;
    }

    @Override
    public int compareTo(Product other) {
        if (other == null) return 1;
        return Double.compare(this.price, other.price);
    }
}
