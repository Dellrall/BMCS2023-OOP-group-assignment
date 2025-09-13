/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.DatabaseModule;

import hillclimmer.RentalModule.Rental;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author las
 */
public class RentalDAO extends DataAccessObject<Rental> {
    
    public RentalDAO() {
        super(System.getProperty("user.dir") + "/data/rentals.csv");
    }
    
    @Override
    protected String objectToCSV(Rental rental) {
        return rental.getRentalId() + "," +
               rental.getCustomerId() + "," +
               rental.getVehicleId() + "," +
               rental.getStartDate() + "," +
               rental.getEndDate() + "," +
               rental.getTotalCost() + "," +
               rental.getPaymentStatus();
    }
    
    @Override
    protected Rental csvToObject(String csvLine) {
        try {
            String[] parts = csvLine.split(",");
            if (parts.length >= 7) {
                int rentalId = Integer.parseInt(parts[0]);
                int customerId = Integer.parseInt(parts[1]);
                int vehicleId = Integer.parseInt(parts[2]);
                LocalDate startDate = LocalDate.parse(parts[3]);
                LocalDate endDate = LocalDate.parse(parts[4]);
                double totalCost = Double.parseDouble(parts[5]);
                String paymentStatus = parts.length > 6 ? parts[6] : "Unpaid"; // Default to Unpaid for backward compatibility
                Rental rental = new Rental(rentalId, customerId, vehicleId, startDate, endDate, totalCost);
                rental.setPaymentStatus(paymentStatus);
                return rental;
            }
        } catch (Exception e) {
            System.err.println("Warning: Skipping corrupted rental CSV line. Error: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    protected String getId(Rental rental) {
        return String.valueOf(rental.getRentalId());
    }
    
    public List<Rental> getAll() {
        return loadAll();
    }
    
    public Rental getById(int rentalId) {
        return load(String.valueOf(rentalId));
    }
    
    public List<Rental> getByCustomerId(int customerId) {
        return loadAll().stream()
                .filter(rental -> rental.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }
    
    public List<Rental> getByVehicleId(int vehicleId) {
        return loadAll().stream()
                .filter(rental -> rental.getVehicleId() == vehicleId)
                .collect(Collectors.toList());
    }
    
    @Override
    protected Rental generateNewId(Rental rental, List<Rental> existingRentals) {
        // Generate new rental ID based on existing rentals
        int maxId = existingRentals.stream()
                .mapToInt(Rental::getRentalId)
                .max()
                .orElse(0);
        
        // Create new rental with generated ID, preserving payment status
        Rental newRental = new Rental(maxId + 1, rental.getCustomerId(), rental.getVehicleId(), 
                         rental.getStartDate(), rental.getEndDate(), rental.getTotalCost());
        newRental.setPaymentStatus(rental.getPaymentStatus());
        return newRental;
    }
}
