package test;

import hillclimmer.DatabaseModule.RentalDAO;
import hillclimmer.RentalModule.Rental;
import java.time.LocalDate;

/**
 * Test to verify rental ID generation and consistency fixes
 */
public class RentalIdConsistencyTest {

    public static void main(String[] args) {
        System.out.println("🧪 Rental ID Consistency Test");
        System.out.println("=============================");

        try {
            // Test 1: Create RentalDAO instance
            RentalDAO rentalDAO = new RentalDAO();
            System.out.println("✅ Test 1 PASSED: RentalDAO created successfully");

            // Clean up any existing test rentals first
            var existingRentals = rentalDAO.loadAll();
            for (Rental r : existingRentals) {
                if (r.getCustomerId() == 1 && r.getVehicleId() == 1 && r.getTotalCost() == 100.0) {
                    rentalDAO.delete(String.valueOf(r.getRentalId()));
                }
                if (r.getCustomerId() == 2 && r.getVehicleId() == 2 && r.getTotalCost() == 200.0) {
                    rentalDAO.delete(String.valueOf(r.getRentalId()));
                }
            }
            System.out.println("✅ Cleanup: Removed existing test rentals");

            // Test 2: Create a rental and check ID generation
            Rental testRental = new Rental(0, 1, 1, LocalDate.now(), LocalDate.now().plusDays(1), 100.0);
            rentalDAO.save(testRental);

            // Load all rentals to find the one we just saved
            var allRentalsAfterSave = rentalDAO.loadAll();
            Rental savedRental = null;
            for (Rental r : allRentalsAfterSave) {
                if (r.getCustomerId() == 1 && r.getVehicleId() == 1 && r.getTotalCost() == 100.0) {
                    savedRental = r;
                    break;
                }
            }

            if (savedRental != null && savedRental.getRentalId() > 0) {
                System.out.println("✅ Test 2 PASSED: Rental saved with generated ID: " + savedRental.getRentalId());
            } else {
                System.out.println("❌ Test 2 FAILED: Rental ID not generated properly");
                return;
            }

            // Test 3: Load the rental and verify ID consistency
            Rental loadedRental = rentalDAO.load(String.valueOf(savedRental.getRentalId()));
            if (loadedRental != null && loadedRental.getRentalId() == savedRental.getRentalId()) {
                System.out.println("✅ Test 3 PASSED: Rental ID consistent between save and load");
            } else {
                System.out.println("❌ Test 3 FAILED: Rental ID mismatch between save and load");
                return;
            }

            // Test 4: Create another rental and verify unique ID
            Rental testRental2 = new Rental(0, 2, 2, LocalDate.now(), LocalDate.now().plusDays(2), 200.0);
            rentalDAO.save(testRental2);

            // Load all rentals to find the second one
            var allRentalsAfterSave2 = rentalDAO.loadAll();
            Rental savedRental2 = null;
            for (Rental r : allRentalsAfterSave2) {
                if (r.getCustomerId() == 2 && r.getVehicleId() == 2 && r.getTotalCost() == 200.0) {
                    savedRental2 = r;
                    break;
                }
            }

            if (savedRental2 != null && savedRental2.getRentalId() != savedRental.getRentalId()) {
                System.out.println("✅ Test 4 PASSED: Second rental has unique ID: " + savedRental2.getRentalId());
            } else {
                System.out.println("❌ Test 4 FAILED: Second rental ID not unique");
                return;
            }

            // Test 5: Verify all rentals can be loaded
            var allRentals = rentalDAO.loadAll();
            boolean foundRental1 = false;
            boolean foundRental2 = false;

            for (Rental r : allRentals) {
                if (r.getRentalId() == savedRental.getRentalId()) foundRental1 = true;
                if (r.getRentalId() == savedRental2.getRentalId()) foundRental2 = true;
            }

            if (foundRental1 && foundRental2) {
                System.out.println("✅ Test 5 PASSED: Both rentals found in database");
            } else {
                System.out.println("❌ Test 5 FAILED: One or more rentals not found in database");
                return;
            }

            // Cleanup: Remove test rentals
            rentalDAO.delete(String.valueOf(savedRental.getRentalId()));
            rentalDAO.delete(String.valueOf(savedRental2.getRentalId()));

            System.out.println("✅ Cleanup: Test rentals removed");

            System.out.println("\n=================================");
            System.out.println("📊 RENTAL ID CONSISTENCY TEST RESULTS");
            System.out.println("=================================");
            System.out.println("📈 Tests Passed: 5/5");
            System.out.println("📊 Success Rate: 100.0%");
            System.out.println("🎉 ALL RENTAL ID TESTS PASSED!");
            System.out.println("✅ Rental ID generation and consistency fixes are working correctly");

        } catch (Exception e) {
            System.out.println("❌ Test FAILED with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
