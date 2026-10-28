package com.inventorymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;

public class InventoryData {

    private List<Product> products = new ArrayList<>();
    private List<InventoryItem> stock = new ArrayList<>();
    private List<Supplier> suppliers = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();

    public InventoryData() {
    }

    public InventoryData(List<Product> products, List<InventoryItem> stock,
                         List<Supplier> suppliers, List<Transaction> transactions) {
        this.products = products != null ? products : new ArrayList<>();
        this.stock = stock != null ? stock : new ArrayList<>();
        this.suppliers = suppliers != null ? suppliers : new ArrayList<>();
        this.transactions = transactions != null ? transactions : new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<InventoryItem> getStock() {
        return stock;
    }

    public void setStock(List<InventoryItem> stock) {
        this.stock = stock;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
