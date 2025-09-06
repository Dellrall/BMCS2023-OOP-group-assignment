/*
 * Password Change Functionality Test
 * Tests the password change feature with proper hashing and verification
 */
package test;

import hillclimmer.CustomerModule.Customer;
import java.time.LocalDate;

public class PasswordChangeTest {
    public static void main(String[] args) {
        System.out.println("🔐 PASSWORD CHANGE FUNCTIONALITY TEST");
        System.out.println("====================================");

        // CustomerDAO customerDAO = new CustomerDAO(); // Not needed for this test
        int passedTests = 0;
        int totalTests = 0;

        try {
            // Test 1: Create test customer
            totalTests++;
            System.out.println("\n1. Creating test customer...");
            Customer testCustomer = new Customer("PCT001", "Password Test", "900101-01-1234",
                "+60123456789", "password@test.com", "B", LocalDate.of(2030, 12, 31), 25, "OriginalPass123!");

            // Verify initial password works
            boolean initialAuth = testCustomer.authenticatePassword("OriginalPass123!");
            if (initialAuth) {
                System.out.println("   ✅ Initial password authentication works");
                passedTests++;
            } else {
                System.out.println("   ❌ Initial password authentication failed");
            }

            // Test 2: Update password
            totalTests++;
            System.out.println("\n2. Testing password update...");
            testCustomer.updatePassword("NewSecurePass456!");

            // Verify old password no longer works
            boolean oldAuth = testCustomer.authenticatePassword("OriginalPass123!");
            boolean newAuth = testCustomer.authenticatePassword("NewSecurePass456!");

            if (!oldAuth && newAuth) {
                System.out.println("   ✅ Password update successful - old password rejected, new password accepted");
                passedTests++;
            } else {
                System.out.println("   ❌ Password update failed - old: " + oldAuth + ", new: " + newAuth);
            }

            // Test 3: Verify hashed password is set
            totalTests++;
            System.out.println("\n3. Testing password hashing...");
            if (testCustomer.getHashedPassword() != null && testCustomer.getSalt() != null &&
                testCustomer.getPassword() == null) {
                System.out.println("   ✅ Password properly hashed and plain text cleared");
                passedTests++;
            } else {
                System.out.println("   ❌ Password hashing failed");
            }

            // Test 4: Test password change with same password
            totalTests++;
            System.out.println("\n4. Testing password change to same value...");
            String originalHash = testCustomer.getHashedPassword();
            String originalSalt = testCustomer.getSalt();

            testCustomer.updatePassword("NewSecurePass456!"); // Same password

            if (!originalHash.equals(testCustomer.getHashedPassword()) ||
                !originalSalt.equals(testCustomer.getSalt())) {
                System.out.println("   ✅ Password re-hashing with new salt on same password");
                passedTests++;
            } else {
                System.out.println("   ❌ Password not re-hashed with new salt");
            }

            // Test 5: Test invalid password (too short)
            totalTests++;
            System.out.println("\n5. Testing invalid password (too short)...");
            try {
                testCustomer.updatePassword("12345"); // Too short
                System.out.println("   ❌ Should have thrown exception for short password");
            } catch (IllegalArgumentException e) {
                System.out.println("   ✅ Properly rejected short password: " + e.getMessage());
                passedTests++;
            }

            // Test 6: Test invalid password (no hex)
            totalTests++;
            System.out.println("\n6. Testing invalid password (no hex characters)...");
            System.out.println("   Current customer password hash: " + (testCustomer.getHashedPassword() != null ? "exists" : "null"));
            try {
                testCustomer.updatePassword("XYZGHI!"); // No hex digits (0-9, a-f, A-F)
                System.out.println("   ❌ Should have thrown exception for password without hex");
            } catch (IllegalArgumentException e) {
                System.out.println("   ✅ Properly rejected password without hex: " + e.getMessage());
                passedTests++;
            }

            // Test 7: Test invalid password (no symbol)
            totalTests++;
            System.out.println("\n7. Testing invalid password (no symbol characters)...");
            try {
                testCustomer.updatePassword("Password123"); // No symbols
                System.out.println("   ❌ Should have thrown exception for password without symbols");
            } catch (IllegalArgumentException e) {
                System.out.println("   ✅ Properly rejected password without symbols: " + e.getMessage());
                passedTests++;
            }

            // Test 8: Test null password
            totalTests++;
            System.out.println("\n8. Testing null password...");
            try {
                testCustomer.updatePassword(null);
                System.out.println("   ❌ Should have thrown exception for null password");
            } catch (IllegalArgumentException e) {
                System.out.println("   ✅ Properly rejected null password: " + e.getMessage());
                passedTests++;
            }

        } catch (Exception e) {
            System.err.println("❌ Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        }

        // Results
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 PASSWORD CHANGE TEST RESULTS");
        System.out.println("=".repeat(50));
        System.out.println("✅ Tests Passed: " + passedTests + "/" + totalTests);
        System.out.println("📈 Success Rate: " + String.format("%.1f", (passedTests * 100.0) / totalTests) + "%");

        if (passedTests == totalTests) {
            System.out.println("🎉 ALL PASSWORD CHANGE TESTS PASSED!");
            System.out.println("✅ Password change functionality is working correctly");
            System.out.println("✅ Password hashing and verification aligned");
            System.out.println("✅ Security issues resolved");
        } else {
            System.out.println("⚠️ SOME TESTS FAILED! Password change functionality needs fixes.");
        }
    }
}
