package com.inventorymanagementsystem.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.inventorymanagementsystem.model.InventoryData;

import java.io.File;
import java.io.IOException;

public class JsonUtil {

    private static final String FILE_NAME = "inventory.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    // Save complete inventory data to JSON
    public static void saveInventory(InventoryData data) {
        try {
            mapper.writeValue(new File(FILE_NAME), data);
            System.out.println("Inventory data saved successfully to " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error saving inventory data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Load complete inventory data from JSON
    public static InventoryData loadInventory() {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                System.out.println(FILE_NAME + " not found. Starting with empty inventory.");
                return new InventoryData();
            }
            InventoryData data = mapper.readValue(file, InventoryData.class);
            System.out.println("Inventory data loaded successfully from " + FILE_NAME);
            return data;
        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
            e.printStackTrace();
            return new InventoryData();
        }
    }
}
