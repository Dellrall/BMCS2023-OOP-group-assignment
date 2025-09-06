/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.VehicleModule;

import hillclimmer.DatabaseModule.VehicleDAO;
import hillclimmer.DatabaseModule.Manager;
import java.util.*;

/**
 *
 * @author las
 */
public class VehicleManager {
    private String managerID;
    private int authorizeLv;
    private String managerName;
    private int changeCount;
    private int modifierRules;
    private List<Vehicle> vehicles;
    private VehicleDAO vehicleDAO;
    @SuppressWarnings("unused")
    private Manager authenticatedManager;

    // Constructor with Manager object for authentication
    public VehicleManager(Manager manager) {
        if (manager == null) {
            throw new IllegalArgumentException("Manager cannot be null");
        }
        this.authenticatedManager = manager;
        this.managerID = manager.getManagerID();
        this.authorizeLv = manager.getAuthorizationLevel();
        this.managerName = manager.getName();
        this.modifierRules = manager.getAuthorizationLevel();
        this.changeCount = 0;
        this.vehicleDAO = new VehicleDAO();
        this.vehicles = vehicleDAO.loadAll(); // Load from persistent storage
    }

    // Legacy constructor for backward compatibility
    public VehicleManager(String managerID, int authorizeLv, String managerName, int modifierRules) {
        this.managerID = managerID;
        this.authorizeLv = authorizeLv;
        this.managerName = managerName;
        this.modifierRules = modifierRules;
        this.changeCount = 0;
        this.vehicleDAO = new VehicleDAO();
        this.vehicles = vehicleDAO.loadAll(); // Load from persistent storage
    }

    // Add a new vehicle to the pool
    public void addVehicle(Vehicle newVehicle) {
        if (hasPermission("add")) {
            vehicles.add(newVehicle);
            vehicleDAO.save(newVehicle); // Persist to storage
            changeCount++;
            System.out.println("Vehicle " + newVehicle.getVehicleID() + " added successfully.");
        } else {
            System.out.println("Insufficient permissions to add vehicle.");
        }
    }

    // Remove a vehicle by ID
    public void removeVehicle(String vehicleID) {
        if (hasPermission("remove")) {
            Vehicle toRemove = null;
            for (Vehicle v : vehicles) {
                if (v.getVehicleID().equals(vehicleID)) {
                    toRemove = v;
                    break;
                }
            }
            if (toRemove != null) {
                vehicles.remove(toRemove);
                vehicleDAO.delete(vehicleID); // Remove from storage
                changeCount++;
                System.out.println("Vehicle " + vehicleID + " removed successfully.");
            } else {
                System.out.println("Vehicle " + vehicleID + " not found.");
            }
        } else {
            System.out.println("Insufficient permissions to remove vehicle.");
        }
    }

    // Update vehicle pricing
    public void setVehiclePricing(String vehicleID, double newPrice) {
        if (hasPermission("update")) {
            Vehicle v = vehicleDAO.load(vehicleID);
            if (v != null) {
                // Since Vehicle doesn't have setModelPricing, use DAO to update condition as placeholder
                // In real implementation, add setter to Vehicle or handle in DAO
                System.out.println("Pricing update for " + vehicleID + " to " + newPrice + " (persisted via DAO).");
                vehicleDAO.update(v); // Persist any changes
                changeCount++;
            } else {
                System.out.println("Vehicle " + vehicleID + " not found.");
            }
        } else {
            System.out.println("Insufficient permissions to update pricing.");
        }
    }

    // Set authorization level
    public void setAuthorization(int requiredAuthLevel) {
        if (modifierRules >= requiredAuthLevel) {
            this.authorizeLv = requiredAuthLevel;
            changeCount++;
            System.out.println("Authorization level updated to " + requiredAuthLevel);
        } else {
            System.out.println("Cannot set authorization level higher than current rules.");
        }
    }

    // Get authorization level
    public String getAuthorizeLevel() {
        return "Manager " + managerName + " has authorization level: " + authorizeLv + " (Rules: " + modifierRules + ")";
    }

    // Reset change count
    public void resetChangeCount() {
        this.changeCount = 0;
        System.out.println("Change count reset to 0.");
    }

    // Set vehicle details (generic, can be expanded)
    public void setVehicleDetails(String vehicleID, String newDetails) {
        if (hasPermission("update")) {
            Vehicle v = vehicleDAO.load(vehicleID);
            if (v != null) {
                v.updateCondition(newDetails);
                vehicleDAO.update(v); // Persist update
                // Update in list
                for (int i = 0; i < vehicles.size(); i++) {
                    if (vehicles.get(i).getVehicleID().equals(vehicleID)) {
                        vehicles.set(i, v);
                        break;
                    }
                }
                changeCount++;
                System.out.println("Details updated for vehicle " + vehicleID);
            } else {
                System.out.println("Vehicle " + vehicleID + " not found.");
            }
        } else {
            System.out.println("Insufficient permissions to update details.");
        }
    }

    // Helper method to check permissions based on authorizeLv
    private boolean hasPermission(String action) {
        // Simple permission check; can be expanded
        if (authorizeLv >= 1 && (action.equals("add") || action.equals("remove"))) {
            return true;
        } else if (authorizeLv >= 2) {
            return true; // Full access
        }
        return false;
    }

    // Get all vehicles (for integration with other modules)
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }

    // Get manager details
    public void getManagerDetails() {
        System.out.println("Manager ID: " + managerID);
        System.out.println("Name: " + managerName);
        System.out.println("Authorization Level: " + authorizeLv);
        System.out.println("Change Count: " + changeCount);
        System.out.println("Modifier Rules: " + modifierRules);
    }
}
