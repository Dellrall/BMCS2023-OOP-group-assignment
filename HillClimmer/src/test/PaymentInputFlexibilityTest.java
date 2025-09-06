/*
 * Payment Input Flexibility Test
 * Tests flexible input handling for credit card and online banking payments
 */
package test;

import hillclimmer.PaymentModule.CreditCardPayment;
import hillclimmer.PaymentModule.OnlineBankingPayment;

/**
 * Test suite for payment input flexibility
 * @author las
 */
public class PaymentInputFlexibilityTest {

    public static void main(String[] args) {
        System.out.println("💳 PAYMENT INPUT FLEXIBILITY TEST SUITE");
        System.out.println("=======================================");

        int passedTests = 0;
        int totalTests = 0;

        // Test 1: Credit Card Number Normalization
        totalTests++;
        if (testCardNumberNormalization()) {
            passedTests++;
            System.out.println("✅ Test 1 PASSED: Card Number Normalization");
        } else {
            System.out.println("❌ Test 1 FAILED: Card Number Normalization");
        }

        // Test 2: Expiry Date Normalization
        totalTests++;
        if (testExpiryDateNormalization()) {
            passedTests++;
            System.out.println("✅ Test 2 PASSED: Expiry Date Normalization");
        } else {
            System.out.println("❌ Test 2 FAILED: Expiry Date Normalization");
        }

        // Test 3: Username Normalization
        totalTests++;
        if (testUsernameNormalization()) {
            passedTests++;
            System.out.println("✅ Test 3 PASSED: Username Normalization");
        } else {
            System.out.println("❌ Test 3 FAILED: Username Normalization");
        }

        System.out.println("\n📊 Test Results: " + passedTests + "/" + totalTests + " tests passed");
        if (passedTests == totalTests) {
            System.out.println("🎉 All payment input flexibility tests PASSED!");
        } else {
            System.out.println("⚠️  Some tests failed. Please review the implementation.");
        }
    }

    private static boolean testCardNumberNormalization() {
        try {
            CreditCardPayment payment = new CreditCardPayment("TEST001", 100.0, "2024-01-01", "CUST001");

            // Test various input formats
            String[] testInputs = {
                "4111 1111 1111 1111",  // spaces
                "4111-1111-1111-1111",  // dashes
                "4111111111111111"      // no separators
            };

            String expected = "4111111111111111";

            for (String input : testInputs) {
                String normalized = payment.normalizeCardNumber(input);
                if (!normalized.equals(expected)) {
                    System.out.println("  Expected: " + expected + ", Got: " + normalized);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("  Exception: " + e.getMessage());
            return false;
        }
    }

    private static boolean testExpiryDateNormalization() {
        try {
            CreditCardPayment payment = new CreditCardPayment("TEST001", 100.0, "2024-01-01", "CUST001");

            // Test various input formats
            String[] testInputs = {
                "12/25",  // standard format
                "12-25",  // dash separator
                "12 25"   // space separator
            };

            String expected = "12/25";

            for (String input : testInputs) {
                String normalized = payment.normalizeExpiryDate(input);
                if (!normalized.equals(expected)) {
                    System.out.println("  Expected: " + expected + ", Got: " + normalized);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("  Exception: " + e.getMessage());
            return false;
        }
    }

    private static boolean testUsernameNormalization() {
        try {
            OnlineBankingPayment payment = new OnlineBankingPayment("TEST001", 100.0, "2024-01-01", "CUST001");

            // Test username with extra spaces
            String input = "  myusername  ";
            String expected = "myusername";

            String normalized = payment.normalizeUsername(input);
            if (!normalized.equals(expected)) {
                System.out.println("  Expected: " + expected + ", Got: " + normalized);
                return false;
            }
            return true;
        } catch (Exception e) {
            System.out.println("  Exception: " + e.getMessage());
            return false;
        }
    }
}
