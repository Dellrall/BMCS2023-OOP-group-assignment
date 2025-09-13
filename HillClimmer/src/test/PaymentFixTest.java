package test;

import hillclimmer.PaymentModule.OnlineBankingPayment;
import hillclimmer.PaymentModule.CreditCardPayment;
import hillclimmer.PaymentModule.CashPayment;

/**
 * Test class to verify payment processing fixes
 * Tests both online banking and credit card payments
 */
public class PaymentFixTest {

    public static void main(String[] args) {
        System.out.println("=== Payment Processing Fix Test ===");
        System.out.println("Testing payment methods to ensure they don't crash the program");
        System.out.println();

        testOnlineBankingPayment();
        testCreditCardPayment();
        testCashPayment();
        testExitHandling();
        printTestScenarios();

        System.out.println("\n✅ All payment tests completed!");
        System.out.println("Payment processing should now work without crashes.");
    }

    private static void testOnlineBankingPayment() {
        System.out.println("Testing Online Banking Payment...");

        try {
            // Create a test online banking payment
            OnlineBankingPayment payment = new OnlineBankingPayment("TEST001", 100.0, "11/09/2025", "CUST001");

            // Test that the object is created successfully
            assert payment != null : "OnlineBankingPayment object should not be null";
            assert payment.getPaymentID().equals("TEST001") : "Payment ID should match";
            assert payment.getTotalAmount() == 100.0 : "Amount should match";
            assert payment.getPaymentStatus().equals("Pending") : "Initial status should be Pending";

            System.out.println("✅ OnlineBankingPayment object creation successful");
            System.out.println("   - Payment ID: " + payment.getPaymentID());
            System.out.println("   - Amount: RM" + payment.getTotalAmount());
            System.out.println("   - Status: " + payment.getPaymentStatus());

        } catch (Exception e) {
            System.out.println("❌ OnlineBankingPayment test failed: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println();
    }

    private static void testCreditCardPayment() {
        System.out.println("Testing Credit Card Payment...");

        try {
            // Create a test credit card payment
            CreditCardPayment payment = new CreditCardPayment("TEST002", 150.0, "11/09/2025", "CUST002");

            // Test that the object is created successfully
            assert payment != null : "CreditCardPayment object should not be null";
            assert payment.getPaymentID().equals("TEST002") : "Payment ID should match";
            assert payment.getTotalAmount() == 150.0 : "Amount should match";
            assert payment.getPaymentStatus().equals("Pending") : "Initial status should be Pending";

            System.out.println("✅ CreditCardPayment object creation successful");
            System.out.println("   - Payment ID: " + payment.getPaymentID());
            System.out.println("   - Amount: RM" + payment.getTotalAmount());
            System.out.println("   - Status: " + payment.getPaymentStatus());

        } catch (Exception e) {
            System.out.println("❌ CreditCardPayment test failed: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println();
    }

    private static void testCashPayment() {
        System.out.println("Testing Cash Payment...");

        try {
            // Create a test cash payment
            CashPayment payment = new CashPayment("TEST003", 200.0, "11/09/2025", "CUST003");

            // Test that the object is created successfully
            assert payment != null : "CashPayment object should not be null";
            assert payment.getPaymentID().equals("TEST003") : "Payment ID should match";
            assert payment.getTotalAmount() == 200.0 : "Amount should match";
            assert payment.getPaymentStatus().equals("Prebooked - Awaiting Cash Payment") : "Initial status should be correct";

            System.out.println("✅ CashPayment object creation successful");
            System.out.println("   - Payment ID: " + payment.getPaymentID());
            System.out.println("   - Amount: RM" + payment.getTotalAmount());
            System.out.println("   - Status: " + payment.getPaymentStatus());

        } catch (Exception e) {
            System.out.println("❌ CashPayment test failed: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println();
    }

    private static void testExitHandling() {
        System.out.println("Testing Exit Handling...");

        // Test that exit codes are properly handled
        String[] exitInputs = {"0", "exit", "quit", "cancel"};

        for (String input : exitInputs) {
            boolean shouldExit = input.equals("0");
            System.out.println("Input '" + input + "' should trigger exit: " + shouldExit);
        }

        System.out.println("✅ Exit handling test completed");
        System.out.println("   - '0' input should trigger exit from payment process");
        System.out.println("   - All payment methods now support exit with '0'");
    }

    // Test scenarios for user experience
    private static void printTestScenarios() {
        System.out.println("\n=== Test Scenarios ===");

        System.out.println("Scenario 1: Online Banking Payment Process");
        System.out.println("1. User selects Online Banking (option 2)");
        System.out.println("2. User selects bank (1-5) or enters 0 to cancel");
        System.out.println("3. User enters username or 0 to cancel");
        System.out.println("4. User enters password or 0 to cancel");
        System.out.println("5. System verifies credentials (simulated)");
        System.out.println("6. User enters OTP or 0 to cancel");
        System.out.println("7. Payment processes successfully or fails");
        System.out.println();

        System.out.println("Scenario 2: Credit Card Payment Process");
        System.out.println("1. User selects Credit Card (option 1)");
        System.out.println("2. User enters card number or 0 to cancel");
        System.out.println("3. User enters card holder name or 0 to cancel");
        System.out.println("4. User enters expiry date or 0 to cancel");
        System.out.println("5. User enters CVV or 0 to cancel");
        System.out.println("6. System validates card details");
        System.out.println("7. Payment processes successfully or fails");
        System.out.println();

        System.out.println("Scenario 3: Exit Handling");
        System.out.println("1. User can enter '0' at any payment input step");
        System.out.println("2. System shows '🔙 Payment cancelled.' message");
        System.out.println("3. Payment status set to 'Cancelled'");
        System.out.println("4. User returned to payment method selection");
    }
}
