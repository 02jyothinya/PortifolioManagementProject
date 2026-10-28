package com.inventorymanagementsystem.concurrent;

import com.inventorymanagementsystem.model.Transaction;
import com.inventorymanagementsystem.service.InventoryService;

public class TransactionProcessingTask implements Runnable {

    private final InventoryService inventoryService;
    private final Transaction transaction;

    public TransactionProcessingTask(InventoryService inventoryService, Transaction transaction) {
        this.inventoryService = inventoryService;
        this.transaction = transaction;
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();
        System.out.println("[Thread: " + threadName + "] Processing transaction: " + transaction.getTransactionId());
        try {
            inventoryService.recordTransaction(transaction);
            System.out.println("[Thread: " + threadName + "] Successfully processed transaction: " + transaction.getTransactionId());
        } catch (Exception e) {
            System.err.println("[Thread: " + threadName + "] Failed to process transaction "
                    + transaction.getTransactionId() + ": " + e.getMessage());
        }
    }
}
