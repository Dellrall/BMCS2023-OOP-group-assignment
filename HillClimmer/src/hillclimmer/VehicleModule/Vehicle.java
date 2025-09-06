/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.VehicleModule;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author las
 */
public class Vehicle {
    private String vehicleID;
    private String vehicleType;
    private String vehicleCon;
    private String vehicleModel;
    private double modelPricing;
    private boolean available;

    public Vehicle(String vehicleID, String vehicleType, String vehicleModel, double modelPricing, String vehicleCon, boolean available) {
        this.vehicleID = vehicleID;
        this.vehicleType = vehicleType;
        this.vehicleModel = vehicleModel;
        this.modelPricing = modelPricing;
        this.vehicleCon = vehicleCon;
        this.available = available;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public double getModelPricing() {
        return modelPricing;
    }

    public void setModelPricing(double modelPricing) {
        this.modelPricing = modelPricing;
    }

    public String getVehicleCon() {
        return vehicleCon;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public static List<Vehicle> getAvailable(List<Vehicle> vehicles) {
        return vehicles.stream().filter(Vehicle::isAvailable).collect(Collectors.toList());
    }

    public void displayPricing() {
        System.out.println("Pricing for " + vehicleModel + ": " + modelPricing);
    }

    public void returnVehicle() {
        this.available = true;
    }

    public void updateCondition(String newCondition) {
        this.vehicleCon = newCondition;
    }

    public void getVehicleDetails() {
        System.out.println("Vehicle ID: " + vehicleID);
        System.out.println("Type: " + vehicleType);
        System.out.println("Model: " + vehicleModel);
        System.out.println("Pricing: " + modelPricing);
        System.out.println("Condition: " + vehicleCon);
        System.out.println("Available: " + available);
    }
}
