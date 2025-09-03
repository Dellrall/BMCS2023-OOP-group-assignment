/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.PaymentModule;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author las
 */
public class Invoice {
    private String invoiceID;
    private String vehicleID;
    private String customerID;
    private String issueDate;
    private String dueDate;
    private List<String> itemList;
    private double discount;
    private double totalAmount;
    
    public Invoice() {
        this.itemList = new ArrayList<>();
        this.discount = 0.0;
        this.totalAmount = 0.0;
    }
    
    public Invoice(String invoiceID, String vehicleID, String customerID, String issueDate, String dueDate) {
        this();
        this.invoiceID = invoiceID;
        this.vehicleID = vehicleID;
        this.customerID = customerID;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
    }
    
    // Getters
    public String getInvoiceID() {
        return invoiceID;
    }
    
    public String getVehicleID() {
        return vehicleID;
    }
    
    public String getCustomerID() {
        return customerID;
    }
    
    public String getIssueDate() {
        return issueDate;
    }
    
    public String getDueDate() {
        return dueDate;
    }
    
    public List<String> getItemList() {
        return itemList;
    }
    
    public double getDiscount() {
        return discount;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    // Setters
    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }
    
    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }
    
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
    
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
    
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
    
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    // Methods
    public void addItem(String itemName, double price) {
        itemList.add(itemName + ":" + price);
        totalAmount += price;
    }
    
    public void applyDiscount(double discount) {
        this.discount = discount;
        totalAmount -= discount;
        if (totalAmount < 0) totalAmount = 0;
    }
    
    public double calculateTotal() {
        return totalAmount;
    }
    
    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceID='" + invoiceID + '\'' +
                ", vehicleID='" + vehicleID + '\'' +
                ", customerID='" + customerID + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", itemList=" + itemList +
                ", discount=" + discount +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
