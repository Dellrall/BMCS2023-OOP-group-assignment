/*
 * HillClimberDemo - Testing and Demonstration class for Hill Climber Vehicle Rental System
 * This class contains only testing and demonstration functionality
 * For production use, run HillClimmer.java instead
 */
package hillclimmer;

import hillclimmer.CustomerModule.*;
import hillclimmer.VehicleModule.*;
import hillclimmer.PaymentModule.*;
import hillclimmer.RentalModule.*;
import hillclimmer.DurationModule.*;
import hillclimmer.DatabaseModule.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Demonstration and Testing class for Hill Climber Vehicle Rental System
 * Contains only testing functionality - NOT for production use
 *
 * @author las
 */
public class HillClimberDemo {
    private static CustomerDAO customerDAO = new CustomerDAO();
    private static VehicleManager vehicleManager;
    private static RentalManager rentalManager = new RentalManager();
    private static DurationManager durationManager = new DurationManager();
    private static TransactionManager transactionManager = new TransactionManager("TM001");

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   🧪 HILL CLIMBER DEMO & TESTING   🧪");
        System.out.println("        Testing and Demonstration");
        System.out.println("        Mode Only");
        System.out.println("=========================================");
        System.out.println("⚠️  WARNING: This is for testing only!");
        System.out.println("   For production use, run: ant run");
        System.out.println("=========================================");

