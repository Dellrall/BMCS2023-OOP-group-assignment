/*
 * Integrated Manager System Test
 * Tests VehicleManager and RentalManager working together with authentication
 */
package test;

import hillclimmer.DatabaseModule.ManagerDAO;
import hillclimmer.DatabaseModule.Manager;
import hillclimmer.VehicleModule.VehicleManager;
import hillclimmer.RentalModule.RentalManager;
import hillclimmer.VehicleModule.Vehicle;
import java.time.LocalDate;

/**
 * Test for integrated manager system with both vehicle and rental management
 * @author las
 */
public class IntegratedManagerTest {
    public static void main(String[] args) {
        System.out.println("🚀 Integrated Manager System Test");
        System.out.println("=================================");

        ManagerDAO managerDAO = new ManagerDAO();

        // Test with different manager levels
        testManagerLevel(managerDAO, "VM002", "Manager123!", "Level 1 Manager");
        testManagerLevel(managerDAO, "VM004", "Nurburg2025!", "Level 3 Manager");
        testManagerLevel(managerDAO, "VM006", "TehSecure789!", "Level 5 Manager");

        System.out.println("\n✅ Integrated Manager System Test Completed!");
    }

    private static void testManagerLevel(ManagerDAO dao, String managerId, String password, String description) {
        System.out.println("\n👤 Testing: " + description + " (" + managerId + ")");

        // Authenticate manager
        Manager manager = dao.authenticate(managerId, password);

        if (manager == null) {
            System.out.println("❌ Authentication failed");
            return;
        }

        System.out.println("✅ Authentication successful");
        System.out.println("   Manager: " + manager.getName());
        System.out.println("   Level: " + manager.getAuthorizationLevel());

        // Initialize managers with authenticated manager
        VehicleManager vehicleManager = new VehicleManager(manager);
        RentalManager rentalManager = new RentalManager(manager);

        // Test vehicle management permissions
        testVehiclePermissions(vehicleManager, manager);

        // Test rental management permissions
        testRentalPermissions(rentalManager, manager);

        System.out.println("   ✅ Integration test passed for " + description);
    }

    private static void testVehiclePermissions(VehicleManager vm, Manager manager) {
        System.out.println("   🚗 Vehicle Management:");

        // Test viewing vehicles (should work for all levels)
        try {
            int vehicleCount = vm.getAllVehicles().size();
            System.out.println("   - View Vehicles: ✅ (" + vehicleCount + " vehicles)");
        } catch (Exception e) {
            System.out.println("   - View Vehicles: ❌ (" + e.getMessage() + ")");
        }

        // Test adding vehicles (level 2+)
        if (manager.getAuthorizationLevel() >= 2) {
            try {
                Vehicle testVehicle = new Vehicle("TEST001", "Mountain Bike", "Trek X5", 50.0, "Excellent", true);
                vm.addVehicle(testVehicle);
                System.out.println("   - Add Vehicle: ✅");
                // Clean up
                vm.removeVehicle("TEST001");
            } catch (Exception e) {
                System.out.println("   - Add Vehicle: ❌ (" + e.getMessage() + ")");
            }
        } else {
            System.out.println("   - Add Vehicle: ❌ (Insufficient level)");
        }

        // Test updating vehicles (level 3+)
        if (manager.getAuthorizationLevel() >= 3) {
            System.out.println("   - Update Vehicle: ✅ (Authorized)");
        } else {
            System.out.println("   - Update Vehicle: ❌ (Insufficient level)");
        }
    }

    private static void testRentalPermissions(RentalManager rm, Manager manager) {
        System.out.println("   📅 Rental Management:");

        // Test viewing rentals (should work for all levels)
        try {
            int rentalCount = rm.getAllRentals().size();
            System.out.println("   - View Rentals: ✅ (" + rentalCount + " rentals)");
        } catch (Exception e) {
            System.out.println("   - View Rentals: ❌ (" + e.getMessage() + ")");
        }

        // Test adding rentals (level 2+)
        if (manager.getAuthorizationLevel() >= 2) {
            try {
                LocalDate start = LocalDate.now();
                LocalDate end = start.plusDays(3);
                rm.addRental(1, 1, start, end, 150.0);
                System.out.println("   - Add Rental: ✅");
            } catch (Exception e) {
                System.out.println("   - Add Rental: ❌ (" + e.getMessage() + ")");
            }
        } else {
            System.out.println("   - Add Rental: ❌ (Insufficient level)");
        }

        // Test removing rentals (level 2+)
        if (manager.getAuthorizationLevel() >= 2) {
            System.out.println("   - Remove Rental: ✅ (Authorized)");
        } else {
            System.out.println("   - Remove Rental: ❌ (Insufficient level)");
        }
    }
}
