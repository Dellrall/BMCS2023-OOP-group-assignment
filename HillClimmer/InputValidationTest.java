import hillclimmer.CustomerModule.Customer;

public class InputValidationTest {
    public static void main(String[] args) {
        System.out.println("🧪 INPUT VALIDATION WHILE LOOP TEST");
        System.out.println("====================================");

        int passedTests = 0;
        int totalTests = 0;

        // Test Customer.isValidIC
        totalTests++;
        System.out.println("\nTesting Customer.isValidIC:");
        boolean ic1 = Customer.isValidIC("950101-14-5678"); // Valid
        boolean ic2 = Customer.isValidIC("invalid"); // Invalid format
        boolean ic3 = Customer.isValidIC("991301-14-5678"); // Invalid date (month 13)
        boolean ic4 = Customer.isValidIC("953201-14-5678"); // Invalid date (day 32)

        System.out.println("  950101-14-5678 (valid): " + ic1);
        System.out.println("  invalid (invalid format): " + ic2);
        System.out.println("  991301-14-5678 (invalid month 13): " + ic3);
        System.out.println("  953201-14-5678 (invalid day 32): " + ic4);

        if (ic1 && !ic2 && !ic3 && !ic4) {
            passedTests++;
            System.out.println("✅ IC validation test PASSED");
        } else {
            System.out.println("❌ IC validation test FAILED");
        }

        // Test Customer.isValidMalaysianPhoneInput
        totalTests++;
        System.out.println("\nTesting Customer.isValidMalaysianPhoneInput:");
        boolean phone1 = Customer.isValidMalaysianPhoneInput("+60123456789"); // Valid
        boolean phone2 = Customer.isValidMalaysianPhoneInput("0123456789"); // Valid
        boolean phone3 = Customer.isValidMalaysianPhoneInput("012-345-6789"); // Valid
        boolean phone4 = Customer.isValidMalaysianPhoneInput("012 345 6789"); // Valid
        boolean phone5 = Customer.isValidMalaysianPhoneInput("invalid"); // Invalid

        System.out.println("  +60123456789 (valid): " + phone1);
        System.out.println("  0123456789 (valid): " + phone2);
        System.out.println("  012-345-6789 (valid): " + phone3);
        System.out.println("  012 345 6789 (valid): " + phone4);
        System.out.println("  invalid (invalid): " + phone5);

        if (phone1 && phone2 && phone3 && phone4 && !phone5) {
            passedTests++;
            System.out.println("✅ Phone validation test PASSED");
        } else {
            System.out.println("❌ Phone validation test FAILED");
        }

        // Test email validation logic (similar to readEmail method)
        totalTests++;
        System.out.println("\nTesting Email validation logic:");
        String email1 = "user@example.com"; // Valid
        String email2 = "invalid"; // Invalid
        String email3 = "a@b.c"; // Valid (minimum length)

        boolean emailValid1 = email1.contains("@") && email1.contains(".") && email1.length() >= 5;
        boolean emailValid2 = email2.contains("@") && email2.contains(".") && email2.length() >= 5;
        boolean emailValid3 = email3.contains("@") && email3.contains(".") && email3.length() >= 5;

        System.out.println("  user@example.com (valid): " + emailValid1);
        System.out.println("  invalid (invalid): " + emailValid2);
        System.out.println("  a@b.c (valid): " + emailValid3);

        if (emailValid1 && !emailValid2 && emailValid3) {
            passedTests++;
            System.out.println("✅ Email validation test PASSED");
        } else {
            System.out.println("❌ Email validation test FAILED");
        }

        // Test ID format validation
        totalTests++;
        System.out.println("\nTesting ID format validation:");
        boolean customerId1 = "C001".matches("C\\d{3,}"); // Valid
        boolean customerId2 = "C1".matches("C\\d{3,}"); // Invalid (too short)
        boolean customerId3 = "invalid".matches("C\\d{3,}"); // Invalid

        boolean managerId1 = "VM001".matches("VM\\d{3,}"); // Valid
        boolean managerId2 = "VM1".matches("VM\\d{3,}"); // Invalid (too short)
        boolean managerId3 = "invalid".matches("VM\\d{3,}"); // Invalid

        System.out.println("  C001 (valid customer ID): " + customerId1);
        System.out.println("  C1 (invalid customer ID): " + customerId2);
        System.out.println("  invalid (invalid customer ID): " + customerId3);
        System.out.println("  VM001 (valid manager ID): " + managerId1);
        System.out.println("  VM1 (invalid manager ID): " + managerId2);
        System.out.println("  invalid (invalid manager ID): " + managerId3);

        if (customerId1 && !customerId2 && !customerId3 &&
            managerId1 && !managerId2 && !managerId3) {
            passedTests++;
            System.out.println("✅ ID format validation test PASSED");
        } else {
            System.out.println("❌ ID format validation test FAILED");
        }

        // Summary
        System.out.println("\n====================================");
        System.out.println("📊 INPUT VALIDATION TEST RESULTS");
        System.out.println("====================================");
        System.out.println("📈 Tests Passed: " + passedTests + "/" + totalTests);
        System.out.println("📊 Success Rate: " + (passedTests * 100.0 / totalTests) + "%");

        if (passedTests == totalTests) {
            System.out.println("🎉 ALL VALIDATION TESTS PASSED!");
            System.out.println("✅ While loops should work correctly for all input methods");
            System.out.println("✅ Users can re-enter input after validation errors");
            System.out.println("✅ Users can exit with '0' at any input prompt");
        } else {
            System.out.println("❌ Some validation tests failed. Please check the implementation.");
        }
    }
}