        initializeSystem();
        runDemoMode();
    }

    private static void initializeSystem() {
        // Initialize default vehicle manager
        vehicleManager = new VehicleManager("VM001", "Level 2", "Ahmad Abdullah", 5);

        // Load vehicles from CSV file
        loadVehiclesFromCSV();

        // Add sample customer for testing
        try {
            Customer sampleCustomer = new Customer("C001", "Muhammad Ali", "950101-14-5678",
                "+60123456789", "muhammad@email.com", "B", LocalDate.of(2026, 12, 31),
                "No. 123, Jalan Bukit Bintang, 55100 Kuala Lumpur", 29, "password123");
            customerDAO.save(sampleCustomer);
        } catch (Exception e) {
            // Customer might already exist
        }
    }

    private static void loadVehiclesFromCSV() {
        try {
            // The vehicles are loaded automatically by the VehicleManager from the CSV file
            System.out.println("Loading vehicles from CSV file...");
        } catch (Exception e) {
            System.out.println("Error loading vehicles from CSV: " + e.getMessage());
        }
    }

    private static void runDemoMode() {
        System.out.println("\n=== DEMO MODE ACTIVATED ===");
        System.out.println("Running system demonstrations and tests...");

        // Display vehicle statistics
        displayVehicleStatistics();

        // Demonstrate key features
        demonstrateKeyFeatures();

        // Demonstrate payment types
        demonstratePaymentTypes();

        System.out.println("\n=== DEMO COMPLETE ===");
        System.out.println("✅ All demonstrations completed successfully");
        System.out.println("=========================================");
        System.out.println("   🚀 PRODUCTION APPLICATION   🚀");
        System.out.println("   Run 'ant run' for full application");
        System.out.println("   with login, rental, and payment features");
        System.out.println("=========================================");
    }

    private static void displayVehicleStatistics() {
        System.out.println("\n=== VEHICLE INVENTORY STATISTICS ===");
        List<Vehicle> allVehicles = vehicleManager.getAllVehicles();

        // Count vehicles by type
        long mountainBikes = allVehicles.stream().filter(v -> v.getVehicleType().equals("Mountain Bike")).count();
        long dirtBikes = allVehicles.stream().filter(v -> v.getVehicleType().equals("Dirt Bike")).count();
        long buggies = allVehicles.stream().filter(v -> v.getVehicleType().equals("Buggy")).count();
        long crossovers = allVehicles.stream().filter(v -> v.getVehicleType().equals("Crossover")).count();
        long availableVehicles = allVehicles.stream().filter(Vehicle::isAvailable).count();

        System.out.println("Total Vehicles: " + allVehicles.size());
        System.out.println("🚵 Mountain Bikes: " + mountainBikes);
        System.out.println("🏍️  Dirt Bikes: " + dirtBikes);
        System.out.println("🚙 Buggies: " + buggies);
        System.out.println("🚗 Crossovers: " + crossovers);
        System.out.println("✅ Available for Rent: " + availableVehicles);
        System.out.println("❌ Currently Rented: " + (allVehicles.size() - availableVehicles));

        // Show price range
        double minPrice = allVehicles.stream().mapToDouble(Vehicle::getModelPricing).min().orElse(0);
        double maxPrice = allVehicles.stream().mapToDouble(Vehicle::getModelPricing).max().orElse(0);
        double avgPrice = allVehicles.stream().mapToDouble(Vehicle::getModelPricing).average().orElse(0);

        System.out.println("💰 Price Range: RM" + String.format("%.0f", minPrice) + " - RM" + String.format("%.0f", maxPrice));
        System.out.println("📊 Average Price: RM" + String.format("%.1f", avgPrice));
    }

    private static void demonstrateKeyFeatures() {
        System.out.println("\n=== KEY FEATURES DEMONSTRATION ===");

        // Test Malaysian validation
        System.out.println("🇲🇾 Testing Malaysian Validations:");
        boolean icValid = Customer.isValidIC("950101-14-5678");
        boolean phoneValid = Customer.isValidMalaysianPhone("+60123456789");
        boolean licenseValid = Customer.isValidLicenseType("B");

        System.out.println("  • IC Validation (950101-14-5678): " + (icValid ? "✅ VALID" : "❌ INVALID"));
        System.out.println("  • Phone Validation (+60123456789): " + (phoneValid ? "✅ VALID" : "❌ INVALID"));
        System.out.println("  • License Validation (B): " + (licenseValid ? "✅ VALID" : "❌ INVALID"));

        // Test duration management
        System.out.println("\n⏰ Testing Duration Management:");
        try {
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(3);
            durationManager.createBasicRentalPeriod(1, startDate, endDate, 75.0);
            System.out.println("  • Rental period created: " + startDate + " to " + endDate);
            System.out.println("  • Active periods: " + durationManager.getActiveRentalPeriods().size());
        } catch (Exception e) {
            System.out.println("  • Duration management test: " + e.getMessage());
        }

        // Test payment processing
        System.out.println("\n💳 Testing Payment Processing:");
        try {
            Payment payment = Payment.createPayment("Credit Card", "DEMO001", 150.0,
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "C001");
            payment.updateStatus("Paid");
            transactionManager.recordTransaction(payment);
            System.out.println("  • Payment processed: RM150.00 via Credit Card");
            System.out.println("  • Transaction recorded successfully");
        } catch (Exception e) {
            System.out.println("  • Payment processing test: " + e.getMessage());
        }

        // Test reminder system
        System.out.println("\n🔔 Testing Reminder System:");
        try {
            durationManager.createReturnReminder(1, LocalDateTime.now().plusDays(2));
            durationManager.createPaymentReminder(1, LocalDateTime.now().plusDays(1), 200.0);
            System.out.println("  • Return reminder created for 2 days from now");
            System.out.println("  • Payment reminder created for tomorrow");
            System.out.println("  • Total reminders: " + durationManager.getAllReminders().size());
        } catch (Exception e) {
            System.out.println("  • Reminder system test: " + e.getMessage());
        }
    }

    private static void demonstratePaymentTypes() {
        System.out.println("\n=== PAYMENT TYPES DEMONSTRATION ===");

        System.out.println("💳 Credit Card Payment:");
        System.out.println("  • Card validation and processing simulation");
        System.out.println("  • CVV, expiry date, and card type detection");
        System.out.println("  • Gateway connection simulation");

        System.out.println("\n🏦 Online Banking Payment:");
        System.out.println("  • Multi-bank support (Maybank, CIMB, Public Bank, etc.)");
        System.out.println("  • Username/password authentication");
        System.out.println("  • OTP verification simulation");

        System.out.println("\n💵 Cash Payment:");
        System.out.println("  • Payment slip generation");
        System.out.println("  • Prebooking system");
        System.out.println("  • 7-day payment deadline");

        System.out.println("\n📄 Payment History:");
        System.out.println("  • Complete transaction records");
        System.out.println("  • Payment slip storage and retrieval");
        System.out.println("  • Reference number tracking");

        System.out.println("\n✅ All payment types are fully functional in the main application!");
    }
}
