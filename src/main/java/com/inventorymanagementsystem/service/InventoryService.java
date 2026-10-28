package com.inventorymanagementsystem.service;

import com.inventorymanagementsystem.exception.InsufficientStockException;
import com.inventorymanagementsystem.exception.InvalidStockException;
import com.inventorymanagementsystem.exception.ProductNotFoundException;
import com.inventorymanagementsystem.exception.SupplierNotFoundException;
import com.inventorymanagementsystem.model.InventoryData;
import com.inventorymanagementsystem.model.InventoryItem;
import com.inventorymanagementsystem.model.Product;
import com.inventorymanagementsystem.model.Supplier;
import com.inventorymanagementsystem.model.Transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InventoryService {

    private final Map<String, Product> products = new LinkedHashMap<>();
    private final Map<String, Supplier> suppliers = new LinkedHashMap<>();
    private final Map<String, InventoryItem> stock = new LinkedHashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();

    public synchronized void addProduct(Product product, int quantity) throws InvalidStockException {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (quantity < 0) {
            throw new InvalidStockException("Initial quantity cannot be negative.");
        }
        if (products.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product ID '" + product.getId() + "' already exists.");
        }

        products.put(product.getId(), product);
        stock.put(product.getId(), new InventoryItem(product, quantity));
        System.out.println("Product added successfully: " + product.getName());
    }

    public synchronized void addSupplier(Supplier supplier) {
        if (supplier == null) {
            throw new IllegalArgumentException("Supplier cannot be null.");
        }
        if (suppliers.containsKey(supplier.getId())) {
            throw new IllegalArgumentException("Supplier ID '" + supplier.getId() + "' already exists.");
        }

        suppliers.put(supplier.getId(), supplier);
        System.out.println("Supplier added successfully: " + supplier.getName());
    }

    public Product getProduct(String id) throws ProductNotFoundException {
        Product p = products.get(id);
        if (p == null) {
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
        return p;
    }

    public Product searchProduct(String id) {
        return products.get(id);
    }

    public List<Product> searchProductsByName(String query) {
        List<Product> matches = new ArrayList<>();
        if (query == null || query.trim().isEmpty()) return matches;
        String lower = query.toLowerCase();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(lower) || p.getDescription().toLowerCase().contains(lower)) {
                matches.add(p);
            }
        }
        return matches;
    }

    public Supplier getSupplier(String id) throws SupplierNotFoundException {
        Supplier s = suppliers.get(id);
        if (s == null) {
            throw new SupplierNotFoundException("Supplier not found with ID: " + id);
        }
        return s;
    }

    public InventoryItem getInventoryItem(String productId) throws ProductNotFoundException {
        InventoryItem item = stock.get(productId);
        if (item == null) {
            throw new ProductNotFoundException("No inventory stock record for product ID: " + productId);
        }
        return item;
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }

    public Collection<Supplier> getAllSuppliers() {
        return suppliers.values();
    }

    public Collection<InventoryItem> getAllStockItems() {
        return stock.values();
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    public synchronized void updateStock(String productId, int change)
            throws ProductNotFoundException, InvalidStockException, InsufficientStockException {
        InventoryItem item = stock.get(productId);
        if (item == null) {
            throw new ProductNotFoundException("Product ID not found: " + productId);
        }

        long updated = (long) item.getQuantity() + change;
        if (updated < 0) {
            throw new InsufficientStockException(
                    "Insufficient stock for product " + productId + ". Current: " + item.getQuantity() + ", requested change: " + change);
        }
        if (updated > Integer.MAX_VALUE) {
            throw new InvalidStockException("Stock level exceeds maximum integer limit.");
        }

        item.setQuantity((int) updated);
        System.out.println("Stock updated successfully for Product ID " + productId + ". New Stock: " + item.getQuantity());
    }

    public synchronized void recordTransaction(Transaction transaction)
            throws ProductNotFoundException, InvalidStockException, InsufficientStockException {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null.");
        }

        Product product = transaction.getProduct();
        if (product == null) {
            throw new IllegalArgumentException("Transaction must have an associated product.");
        }

        Product existing = products.get(product.getId());
        if (existing == null) {
            throw new ProductNotFoundException("Product not registered in inventory: " + product.getId());
        }

        int quantity = transaction.getQuantity();
        String type = transaction.getType();
        int change;

        if ("PURCHASE".equalsIgnoreCase(type)) {
            change = quantity;
        } else if ("SALE".equalsIgnoreCase(type)) {
            change = -quantity;
        } else {
            throw new IllegalArgumentException("Invalid transaction type: " + type + ". Must be PURCHASE or SALE.");
        }

        updateStock(product.getId(), change);
        transactions.add(transaction);
        System.out.println("Transaction recorded successfully: " + transaction.getTransactionId());
    }

    public void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available in inventory.");
            return;
        }
        System.out.println("\n========== PRODUCT CATALOG ==========");
        for (Product product : products.values()) {
            InventoryItem item = stock.get(product.getId());
            int qty = item != null ? item.getQuantity() : 0;
            System.out.println(product.getDetails() + ", Stock: " + qty);
        }
    }

    public void viewSuppliers() {
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers available.");
            return;
        }
        System.out.println("\n========== SUPPLIER DIRECTORY ==========");
        for (Supplier supplier : suppliers.values()) {
            System.out.println(supplier.getDetails());
        }
    }

    public void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
            return;
        }
        System.out.println("\n========== TRANSACTION HISTORY ==========");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    public List<Product> sortProductsByPrice(boolean ascending) {
        List<Product> sorted = new ArrayList<>(products.values());
        if (ascending) {
            sorted.sort(Comparator.comparingDouble(Product::getPrice));
        } else {
            sorted.sort(Comparator.comparingDouble(Product::getPrice).reversed());
        }
        return sorted;
    }

    public List<InventoryItem> sortProductsByQuantity(boolean ascending) {
        List<InventoryItem> sorted = new ArrayList<>(stock.values());
        if (ascending) {
            sorted.sort(Comparator.comparingInt(InventoryItem::getQuantity));
        } else {
            sorted.sort(Comparator.comparingInt(InventoryItem::getQuantity).reversed());
        }
        return sorted;
    }

    // Build complete InventoryData snapshot for JSON serialization
    public InventoryData getInventoryData() {
        return new InventoryData(
                new ArrayList<>(products.values()),
                new ArrayList<>(stock.values()),
                new ArrayList<>(suppliers.values()),
                new ArrayList<>(transactions)
        );
    }

    // Populate inventory state from deserialized InventoryData
    public synchronized void loadInventoryData(InventoryData data) {
        if (data == null) return;

        products.clear();
        stock.clear();
        suppliers.clear();
        transactions.clear();

        if (data.getSuppliers() != null) {
            for (Supplier s : data.getSuppliers()) {
                suppliers.put(s.getId(), s);
            }
        }

        if (data.getProducts() != null) {
            for (Product p : data.getProducts()) {
                products.put(p.getId(), p);
            }
        }

        if (data.getStock() != null) {
            for (InventoryItem item : data.getStock()) {
                if (item.getProduct() != null) {
                    // Sync reference with products map
                    Product p = products.get(item.getProduct().getId());
                    if (p != null) {
                        item.setProduct(p);
                    } else {
                        products.put(item.getProduct().getId(), item.getProduct());
                    }
                    stock.put(item.getProduct().getId(), item);
                }
            }
        }

        // Ensure every product has a stock entry
        for (Product p : products.values()) {
            stock.putIfAbsent(p.getId(), new InventoryItem(p, 0));
        }

        if (data.getTransactions() != null) {
            for (Transaction t : data.getTransactions()) {
                if (t.getProduct() != null) {
                    Product p = products.get(t.getProduct().getId());
                    if (p != null) {
                        t.setProduct(p);
                    }
                }
                transactions.add(t);
            }
        }

        System.out.println("Loaded " + products.size() + " products, "
                + suppliers.size() + " suppliers, "
                + transactions.size() + " transactions into active inventory.");
    }
}
