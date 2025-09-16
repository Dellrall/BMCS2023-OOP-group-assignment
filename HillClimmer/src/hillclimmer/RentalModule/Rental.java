/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.RentalModule;

import java.time.LocalDate;

/**
 *
 * @author las
 */
public class Rental {
    private int rentalId;
    private int customerId;
    private int vehicleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalCost;
    private String paymentStatus; // "Paid", "Pending", "Unpaid"
    private String status; // "Active", "Upcoming", "End", "Cancelled"
    
    public Rental(int rentalId, int customerId, int vehicleId, LocalDate startDate, LocalDate endDate, double totalCost) {
        this.rentalId = rentalId;
        this.customerId = customerId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.paymentStatus = "Unpaid"; // Default status
        this.status = "Pending"; // Default status for new rentals - waiting for payment
    }
    
    // Getters
    public int getRentalId() {
        return rentalId;
    }
    
    public int getCustomerId() {
        return customerId;
    }
    
    public int getVehicleId() {
        return vehicleId;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public double getTotalCost() {
        return totalCost;
    }
    
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    public String getStatus() {
        return status;
    }
    
    // Setters
    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Rental{" +
                "rentalId=" + rentalId +
                ", customerId=" + customerId +
                ", vehicleId=" + vehicleId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalCost=" + totalCost +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
