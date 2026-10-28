package com.inventorymanagementsystem.model;

public class Supplier extends Asset {

    private String contactNumber;
    private String address;

    // Default constructor required by Jackson
    public Supplier() {
        super();
    }

    public Supplier(String id, String name, String description, String contactNumber, String address) {
        super(id, name, description);
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getDetails() {
        return "Supplier ID: " + id
                + ", Name: " + name
                + ", Contact: " + contactNumber
                + ", Address: " + address
                + ", Description: " + description;
    }
}
