package test;

import hillclimmer.DatabaseModule.RentalDAO;
import hillclimmer.RentalModule.Rental;
import hillclimmer.RentalModule.RentalManager;
import java.time.LocalDate;

/**
 * Test to verify rental status update functionality based on current date
 *
 * Status definitions:
 * - Pending: Created but not paid yet (paymentStatus != "Paid")
 * - Upcoming: Paid and startDate > today (pre-booking)
 * - Active: Paid and startDate <= today AND endDate >= today (ongoing)
 * - End: Paid and endDate < today (completed)
 */
public class RentalStatusUpdateTest {

    public static void main(String[] args) {
        System.out.println("🧪 Rental Status Update Test");
        System.out.println("============================");

        RentalDAO rentalDAO = new RentalDAO();
        RentalManager rentalManager = new RentalManager();

        try {
            // Clean up any existing test rentals
            System.out.println("\n🧹 Cleaning up existing test data...");
            var existingRentals = rentalDAO.loadAll();
            for (Rental r : existingRentals) {
                if (r.getCustomerId() == 999) { // Use test customer ID
                    rentalDAO.delete(String.valueOf(r.getRentalId()));
                }
            }

            // Test data setup
            LocalDate today = LocalDate.now(); // 2025-09-16
            System.out.println("📅 Today: " + today);

            // Test Case 1: Upcoming rental (starts in future)
            Rental upcomingRental = new Rental(0, 999, 1,
                today.plusDays(5), today.plusDays(7), 150.0);
            upcomingRental.setStatus("Active"); // Set wrong status initially
            upcomingRental.setPaymentStatus("Paid"); // Set as paid for testing
            rentalDAO.save(upcomingRental);

            // Test Case 2: Active rental (started in past, ends in future)
            Rental activeRental = new Rental(0, 999, 2,
                today.minusDays(2), today.plusDays(2), 200.0);
            activeRental.setStatus("Upcoming"); // Set wrong status initially
            activeRental.setPaymentStatus("Paid"); // Set as paid for testing
            rentalDAO.save(activeRental);

            // Test Case 3: Ended rental (ended in past)
            Rental endedRental = new Rental(0, 999, 3,
                today.minusDays(5), today.minusDays(1), 120.0);
            endedRental.setStatus("Active"); // Set wrong status initially
            endedRental.setPaymentStatus("Paid"); // Set as paid for testing
            rentalDAO.save(endedRental);

            System.out.println("\n📝 Created test rentals with incorrect initial statuses");

            // Find the saved rentals
            var allRentals = rentalDAO.loadAll();
            Rental savedUpcoming = null, savedActive = null, savedEnded = null;

            for (Rental r : allRentals) {
                if (r.getCustomerId() == 999) {
                    if (r.getVehicleId() == 1) savedUpcoming = r;
                    else if (r.getVehicleId() == 2) savedActive = r;
                    else if (r.getVehicleId() == 3) savedEnded = r;
                }
            }

            // Display initial statuses
            System.out.println("\n📊 Initial Statuses:");
            if (savedUpcoming != null) {
                System.out.println("Upcoming rental (ID:" + savedUpcoming.getRentalId() + "): " +
                    savedUpcoming.getStartDate() + " to " + savedUpcoming.getEndDate() +
                    " | Status: " + savedUpcoming.getStatus());
            }
            if (savedActive != null) {
                System.out.println("Active rental (ID:" + savedActive.getRentalId() + "): " +
                    savedActive.getStartDate() + " to " + savedActive.getEndDate() +
                    " | Status: " + savedActive.getStatus());
            }
            if (savedEnded != null) {
                System.out.println("Ended rental (ID:" + savedEnded.getRentalId() + "): " +
                    savedEnded.getStartDate() + " to " + savedEnded.getEndDate() +
                    " | Status: " + savedEnded.getStatus());
            }

            // Run status update
            System.out.println("\n🔄 Running status update...");
            rentalManager.updateRentalStatuses();

            // Reload and check final statuses
            allRentals = rentalDAO.loadAll();
            for (Rental r : allRentals) {
                if (r.getCustomerId() == 999) {
                    if (r.getVehicleId() == 1) savedUpcoming = r;
                    else if (r.getVehicleId() == 2) savedActive = r;
                    else if (r.getVehicleId() == 3) savedEnded = r;
                }
            }

            // Display final statuses and verify
            System.out.println("\n📊 Final Statuses:");
            boolean allTestsPassed = true;

            if (savedUpcoming != null) {
                String status = savedUpcoming.getStatus();
                boolean correct = "Upcoming".equals(status);
                System.out.println("Upcoming rental (ID:" + savedUpcoming.getRentalId() + "): " +
                    savedUpcoming.getStartDate() + " to " + savedUpcoming.getEndDate() +
                    " | Status: " + status + (correct ? " ✅" : " ❌"));
                if (!correct) allTestsPassed = false;
            }

            if (savedActive != null) {
                String status = savedActive.getStatus();
                boolean correct = "Active".equals(status);
                System.out.println("Active rental (ID:" + savedActive.getRentalId() + "): " +
                    savedActive.getStartDate() + " to " + savedActive.getEndDate() +
                    " | Status: " + status + (correct ? " ✅" : " ❌"));
                if (!correct) allTestsPassed = false;
            }

            if (savedEnded != null) {
                String status = savedEnded.getStatus();
                boolean correct = "End".equals(status);
                System.out.println("Ended rental (ID:" + savedEnded.getRentalId() + "): " +
                    savedEnded.getStartDate() + " to " + savedEnded.getEndDate() +
                    " | Status: " + status + (correct ? " ✅" : " ❌"));
                if (!correct) allTestsPassed = false;
            }

            // Test result
            System.out.println("\n" + (allTestsPassed ? "✅ ALL TESTS PASSED" : "❌ SOME TESTS FAILED"));

            // Cleanup
            System.out.println("\n🧹 Cleaning up test data...");
            for (Rental r : allRentals) {
                if (r.getCustomerId() == 999) {
                    rentalDAO.delete(String.valueOf(r.getRentalId()));
                }
            }
            System.out.println("✅ Test cleanup completed");

        } catch (Exception e) {
            System.out.println("❌ Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}