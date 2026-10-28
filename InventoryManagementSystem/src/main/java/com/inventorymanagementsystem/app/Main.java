package com.inventorymanagementsystem.app;

import com.inventorymanagementsystem.concurrent.StockUpdateTask;
import com.inventorymanagementsystem.exception.InsufficientStockException;
import com.inventorymanagementsystem.exception.InvalidStockException;
import com.inventorymanagementsystem.exception.ProductNotFoundException;
import com.inventorymanagementsystem.exception.SupplierNotFoundException;
import com.inventorymanagementsystem.model.InventoryData;
import com.inventorymanagementsystem.model.InventoryItem;
import com.inventorymanagementsystem.model.Product;
import com.inventorymanagementsystem.model.Supplier;
import com.inventorymanagementsystem.model.Transaction;
import com.inventorymanagementsystem.service.InventoryService;
import com.inventorymanagementsystem.util.JVMInfo;
import com.inventorymanagementsystem.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("       INVENTORY MANAGEMENT SYSTEM APPLICATION   ");
        System.out.println("=================================================");

        Scanner scanner = new Scanner(System.in);
        InventoryService inventoryService = new InventoryService();

        // Seed some initial data for convenience
        seedInitialData(inventoryService);

        while (true) {
            System.out.println("\n----------------- MAIN MENU -----------------");
            System.out.println("1.  Add Product");
            System.out.println("2.  View All Products");
            System.out.println("3.  Search Product (ID or Name)");
            System.out.println("4.  Update Stock");
            System.out.println("5.  Add Supplier");
            System.out.println("6.  View Suppliers");
            System.out.println("7.  Record Transaction (PURCHASE / SALE)");
            System.out.println("8.  View Transactions");
            System.out.println("9.  Sort Products (Price / Quantity)");
            System.out.println("10. Save Data to JSON");
            System.out.println("11. Load Data from JSON");
            System.out.println("12. Concurrent Stock Updates (ExecutorService)");
            System.out.println("13. Display JVM Information");
            System.out.println("0.  Exit");
            System.out.println("---------------------------------------------");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                continue;
            }

            try {
                switch (choice) {
                    case 1: {
                        System.out.println("\n--- Add New Product ---");
                        System.out.print("Enter Product ID: ");
                        String id = scanner.nextLine().trim();

                        System.out.print("Enter Product Name: ");
                        String name = scanner.nextLine().trim();

                        System.out.print("Enter Description: ");
                        String desc = scanner.nextLine().trim();

                        System.out.print("Enter Price: ");
                        double price = Double.parseDouble(scanner.nextLine().trim());

                        System.out.print("Enter Category: ");
                        String category = scanner.nextLine().trim();

                        System.out.print("Enter Initial Stock Quantity: ");
                        int quantity = Integer.parseInt(scanner.nextLine().trim());

                        Product product = new Product(id, name, desc, price, category);
                        inventoryService.addProduct(product, quantity);
                        break;
                    }

                    case 2: {
                        inventoryService.viewProducts();
                        break;
                    }

                    case 3: {
                        System.out.println("\n--- Search Product ---");
                        System.out.print("Enter Product ID or Keyword: ");
                        String query = scanner.nextLine().trim();

                        Product byId = inventoryService.searchProduct(query);
                        if (byId != null) {
                            try {
                                InventoryItem item = inventoryService.getInventoryItem(byId.getId());
                                System.out.println("Found Product: " + byId.getDetails() + ", Stock: " + item.getQuantity());
                            } catch (ProductNotFoundException e) {
                                System.out.println("Found Product: " + byId.getDetails());
                            }
                        } else {
                            List<Product> matches = inventoryService.searchProductsByName(query);
                            if (matches.isEmpty()) {
                                System.out.println("No products found matching: " + query);
                            } else {
                                System.out.println("Matches found (" + matches.size() + "):");
                                for (Product p : matches) {
                                    System.out.println(" - " + p.getDetails());
                                }
                            }
                        }
                        break;
                    }

                    case 4: {
                        System.out.println("\n--- Update Stock ---");
                        System.out.print("Enter Product ID: ");
                        String productId = scanner.nextLine().trim();

                        System.out.print("Enter Stock Change (+ to add, - to deduct): ");
                        int change = Integer.parseInt(scanner.nextLine().trim());

                        inventoryService.updateStock(productId, change);
                        break;
                    }

                    case 5: {
                        System.out.println("\n--- Add Supplier ---");
                        System.out.print("Enter Supplier ID: ");
                        String id = scanner.nextLine().trim();

                        System.out.print("Enter Supplier Name: ");
                        String name = scanner.nextLine().trim();

                        System.out.print("Enter Description: ");
                        String desc = scanner.nextLine().trim();

                        System.out.print("Enter Contact Number: ");
                        String contact = scanner.nextLine().trim();

                        System.out.print("Enter Address: ");
                        String address = scanner.nextLine().trim();

                        Supplier supplier = new Supplier(id, name, desc, contact, address);
                        inventoryService.addSupplier(supplier);
                        break;
                    }

                    case 6: {
                        inventoryService.viewSuppliers();
                        break;
                    }

                    case 7: {
                        System.out.println("\n--- Record Transaction ---");
                        System.out.print("Enter Transaction ID: ");
                        String tid = scanner.nextLine().trim();

                        System.out.print("Enter Product ID: ");
                        String pid = scanner.nextLine().trim();

                        Product product = inventoryService.getProduct(pid);

                        System.out.print("Enter Type (PURCHASE / SALE): ");
                        String type = scanner.nextLine().trim();

                        System.out.print("Enter Quantity: ");
                        int qty = Integer.parseInt(scanner.nextLine().trim());

                        Transaction transaction = new Transaction(tid, type, product, qty);
                        inventoryService.recordTransaction(transaction);
                        break;
                    }

                    case 8: {
                        inventoryService.viewTransactions();
                        break;
                    }

                    case 9: {
                        System.out.println("\n--- Sort Products ---");
                        System.out.println("1. Sort by Price (Low to High)");
                        System.out.println("2. Sort by Price (High to Low)");
                        System.out.println("3. Sort by Quantity (Low to High)");
                        System.out.println("4. Sort by Quantity (High to Low)");
                        System.out.print("Select sorting option: ");
                        int sortChoice = Integer.parseInt(scanner.nextLine().trim());

                        switch (sortChoice) {
                            case 1:
                                System.out.println("\n--- Products Sorted by Price (Ascending) ---");
                                for (Product p : inventoryService.sortProductsByPrice(true)) {
                                    System.out.println(p.getDetails());
                                }
                                break;
                            case 2:
                                System.out.println("\n--- Products Sorted by Price (Descending) ---");
                                for (Product p : inventoryService.sortProductsByPrice(false)) {
                                    System.out.println(p.getDetails());
                                }
                                break;
                            case 3:
                                System.out.println("\n--- Products Sorted by Stock Quantity (Ascending) ---");
                                for (InventoryItem item : inventoryService.sortProductsByQuantity(true)) {
                                    System.out.println(item.getProduct().getName() + " -> Quantity: " + item.getQuantity());
                                }
                                break;
                            case 4:
                                System.out.println("\n--- Products Sorted by Stock Quantity (Descending) ---");
                                for (InventoryItem item : inventoryService.sortProductsByQuantity(false)) {
                                    System.out.println(item.getProduct().getName() + " -> Quantity: " + item.getQuantity());
                                }
                                break;
                            default:
                                System.out.println("Invalid sort choice.");
                        }
                        break;
                    }

                    case 10: {
                        System.out.println("\n--- Save Inventory Data to JSON ---");
                        InventoryData data = inventoryService.getInventoryData();
                        JsonUtil.saveInventory(data);
                        break;
                    }

                    case 11: {
                        System.out.println("\n--- Load Inventory Data from JSON ---");
                        InventoryData loaded = JsonUtil.loadInventory();
                        inventoryService.loadInventoryData(loaded);
                        break;
                    }

                    case 12: {
                        System.out.println("\n--- Concurrent Stock Update Demonstration ---");
                        if (inventoryService.getAllStockItems().isEmpty()) {
                            System.out.println("No stock items available to update.");
                            break;
                        }

                        ExecutorService executor = Executors.newFixedThreadPool(4);
                        List<Future<Integer>> futures = new ArrayList<>();

                        System.out.println("Submitting concurrent stock increment tasks (Callable) across pool...");
                        for (InventoryItem item : inventoryService.getAllStockItems()) {
                            StockUpdateTask task = new StockUpdateTask(item, 5);
                            futures.add(executor.submit(task));
                        }

                        // Await completion
                        for (Future<Integer> f : futures) {
                            try {
                                f.get();
                            } catch (InterruptedException | ExecutionException e) {
                                System.err.println("Concurrent task execution error: " + e.getMessage());
                            }
                        }

                        executor.shutdown();
                        System.out.println("All concurrent stock updates completed successfully!");
                        break;
                    }

                    case 13: {
                        JVMInfo.displayJVMInfo();
                        break;
                    }

                    case 0: {
                        System.out.println("Exiting Inventory Management System. Goodbye!");
                        scanner.close();
                        return;
                    }

                    default:
                        System.out.println("Invalid option! Please enter a choice between 0 and 13.");
                }
            } catch (InvalidStockException | InsufficientStockException | ProductNotFoundException e) {
                System.out.println("Business Logic Error: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Validation Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void seedInitialData(InventoryService service) {
        try {
            Product p1 = new Product("P101", "Gaming Laptop", "15.6-inch RTX 4060 16GB RAM", 1199.99, "Electronics");
            Product p2 = new Product("P102", "Wireless Mouse", "Ergonomic 2.4GHz optical mouse", 29.99, "Accessories");
            Product p3 = new Product("P103", "Mechanical Keyboard", "RGB Tenkeyless Mechanical Keyboard", 89.99, "Accessories");

            service.addProduct(p1, 20);
            service.addProduct(p2, 100);
            service.addProduct(p3, 45);

            Supplier s1 = new Supplier("S201", "TechDistro Corp", "Authorized Computer Hardware Distributor", "+1-800-555-0199", "100 Silicon Blvd, CA");
            Supplier s2 = new Supplier("S202", "Peripheral Plus", "Quality Computer Accessories Supplier", "+1-800-555-0144", "456 Market St, NY");

            service.addSupplier(s1);
            service.addSupplier(s2);
        } catch (Exception e) {
            System.err.println("Warning: failed to seed initial data: " + e.getMessage());
        }
    }
}
