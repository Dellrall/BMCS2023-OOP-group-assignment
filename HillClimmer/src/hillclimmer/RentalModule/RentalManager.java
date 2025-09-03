/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.RentalModule;

import hillclimmer.DatabaseModule.RentalDAO;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author las
 */
public class RentalManager {
    private RentalDAO rentalDAO;
    
    public RentalManager() {
        this.rentalDAO = new RentalDAO();
    }
    
    public void addRental(int customerId, int vehicleId, LocalDate startDate, LocalDate endDate, double totalCost) {
        Rental rental = new Rental(0, customerId, vehicleId, startDate, endDate, totalCost);
        rentalDAO.save(rental);
    }
    
    public List<Rental> getAllRentals() {
        return rentalDAO.getAll();
    }
    
    public Rental getRentalById(int rentalId) {
        return rentalDAO.getById(rentalId);
    }
    
    public void updateRental(Rental rental) {
        rentalDAO.update(rental);
    }
    
    public void deleteRental(int rentalId) {
        rentalDAO.delete(String.valueOf(rentalId));
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
}
