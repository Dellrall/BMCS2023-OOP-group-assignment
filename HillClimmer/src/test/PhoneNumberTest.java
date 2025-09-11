package test;

import hillclimmer.CustomerModule.Customer;

/**
 * Test phone number validation with new flexible format
 */
public class PhoneNumberTest {
    public static void main(String[] args) {
        System.out.println("🧪 PHONE NUMBER VALIDATION TEST");
        System.out.println("===============================");

        // Test cases for different phone number formats and lengths
        String[] testCases = {
            // Valid cases (10-11 digits total)
            "0123456789",      // 10 digits
            "+60123456789",    // 11 digits with +60
            "01234567890",     // 11 digits (should be valid now)
            "0134567890",      // 10 digits
            "+60134567890",    // 11 digits with +60
            "012-345-6789",    // 10 digits with dashes
            "012 345 6789",    // 10 digits with spaces
            "0123-456-7890",   // 11 digits with dashes
            "0123 456 7890",   // 11 digits with spaces

            // Invalid cases
            "012345678",       // 9 digits (too short)
            "01234567",        // 8 digits (too short)
            "012345678901",    // 12 digits (too long)
            "1123456789",      // starts with 1 (invalid prefix)
            "0223456789",      // starts with 02 (invalid area code)
            "abc1234567",      // contains letters
            "012-345-67",      // incomplete
            "",                // empty
            "0123456789abc"    // with letters
        };

        System.out.println("✅ VALID PHONE NUMBERS:");
        for (String phone : testCases) {
            boolean isValid = Customer.isValidMalaysianPhoneInput(phone);
            if (isValid) {
                String normalized = Customer.normalizeMalaysianPhone(phone);
                System.out.println("   ✓ " + phone + " → " + normalized);
            }
        }

        System.out.println("\n❌ INVALID PHONE NUMBERS:");
        for (String phone : testCases) {
            boolean isValid = Customer.isValidMalaysianPhoneInput(phone);
            if (!isValid && !phone.isEmpty()) {
                System.out.println("   ✗ " + phone);
            }
        }

        System.out.println("\n===============================");
        System.out.println("🎉 PHONE NUMBER TEST COMPLETED!");

        // Test normalization
        System.out.println("\n🔄 NORMALIZATION EXAMPLES:");
        String[] normalizeTests = {
            "0123456789",
            "+60123456789",
            "012-345-6789",
            "012 345 6789"
        };

        for (String phone : normalizeTests) {
            if (Customer.isValidMalaysianPhoneInput(phone)) {
                String normalized = Customer.normalizeMalaysianPhone(phone);
                System.out.println("   " + phone + " → " + normalized);
            }
        }
    }
}
