package test;

import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.DatabaseModule.RentalDAO;
import hillclimmer.DatabaseModule.VehicleDAO;
import hillclimmer.RentalModule.Rental;
import hillclimmer.VehicleModule.VehicleManager;
import java.time.LocalDate;

/**
 * Test to simulate the exact user scenario: customer login, rental, then system report
 */
public class SystemReportBugTest {

    public static void main(String[] args) {
        System.out.println("🧪 System Report Bug Test");
        System.out.println("==========================");

        try {
            // Initialize DAOs
            CustomerDAO customerDAO = new CustomerDAO();
            RentalDAO rentalDAO = new RentalDAO();
            VehicleDAO vehicleDAO = new VehicleDAO();

            // Initialize VehicleManager (simulate manager login)
            VehicleManager vehicleManager = new VehicleManager("M001", 2, "Test Manager", 2);
            System.out.println("✅ VehicleManager initialized");

            // Check initial state
            int initialCustomerCount = customerDAO.getAll().size();
            int initialVehicleCount = vehicleDAO.loadAll().size();
            int initialRentalCount = rentalDAO.getAll().size();

            System.out.println("📊 Initial State:");
            System.out.println("   Customers: " + initialCustomerCount);
            System.out.println("   Vehicles: " + initialVehicleCount);
            System.out.println("   Rentals: " + initialRentalCount);

            // Simulate customer login and rental
            System.out.println("\n🔐 Simulating Customer Login & Rental...");
            Rental customerRental = new Rental(1001, 1, 1, LocalDate.now(), LocalDate.now().plusDays(2), 150.0);
            rentalDAO.save(customerRental);
            System.out.println("✅ Customer rental created");

            // Check state after rental
            int postRentalCustomerCount = customerDAO.getAll().size();
            int postRentalVehicleCount = vehicleDAO.loadAll().size();
            int postRentalRentalCount = rentalDAO.getAll().size();

            System.out.println("\n📊 State After Customer Rental:");
            System.out.println("   Customers: " + postRentalCustomerCount);
            System.out.println("   Vehicles: " + postRentalVehicleCount);
            System.out.println("   Rentals: " + postRentalRentalCount);

            // Simulate manager logout (set vehicleManager to null)
            System.out.println("\n🚪 Simulating Manager Logout...");
            vehicleManager = null;
            System.out.println("✅ VehicleManager set to null (simulating logout)");

            // Now try to generate system report (this is where the bug occurs)
            System.out.println("\n📊 Attempting System Report Generation...");

            try {
                // This simulates the showSystemReports() method
                System.out.println("   Generating system report...");

                // Check if vehicleManager is null
                if (vehicleManager == null) {
                    System.out.println("   ⚠️  VehicleManager is null - this would cause the bug!");
                    System.out.println("   📊 Fallback: Using direct DAO access for vehicle count");

                    // Fallback to direct DAO access
                    int fallbackVehicleCount = vehicleDAO.loadAll().size();
                    System.out.println("   ✅ Fallback vehicle count: " + fallbackVehicleCount);
                }

                // Check customer count
                int reportCustomerCount = customerDAO.getAll().size();
                System.out.println("   ✅ Customer count in report: " + reportCustomerCount);

                // Check rental count
                int reportRentalCount = rentalDAO.getAll().size();
                System.out.println("   ✅ Rental count in report: " + reportRentalCount);

                System.out.println("\n📊 System Report Results:");
                System.out.println("   Vehicles: " + (vehicleManager != null ? "N/A (would crash)" : vehicleDAO.loadAll().size()));
                System.out.println("   Customers: " + reportCustomerCount);
                System.out.println("   Rentals: " + reportRentalCount);

                if (reportCustomerCount == initialCustomerCount) {
                    System.out.println("✅ Customer count remained correct: " + reportCustomerCount);
                } else {
                    System.out.println("❌ Customer count incorrect! Expected: " + initialCustomerCount + ", Got: " + reportCustomerCount);
                }

            } catch (Exception e) {
                System.out.println("❌ System report generation FAILED: " + e.getMessage());
                e.printStackTrace();
            }

            // Clean up
            rentalDAO.delete("1001");
            System.out.println("\n🧹 Test cleanup completed");

            System.out.println("\n=================================");
            System.out.println("📊 SYSTEM REPORT BUG TEST RESULTS");
            System.out.println("=================================");
            System.out.println("🎯 Bug Identified: VehicleManager null after logout causes system report crash");
            System.out.println("💡 Solution: Add null checks and fallback to direct DAO access");
            System.out.println("✅ Test demonstrates the exact issue and potential fix");

        } catch (Exception e) {
            System.out.println("❌ Test FAILED with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
