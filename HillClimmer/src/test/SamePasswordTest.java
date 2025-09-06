package test;

import hillclimmer.CustomerModule.Customer;
import java.time.LocalDate;

/**
 * Test to verify that changing password to the same value works correctly
 */
public class SamePasswordTest {
    public static void main(String[] args) {
        System.out.println("🔐 Testing Password Change to Same Value");
        System.out.println("=======================================");

        try {
            // Create a test customer
            Customer customer = new Customer("TEST001", "Test User", "950101-14-5678",
                "+60123456789", "test@email.com", "B", LocalDate.of(2026, 12, 31), 29, "TestPass123!");

            System.out.println("✅ Customer created successfully");

            // Get original hash and salt
            String originalHash = customer.getHashedPassword();
            String originalSalt = customer.getSalt();

            System.out.println("📊 Original Hash: " + originalHash.substring(0, 20) + "...");
            System.out.println("🧂 Original Salt: " + originalSalt.substring(0, 20) + "...");

            // Try to change to the same password
            System.out.println("\n🔄 Attempting to change password to same value...");
            customer.updatePassword("TestPass123!");

            // Check if hash and salt changed (they should for security)
            String newHash = customer.getHashedPassword();
            String newSalt = customer.getSalt();

            System.out.println("📊 New Hash: " + newHash.substring(0, 20) + "...");
            System.out.println("🧂 New Salt: " + newSalt.substring(0, 20) + "...");

            if (!originalHash.equals(newHash) && !originalSalt.equals(newSalt)) {
                System.out.println("✅ SUCCESS: Password re-hashed with new salt");
                System.out.println("✅ Same password change is working correctly");
            } else {
                System.out.println("❌ FAILED: Password not properly re-hashed");
            }

            // Verify authentication still works
            boolean authResult = customer.authenticatePassword("TestPass123!");
            if (authResult) {
                System.out.println("✅ Authentication still works after password change");
            } else {
                System.out.println("❌ Authentication failed after password change");
            }

        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
