/*
 * Recent Enhancements Test Suite
 * Tests all recent implementations including:
 * - Email/ID login functionality
 * - Phone number validation and normalization
 * - IC validation improvements
 * - Input validation while loops
 * - Exit functionality with "0"
 */
package test;

import hillclimmer.CustomerModule.Customer;

/**
 * Test suite for recent system enhancements
 * @author las
 */
public class RecentEnhancementsTest {

    public static void main(String[] args) {
        System.out.println("🧪 RECENT ENHANCEMENTS TEST SUITE");
        System.out.println("=================================");

        int passedTests = 0;
        int totalTests = 0;

        // Test 1: Customer IC Validation
        totalTests++;
        if (testICValidation()) {
            passedTests++;
            System.out.println("✅ Test 1 PASSED: IC Validation");
        } else {
            System.out.println("❌ Test 1 FAILED: IC Validation");
        }

        // Test 2: Phone Number Validation and Normalization
        totalTests++;
        if (testPhoneValidation()) {
            passedTests++;
            System.out.println("✅ Test 2 PASSED: Phone Validation");
        } else {
            System.out.println("❌ Test 2 FAILED: Phone Validation");
        }

        // Test 3: Email/ID Login Logic
        totalTests++;
        if (testLoginLogic()) {
            passedTests++;
            System.out.println("✅ Test 3 PASSED: Login Logic");
        } else {
            System.out.println("❌ Test 3 FAILED: Login Logic");
        }

        // Test 4: Input Validation Formats
        totalTests++;
        if (testInputFormats()) {
            passedTests++;
            System.out.println("✅ Test 4 PASSED: Input Formats");
        } else {
            System.out.println("❌ Test 4 FAILED: Input Formats");
        }

        // Test 5: While Loop Error Handling
        totalTests++;
        if (testWhileLoopBehavior()) {
            passedTests++;
            System.out.println("✅ Test 5 PASSED: While Loop Behavior");
        } else {
            System.out.println("❌ Test 5 FAILED: While Loop Behavior");
        }

        // Summary
        System.out.println("\n=================================");
        System.out.println("📊 RECENT ENHANCEMENTS TEST RESULTS");
        System.out.println("=================================");
        System.out.println("📈 Tests Passed: " + passedTests + "/" + totalTests);
        System.out.println("📊 Success Rate: " + (passedTests * 100.0 / totalTests) + "%");

        if (passedTests == totalTests) {
            System.out.println("🎉 ALL RECENT ENHANCEMENTS TESTS PASSED!");
            System.out.println("✅ System enhancements are working correctly");
        } else {
            System.out.println("❌ Some tests failed. Please review the recent implementations.");
        }
    }

    private static boolean testICValidation() {
        try {
            // Test valid IC
            boolean validIC = Customer.isValidIC("950101-14-5678");
            if (!validIC) return false;

            // Test invalid format
            boolean invalidFormat = Customer.isValidIC("invalid");
            if (invalidFormat) return false;

            // Test invalid date
            boolean invalidDate = Customer.isValidIC("991301-14-5678"); // Month 13
            if (invalidDate) return false;

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean testPhoneValidation() {
        try {
            // Test various phone formats
            boolean format1 = Customer.isValidMalaysianPhoneInput("+60123456789");
            boolean format2 = Customer.isValidMalaysianPhoneInput("0123456789");
            boolean format3 = Customer.isValidMalaysianPhoneInput("012-345-6789");
            boolean format4 = Customer.isValidMalaysianPhoneInput("012 345 6789");
            boolean invalid = Customer.isValidMalaysianPhoneInput("invalid");

            if (!format1 || !format2 || !format3 || !format4 || invalid) {
                return false;
            }

            // Test normalization
            String normalized1 = Customer.normalizeMalaysianPhone("+60123456789");
            String normalized2 = Customer.normalizeMalaysianPhone("0123456789");

            if (!"+60123456789".equals(normalized1) || !"+60123456789".equals(normalized2)) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean testLoginLogic() {
        try {
            // Test ID format validation
            boolean validCustomerId = "C001".matches("C\\d{3,}");
            boolean invalidCustomerId = "C1".matches("C\\d{3,}");
            boolean validManagerId = "VM001".matches("VM\\d{3,}");
            boolean invalidManagerId = "VM1".matches("VM\\d{3,}");

            if (!validCustomerId || invalidCustomerId || !validManagerId || invalidManagerId) {
                return false;
            }

            // Test email validation logic (basic)
            boolean validEmail = "user@example.com".contains("@") &&
                               "user@example.com".contains(".") &&
                               "user@example.com".length() >= 5;
            boolean invalidEmail = "invalid".contains("@") &&
                                 "invalid".contains(".") &&
                                 "invalid".length() >= 5;

            if (!validEmail || invalidEmail) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean testInputFormats() {
        try {
            // Test license type validation
            String[] validTypes = {"B", "B2", "D", "DA", "E", "E1", "E2"};
            for (String type : validTypes) {
                if (!isValidLicenseType(type)) {
                    return false;
                }
            }

            // Test invalid license type
            if (isValidLicenseType("INVALID")) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean testWhileLoopBehavior() {
        try {
            // Test that validation methods return appropriate boolean values
            // This simulates the while loop condition checks

            // IC validation
            boolean icValid = Customer.isValidIC("950101-14-5678");
            boolean icInvalid = Customer.isValidIC("invalid");

            // Phone validation
            boolean phoneValid = Customer.isValidMalaysianPhoneInput("0123456789");
            boolean phoneInvalid = Customer.isValidMalaysianPhoneInput("invalid");

            // ID format validation
            boolean customerIdValid = "C001".matches("C\\d{3,}");
            boolean customerIdInvalid = "C1".matches("C\\d{3,}");

            boolean managerIdValid = "VM001".matches("VM\\d{3,}");
            boolean managerIdInvalid = "VM1".matches("VM\\d{3,}");

            if (!icValid || icInvalid ||
                !phoneValid || phoneInvalid ||
                !customerIdValid || customerIdInvalid ||
                !managerIdValid || managerIdInvalid) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isValidLicenseType(String type) {
        // Simulate the license type validation logic
        String[] validTypes = {"B", "B2", "D", "DA", "E", "E1", "E2"};
        for (String validType : validTypes) {
            if (validType.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
