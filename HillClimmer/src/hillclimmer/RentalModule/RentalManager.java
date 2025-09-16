/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.RentalModule;

import hillclimmer.DatabaseModule.RentalDAO;
import hillclimmer.DatabaseModule.Manager;
import hillclimmer.DatabaseModule.PaymentDAO;
import hillclimmer.PaymentModule.Payment;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author las
 */
public class RentalManager {
    private RentalDAO rentalDAO;
    private Manager authenticatedManager;

    // Constructor with Manager for authorization
    public RentalManager(Manager manager) {
        this.rentalDAO = new RentalDAO();
        this.authenticatedManager = manager;
    }

    // Legacy constructor for backward compatibility
    public RentalManager() {
        this.rentalDAO = new RentalDAO();
    }
    
    public void addRental(int customerId, int vehicleId, LocalDate startDate, LocalDate endDate, double totalCost) {
        if (hasPermission("add")) {
            Rental rental = new Rental(0, customerId, vehicleId, startDate, endDate, totalCost);
            rentalDAO.save(rental);
            System.out.println("✅ Rental added successfully by " + getManagerName());
        } else {
            System.out.println("❌ Insufficient permissions to add rental.");
        }
    }
    
    public void addRentalWithId(int rentalId, int customerId, int vehicleId, LocalDate startDate, LocalDate endDate, double totalCost) {
        if (hasPermission("add")) {
            Rental rental = new Rental(rentalId, customerId, vehicleId, startDate, endDate, totalCost);
            rentalDAO.save(rental);
            System.out.println("✅ Rental added successfully by " + getManagerName());
        } else {
            System.out.println("❌ Insufficient permissions to add rental.");
        }
    }
    
    public List<Rental> getAllRentals() {
        if (hasPermission("view")) {
            return rentalDAO.getAll();
        } else {
            System.out.println("❌ Insufficient permissions to view rentals.");
            return new java.util.ArrayList<>();
        }
    }
    
    public Rental getRentalById(int rentalId) {
        if (hasPermission("view")) {
            return rentalDAO.getById(rentalId);
        } else {
            System.out.println("❌ Insufficient permissions to view rental details.");
            return null;
        }
    }
    
    public void updateRental(Rental rental) {
        if (hasPermission("update")) {
            rentalDAO.update(rental);
            System.out.println("✅ Rental updated successfully by " + getManagerName());
        } else {
            System.out.println("❌ Insufficient permissions to update rental.");
        }
    }
    
    public void deleteRental(int rentalId) {
        if (hasPermission("remove")) {
            rentalDAO.delete(String.valueOf(rentalId));
            System.out.println("✅ Rental deleted successfully by " + getManagerName());
        } else {
            System.out.println("❌ Insufficient permissions to delete rental.");
        }
    }
    
    public List<Rental> getRentalsByCustomer(int customerId) {
        return rentalDAO.getByCustomerId(customerId);
    }
    
    public List<Rental> getRentalsByVehicle(int vehicleId) {
        return rentalDAO.getByVehicleId(vehicleId);
    }
    
    public double calculateTotalCost(LocalDate startDate, LocalDate endDate, double dailyRate) {
        long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
        return days * dailyRate;
    }

    // Authorization helper methods
    private boolean hasPermission(String action) {
        if (authenticatedManager == null) {
            // No authentication required for legacy usage
            return true;
        }
        return authenticatedManager.hasPermission(action);
    }

    private String getManagerName() {
        if (authenticatedManager != null) {
            return authenticatedManager.getName();
        }
        return "System";
    }

    // Get current authenticated manager
    public Manager getAuthenticatedManager() {
        return authenticatedManager;
    }

    // Check if manager is authenticated
    public boolean isAuthenticated() {
        return authenticatedManager != null;
    }

    /**
     * Find rentals that might be related to a payment by reference number
     * Since payments affect outstanding balances, this returns rentals for the customer
     * that made the payment and were in pending/unpaid status
     * @param referenceNumber The payment reference number
     * @param paymentDAO PaymentDAO instance to find the payment
     * @return List of potentially related rentals
     */
    public List<Rental> findRentalsByPaymentReference(String referenceNumber, PaymentDAO paymentDAO) {
        if (!hasPermission("view")) {
            System.out.println("❌ Insufficient permissions to search rentals.");
            return new java.util.ArrayList<>();
        }

        // Find the payment by reference number
        Payment payment = paymentDAO.getByReferenceNumber(referenceNumber);
        if (payment == null) {
            return new java.util.ArrayList<>();
        }

        // Extract customer ID from payment (format is "C001", "C002", etc.)
        String customerIdStr = payment.getCustomerID();
        if (!customerIdStr.startsWith("C")) {
            return new java.util.ArrayList<>();
        }

        try {
            int customerId = Integer.parseInt(customerIdStr.substring(1));
            // Return all rentals for this customer (both paid and unpaid)
            // The manager can then see which ones might have been affected
            return getRentalsByCustomer(customerId);
        } catch (NumberFormatException e) {
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Updates rental statuses based on current date:
     * - Upcoming: startDate > today (pre-booking)
     * - Active: startDate <= today and endDate >= today (ongoing)
     * - End: endDate < today (completed)
     */
    public void updateRentalStatuses() {
        List<Rental> allRentals = rentalDAO.getAll();
        LocalDate today = LocalDate.now();
        boolean hasUpdates = false;

        for (Rental rental : allRentals) {
            String currentStatus = rental.getStatus();
            String newStatus = currentStatus;

            if (rental.getStartDate().isAfter(today)) {
                newStatus = "Upcoming";
            } else if (rental.getEndDate().isBefore(today)) {
                newStatus = "End";
            } else {
                newStatus = "Active";
            }

            if (!currentStatus.equals(newStatus)) {
                rental.setStatus(newStatus);
                rentalDAO.update(rental);
                hasUpdates = true;
            }
        }

        if (hasUpdates) {
            System.out.println("✅ Rental statuses updated based on current date");
        }
    }
}
