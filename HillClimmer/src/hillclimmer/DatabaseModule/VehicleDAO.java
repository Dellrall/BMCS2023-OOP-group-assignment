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
        super(System.getProperty("user.dir") + "/data/vehicles.csv"); // File for storing vehicle data
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
    
    @Override
    protected Vehicle generateNewId(Vehicle vehicle, java.util.List<Vehicle> existingVehicles) {
        // Generate new vehicle ID based on existing vehicles with proper prefixes
        String prefix;
        switch (vehicle.getVehicleType()) {
            case "Mountain Bike":
                prefix = "MB";
                break;
            case "Dirt Bike":
                prefix = "DB";
                break;
            case "Buggy":
                prefix = "BG";
                break;
            case "Crossover":
                prefix = "CR";
                break;
            default:
                prefix = "V"; // Fallback for unknown types
        }

        // Find the highest ID number for this vehicle type
        int maxId = existingVehicles.stream()
                .filter(v -> v.getVehicleID().startsWith(prefix))
                .mapToInt(v -> {
                    try {
                        return Integer.parseInt(v.getVehicleID().substring(2));
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);
        
        String newVehicleId = prefix + String.format("%03d", maxId + 1);
        
        // Create new vehicle with generated ID based on type
        switch (vehicle.getVehicleType()) {
            case "Mountain Bike":
                return new MountainBike(newVehicleId, vehicle.getVehicleModel(), vehicle.getModelPricing(), 
                                      vehicle.getVehicleCon(), vehicle.isAvailable());
            case "Dirt Bike":
                return new DirtBike(newVehicleId, vehicle.getVehicleModel(), vehicle.getModelPricing(), 
                                  vehicle.getVehicleCon(), vehicle.isAvailable());
            case "Buggy":
                return new Buggy(newVehicleId, vehicle.getVehicleModel(), vehicle.getModelPricing(), 
                               vehicle.getVehicleCon(), vehicle.isAvailable());
            case "Crossover":
                return new Crossover(newVehicleId, vehicle.getVehicleModel(), vehicle.getModelPricing(), 
                                   vehicle.getVehicleCon(), vehicle.isAvailable());
            default:
                return new Vehicle(newVehicleId, vehicle.getVehicleType(), vehicle.getVehicleModel(), 
                                 vehicle.getModelPricing(), vehicle.getVehicleCon(), vehicle.isAvailable());
        }
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
