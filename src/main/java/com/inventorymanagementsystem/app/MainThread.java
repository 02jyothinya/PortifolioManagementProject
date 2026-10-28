package com.inventorymanagementsystem.app;

import com.inventorymanagementsystem.concurrent.StockUpdateTask;
import com.inventorymanagementsystem.concurrent.TransactionProcessingTask;
import com.inventorymanagementsystem.model.InventoryItem;
import com.inventorymanagementsystem.model.Product;
import com.inventorymanagementsystem.model.Transaction;
import com.inventorymanagementsystem.service.InventoryService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainThread {

    public static void main(String[] args) {
        System.out.println("========== MULTI-THREAD CONCURRENCY DEMO ==========");

        InventoryService service = new InventoryService();
        Product sampleProduct = new Product("P999", "Smart Monitor 4K", "32-inch 4K UHD display", 399.99, "Monitors");

        try {
            service.addProduct(sampleProduct, 50);
            InventoryItem item = service.getInventoryItem("P999");

            System.out.println("Initial Stock: " + item.getQuantity());

            // 1. Thread and Runnable demonstration
            Transaction purchaseTx = new Transaction("TX-TH1", "PURCHASE", sampleProduct, 10);
            Thread thread1 = new Thread(new TransactionProcessingTask(service, purchaseTx), "Worker-Thread-1");

            Transaction saleTx = new Transaction("TX-TH2", "SALE", sampleProduct, 5);
            Thread thread2 = new Thread(new TransactionProcessingTask(service, saleTx), "Worker-Thread-2");

            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            System.out.println("Stock after Thread 1 & 2: " + item.getQuantity());

            // 2. ExecutorService and Callable demonstration
            System.out.println("\nSubmitting Callable task to ExecutorService...");
            ExecutorService executor = Executors.newFixedThreadPool(2);
            StockUpdateTask callableTask = new StockUpdateTask(item, 15);

            Future<Integer> future = executor.submit(callableTask);
            Integer result = future.get();

            System.out.println("Callable returned new stock value: " + result);
            executor.shutdown();

            System.out.println("Final Stock: " + item.getQuantity());
            System.out.println("Multi-thread concurrency demonstration finished successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
