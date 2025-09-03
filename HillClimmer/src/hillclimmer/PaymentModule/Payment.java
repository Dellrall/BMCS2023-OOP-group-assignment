/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.PaymentModule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Payment class for managing payment transactions.
 *
 * @author las
 */
public abstract class Payment {
    protected String paymentID;
    protected double totalAmount;
    protected String paymentMethod;
    protected String paymentStatus;
    protected String timestamp;
    protected String customerID;
    protected String referenceNumber;
    protected String paymentSlip;

    public Payment(String paymentID, double totalAmount, String paymentMethod, String paymentStatus, String timestamp, String customerID) {
        this.paymentID = paymentID;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.timestamp = timestamp;
        this.customerID = customerID;
        this.referenceNumber = generateReferenceNumber();
        this.paymentSlip = "";
    }

    public String getPaymentID() {
        return paymentID;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public String getPaymentSlip() {
        return paymentSlip;
    }

    // Abstract method for processing payment
    public abstract void processPayment();

    public void refundPayment() {
        // Simulate refund
        this.paymentStatus = "Refunded";
        System.out.println("Refund processed for ID: " + paymentID);
    }

    public String getPaymentDetails() {
        return "Payment ID: " + paymentID + "\n" +
               "Reference Number: " + referenceNumber + "\n" +
               "Amount: RM" + String.format("%.2f", totalAmount) + "\n" +
               "Method: " + paymentMethod + "\n" +
               "Status: " + paymentStatus + "\n" +
               "Timestamp: " + timestamp + "\n" +
               "Customer ID: " + customerID +
               (paymentSlip.isEmpty() ? "" : "\nPayment Slip: " + paymentSlip);
    }

    public void updateStatus(String newStatus) {
        this.paymentStatus = newStatus;
    }

    private String generateReferenceNumber() {
        return "REF" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    // Factory method to create appropriate payment type
    public static Payment createPayment(String paymentType, String paymentID, double totalAmount,
                                      String timestamp, String customerID) {
        switch (paymentType.toLowerCase()) {
            case "credit card":
                return new CreditCardPayment(paymentID, totalAmount, timestamp, customerID);
            case "online banking":
                return new OnlineBankingPayment(paymentID, totalAmount, timestamp, customerID);
            case "cash":
                return new CashPayment(paymentID, totalAmount, timestamp, customerID);
            default:
                return new CreditCardPayment(paymentID, totalAmount, timestamp, customerID);
        }
    }
}
