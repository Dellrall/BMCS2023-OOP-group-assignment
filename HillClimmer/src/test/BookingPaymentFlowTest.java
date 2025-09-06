package test;

import hillclimmer.CustomerModule.Customer;
import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.DatabaseModule.RentalDAO;
import hillclimmer.DatabaseModule.VehicleDAO;
import hillclimmer.RentalModule.Rental;
import hillclimmer.VehicleModule.Vehicle;
import hillclimmer.PaymentModule.*;
import java.time.LocalDate;
import java.util.List;

/**
 * Comprehensive test for the booking and payment flow
 */
public class BookingPaymentFlowTest {
    public static void main(String[] args) {
        System.out.println("🚗 BOOKING & PAYMENT FLOW TEST");
        System.out.println("==============================");

        int totalTests = 0;
        int passedTests = 0;

        try {
            // Setup DAOs
            CustomerDAO customerDAO = new CustomerDAO();
            RentalDAO rentalDAO = new RentalDAO();
            VehicleDAO vehicleDAO = new VehicleDAO();

            // Test 1: Customer login verification
            totalTests++;
            System.out.println("\n1. Testing Customer Login");
            Customer testCustomer = customerDAO.load("C001");
            if (testCustomer != null && testCustomer.authenticatePassword("AliSecure123!")) {
                System.out.println("✅ Customer login successful");
                passedTests++;
            } else {
                System.out.println("❌ Customer login failed");
                return;
            }

            // Test 2: Vehicle availability
            totalTests++;
            System.out.println("\n2. Testing Vehicle Availability");
            List<Vehicle> vehicles = vehicleDAO.loadAll();
            Vehicle availableVehicle = vehicles.stream()
                .filter(Vehicle::isAvailable)
                .findFirst()
                .orElse(null);

            if (availableVehicle != null) {
                System.out.println("✅ Available vehicle found: " + availableVehicle.getVehicleModel());
                passedTests++;
            } else {
                System.out.println("❌ No available vehicles found");
                return;
            }

            // Test 3: Rental creation
            totalTests++;
            System.out.println("\n3. Testing Rental Creation");
            int initialRentalCount = rentalDAO.loadAll().size();

            // Simulate rental creation (we can't actually call the UI method, so we'll test the DAO)
            Rental testRental = new Rental(999, 1, availableVehicle.getVehicleID().substring(1).equals("B") ? 1 :
                Integer.parseInt(availableVehicle.getVehicleID().substring(2)),
                LocalDate.now(), LocalDate.now().plusDays(2), 200.0);

            rentalDAO.save(testRental);

            int finalRentalCount = rentalDAO.loadAll().size();
            if (finalRentalCount > initialRentalCount) {
                System.out.println("✅ Rental created successfully");
                passedTests++;
            } else {
                System.out.println("❌ Rental creation failed");
            }

            // Test 4: Payment processing - Credit Card
            totalTests++;
            System.out.println("\n4. Testing Credit Card Payment");
            String paymentID = "P" + System.currentTimeMillis();
            CreditCardPayment cardPayment = new CreditCardPayment(paymentID, 200.0,
                java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                testCustomer.getCustomerID());

            cardPayment.processPayment();
            if ("Paid".equals(cardPayment.getPaymentStatus())) {
                System.out.println("✅ Credit card payment processed successfully");
                passedTests++;
            } else {
                System.out.println("❌ Credit card payment failed: " + cardPayment.getPaymentStatus());
            }

            // Test 5: Payment processing - Cash
            totalTests++;
            System.out.println("\n5. Testing Cash Payment");
            paymentID = "P" + (System.currentTimeMillis() + 1);
            CashPayment cashPayment = new CashPayment(paymentID, 150.0,
                java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                testCustomer.getCustomerID());

            cashPayment.processPayment();
            if ("Prebooked - Awaiting Cash Payment".equals(cashPayment.getPaymentStatus())) {
                System.out.println("✅ Cash payment slip generated successfully");
                System.out.println("   Reference: " + cashPayment.getReferenceNumber());
                passedTests++;
            } else {
                System.out.println("❌ Cash payment failed: " + cashPayment.getPaymentStatus());
            }

            // Test 6: Outstanding balance update
            totalTests++;
            System.out.println("\n6. Testing Outstanding Balance Update");
            double initialBalance = testCustomer.getOutstandingBalance();
            testCustomer.setOutstandingBalance(initialBalance + 200.0);
            customerDAO.update(testCustomer);

            Customer updatedCustomer = customerDAO.load(testCustomer.getCustomerID());
            if (updatedCustomer.getOutstandingBalance() == initialBalance + 200.0) {
                System.out.println("✅ Outstanding balance updated correctly");
                passedTests++;
            } else {
                System.out.println("❌ Outstanding balance update failed");
            }

            // Clean up test data
            System.out.println("\n🧹 Cleaning up test data...");
            rentalDAO.delete("999");
            testCustomer.setOutstandingBalance(initialBalance);
            customerDAO.update(testCustomer);

        } catch (Exception e) {
            System.out.println("❌ Test error: " + e.getMessage());
            e.printStackTrace();
        }

        // Results
        System.out.println("\n📊 TEST RESULTS");
        System.out.println("===============");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Success Rate: " + (totalTests > 0 ? (passedTests * 100 / totalTests) : 0) + "%");

        if (passedTests == totalTests) {
            System.out.println("🎉 ALL TESTS PASSED - Booking & Payment Flow Working!");
            System.out.println("✅ Integrated payment flow is functional");
            System.out.println("✅ Credit card payments work");
            System.out.println("✅ Cash payment slips generated");
            System.out.println("✅ Outstanding balance updates correctly");
        } else {
            System.out.println("⚠️  Some tests failed - review the booking/payment flow");
        }
    }
}
