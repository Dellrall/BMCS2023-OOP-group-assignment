/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.RentalModule;

import hillclimmer.DatabaseModule.RentalDAO;
import hillclimmer.DatabaseModule.Manager;
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
}
