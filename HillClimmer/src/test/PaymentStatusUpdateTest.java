package test;

import hillclimmer.DatabaseModule.RentalDAO;
import hillclimmer.RentalModule.Rental;
import hillclimmer.RentalModule.RentalManager;
import java.time.LocalDate;

/**
 * Test to verify that rental payment status updates work correctly
 * Tests the issue where payment shows successful but rental status remains Pending
 */
public class PaymentStatusUpdateTest {

    public static void main(String[] args) {
        System.out.println("🧪 Payment Status Update Test");
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

            // Create a test rental
            LocalDate today = LocalDate.now(); // 2025-09-16
            System.out.println("📅 Today: " + today);

            // Create a rental for today (should become Active when paid)
            Rental testRental = new Rental(0, 999, 1,
                today, today, 75.0);
            testRental.setStatus("Pending"); // Set as pending (unpaid)
            testRental.setPaymentStatus("Unpaid");
            rentalDAO.save(testRental);

            // Get the saved rental
            var allRentals = rentalDAO.loadAll();
            Rental savedRental = null;
            for (Rental r : allRentals) {
                if (r.getCustomerId() == 999) {
                    savedRental = r;
                    break;
                }
            }

            if (savedRental == null) {
                System.out.println("❌ Failed to create test rental");
                return;
            }

            System.out.println("\n📋 Test rental created:");
            System.out.println("   ID: " + savedRental.getRentalId());
            System.out.println("   Dates: " + savedRental.getStartDate() + " to " + savedRental.getEndDate());
            System.out.println("   Initial Status: " + savedRental.getStatus());
            System.out.println("   Initial Payment: " + savedRental.getPaymentStatus());

            // Simulate successful payment (what happens in processRentalPayment)
            System.out.println("\n💳 Simulating successful payment...");

            // This is the exact code from the payment success flow
            Rental rental = rentalManager.getRentalById(savedRental.getRentalId());
            if (rental != null) {
                System.out.println("✅ Found rental for payment processing");
                rental.setPaymentStatus("Paid");
                rentalManager.updateRental(rental);
                System.out.println("✅ Payment status set to Paid");
                
                // Update rental status based on dates now that payment is successful
                rentalManager.updateRentalStatuses();
                System.out.println("✅ updateRentalStatuses() called");
                
                // Check final status
                rental = rentalManager.getRentalById(savedRental.getRentalId());
                if (rental != null) {
                    System.out.println("\n📋 Final status after payment:");
                    System.out.println("   Status: " + rental.getStatus());
                    System.out.println("   Payment: " + rental.getPaymentStatus());
                    
                    // Verify the status is correct
                    boolean statusCorrect = "Active".equals(rental.getStatus());
                    boolean paymentCorrect = "Paid".equals(rental.getPaymentStatus());
                    
                    if (statusCorrect && paymentCorrect) {
                        System.out.println("✅ SUCCESS: Payment and status update worked correctly");
                    } else {
                        System.out.println("❌ FAILURE: Status update did not work");
                        System.out.println("   Expected: Status=Active, Payment=Paid");
                        System.out.println("   Actual: Status=" + rental.getStatus() + ", Payment=" + rental.getPaymentStatus());
                    }
                }
            } else {
                System.out.println("❌ ERROR: Could not find rental for payment processing");
            }

            // Cleanup
            System.out.println("\n🧹 Cleaning up test data...");
            rentalDAO.delete(String.valueOf(savedRental.getRentalId()));
            System.out.println("✅ Test cleanup completed");

        } catch (Exception e) {
            System.out.println("❌ Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
