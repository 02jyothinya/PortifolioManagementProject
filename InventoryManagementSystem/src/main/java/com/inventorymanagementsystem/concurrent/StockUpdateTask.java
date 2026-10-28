package com.inventorymanagementsystem.concurrent;

import com.inventorymanagementsystem.model.InventoryItem;
import java.util.concurrent.Callable;

public class StockUpdateTask implements Callable<Integer> {

    private final InventoryItem item;
    private final int quantityChange;

    public StockUpdateTask(InventoryItem item, int quantityChange) {
        this.item = item;
        this.quantityChange = quantityChange;
    }

    @Override
    public Integer call() throws Exception {
        String threadName = Thread.currentThread().getName();
        String productName = item.getProduct() != null ? item.getProduct().getName() : "Unknown";

        System.out.println("[Thread: " + threadName + "] Starting stock update for: " + productName);
        int oldQuantity = item.getQuantity();

        // Synchronized block on item to ensure thread-safety
        synchronized (item) {
            if (quantityChange >= 0) {
                item.addStock(quantityChange);
            } else {
                item.deductStock(Math.abs(quantityChange));
            }
        }

        int newQuantity = item.getQuantity();
        System.out.println("[Thread: " + threadName + "] Updated " + productName
                + " stock from " + oldQuantity + " -> " + newQuantity);

        return newQuantity;
    }
}
