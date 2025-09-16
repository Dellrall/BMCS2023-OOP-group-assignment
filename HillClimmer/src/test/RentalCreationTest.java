package test;

import hillclimmer.DatabaseModule.RentalDAO;
import hillclimmer.RentalModule.Rental;
import hillclimmer.RentalModule.RentalManager;
import java.time.LocalDate;

/**
 * Test to verify rental creation works correctly
 */
public class RentalCreationTest {

    public static void main(String[] args) {
        System.out.println("🧪 Rental Creation Test");
        System.out.println("=======================");

        RentalDAO rentalDAO = new RentalDAO();
        RentalManager rentalManager = new RentalManager();

        try {
            // Clean up any existing test rentals
            System.out.println("\n🧹 Cleaning up existing test data...");
            var existingRentals = rentalDAO.loadAll();
            for (Rental r : existingRentals) {
                if (r.getCustomerId() == 999) {
                    rentalDAO.delete(String.valueOf(r.getRentalId()));
                }
            }

            // Simulate the rental creation process from newRental()
            System.out.println("\n🏍️ Simulating rental creation process...");

            var allRentals = rentalDAO.loadAll();
            int maxId = allRentals.stream()
                .mapToInt(Rental::getRentalId)
                .max()
                .orElse(0);
            int rentalId = maxId + 1;

            System.out.println("Calculated rental ID: " + rentalId);
            System.out.println("Max existing ID: " + maxId);

            LocalDate today = LocalDate.now();
            rentalManager.addRentalWithId(rentalId, 999, 1, today, today, 75.0);

            // Get the created rental and set initial status (like in app)
            Rental newRental = rentalManager.getRentalById(rentalId);
            if (newRental != null) {
                System.out.println("✅ Rental found after creation");
                System.out.println("   ID: " + newRental.getRentalId());
                System.out.println("   Status: " + newRental.getStatus());
                System.out.println("   Payment: " + newRental.getPaymentStatus());

                newRental.setPaymentStatus("Unpaid");
                newRental.setStatus("Pending");
                rentalManager.updateRental(newRental);

                // Verify it was saved
                Rental savedRental = rentalManager.getRentalById(rentalId);
                if (savedRental != null) {
                    System.out.println("✅ Rental saved successfully");
                    System.out.println("   Final Status: " + savedRental.getStatus());
                    System.out.println("   Final Payment: " + savedRental.getPaymentStatus());
                } else {
                    System.out.println("❌ Rental not found after save!");
                }
            } else {
                System.out.println("❌ Rental not found immediately after creation!");
            }

            // Cleanup
            System.out.println("\n🧹 Cleaning up test data...");
            rentalDAO.delete(String.valueOf(rentalId));
            System.out.println("✅ Test cleanup completed");

        } catch (Exception e) {
            System.out.println("❌ Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
