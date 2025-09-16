package test;

import hillclimmer.DatabaseModule.RentalDAO;
import hillclimmer.RentalModule.Rental;
import hillclimmer.RentalModule.RentalManager;
import hillclimmer.PaymentModule.CreditCardPayment;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Test to simulate the exact payment flow from the application
 * Tests the issue where payment shows successful but rental status doesn't update
 */
public class PaymentFlowTest {

    public static void main(String[] args) {
        System.out.println("🧪 Payment Flow Test (Simulating App Behavior)");
        System.out.println("=============================================");

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

            // Simulate rental creation (like in newRental() method)
            System.out.println("\n🏍️ Simulating rental creation...");
            var allRentals = rentalDAO.loadAll();
            int maxId = allRentals.stream()
                .mapToInt(Rental::getRentalId)
                .max()
                .orElse(0);
            int rentalId = maxId + 1;

            LocalDate today = LocalDate.now();
            rentalManager.addRentalWithId(rentalId, 999, 1, today, today, 75.0);

            // Get the created rental and set initial status (like in app)
            Rental newRental = rentalManager.getRentalById(rentalId);
            if (newRental != null) {
                newRental.setPaymentStatus("Unpaid");
                newRental.setStatus("Pending");
                rentalManager.updateRental(newRental);
                System.out.println("✅ Rental created with ID: " + rentalId + ", Status: " + newRental.getStatus());
            }

            // Now simulate the payment process (like in processRentalPayment)
            System.out.println("\n💳 Simulating credit card payment process...");

            double amount = 75.0;
            String paymentID = "P" + System.currentTimeMillis();
            String timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String customerID = "C999"; // Test customer

            CreditCardPayment payment = new CreditCardPayment(paymentID, amount, timestamp, customerID);
            payment.processPayment();

            System.out.println("Payment processing result: " + payment.getPaymentStatus());

            // This is the exact code from the successful payment branch
            if ("Paid".equals(payment.getPaymentStatus())) {
                System.out.println("✅ Payment successful, updating rental status...");

                // Update rental payment status to Paid
                Rental rental = rentalManager.getRentalById(rentalId);
                if (rental != null) {
                    System.out.println("   Found rental ID " + rentalId + " with current status: " + rental.getStatus());
                    rental.setPaymentStatus("Paid");
                    rentalManager.updateRental(rental);
                    System.out.println("   Payment status set to Paid");

                    // Update rental status based on dates now that payment is successful
                    rentalManager.updateRentalStatuses();
                    System.out.println("   updateRentalStatuses() called");

                    // Check final result
                    rental = rentalManager.getRentalById(rentalId);
                    if (rental != null) {
                        System.out.println("   Final status: " + rental.getStatus() + " (Payment: " + rental.getPaymentStatus() + ")");

                        boolean success = "Active".equals(rental.getStatus()) && "Paid".equals(rental.getPaymentStatus());
                        System.out.println(success ? "✅ SUCCESS: Payment flow worked correctly" : "❌ FAILURE: Status not updated properly");
                    }
                } else {
                    System.out.println("❌ ERROR: Rental ID " + rentalId + " not found for payment update!");
                }
            } else {
                System.out.println("❌ Payment failed with status: " + payment.getPaymentStatus());
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
