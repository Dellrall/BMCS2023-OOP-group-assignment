/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.DatabaseModule;

import hillclimmer.VehicleModule.*;

/**
 * VehicleDAO class extending DataAccessObject for Vehicle data management.
 *
 * @author las
 */
public class VehicleDAO extends DataAccessObject<Vehicle> {

    public VehicleDAO() {
        super("vehicles.csv"); // File for storing vehicle data
    }

    @Override
    protected String objectToCSV(Vehicle vehicle) {
        return vehicle.getVehicleID() + "," +
               vehicle.getVehicleType() + "," +
               vehicle.getVehicleModel() + "," +
               vehicle.getModelPricing() + "," +
               vehicle.getVehicleCon() + "," +
               vehicle.isAvailable();
    }

    @Override
    protected Vehicle csvToObject(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length == 6) {
            String vehicleID = parts[0];
            String vehicleType = parts[1];
            String vehicleModel = parts[2];
            double modelPricing = Double.parseDouble(parts[3]);
            String vehicleCon = parts[4];
            boolean available = Boolean.parseBoolean(parts[5]);

            // Create Vehicle based on type
            switch (vehicleType) {
                case "Mountain Bike":
                    return new MountainBike(vehicleID, vehicleModel, modelPricing, vehicleCon, available);
                case "Dirt Bike":
                    return new DirtBike(vehicleID, vehicleModel, modelPricing, vehicleCon, available);
                case "Buggy":
                    return new Buggy(vehicleID, vehicleModel, modelPricing, vehicleCon, available);
                case "Crossover":
                    return new Crossover(vehicleID, vehicleModel, modelPricing, vehicleCon, available);
                default:
                    return new Vehicle(vehicleID, vehicleType, vehicleModel, modelPricing, vehicleCon, available);
            }
        }
        return null;
    }

    @Override
    protected String getId(Vehicle vehicle) {
        return vehicle.getVehicleID();
    }

    // Additional methods specific to VehicleDAO
    public java.util.List<Vehicle> getAvailableVehicles() {
        java.util.List<Vehicle> all = loadAll();
        java.util.List<Vehicle> available = new java.util.ArrayList<>();
        for (Vehicle v : all) {
            if (v.isAvailable()) {
                available.add(v);
            }
        }
        return available;
    }

    public void updateVehicleCondition(String vehicleID, String newCondition) {
        Vehicle v = load(vehicleID);
        if (v != null) {
            v.updateCondition(newCondition);
            update(v);
        }
    }

    public void returnVehicle(String vehicleID) {
        Vehicle v = load(vehicleID);
        if (v != null) {
            v.returnVehicle();
            update(v);
        }
    }
}
