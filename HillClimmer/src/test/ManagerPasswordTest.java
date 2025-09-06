package test;

import hillclimmer.DatabaseModule.Manager;

/**
 * Test to verify that manager password change works correctly, including same password
 */
public class ManagerPasswordTest {
    public static void main(String[] args) {
        System.out.println("🔐 Testing Manager Password Change");
        System.out.println("==================================");

        try {
            // Create a test manager
            Manager manager = new Manager("VM001", "Test Manager", 3);
            manager.setPassword("ManagerPass123!");

            System.out.println("✅ Manager created successfully");

            // Get original hash and salt
            String originalHash = manager.getHashedPassword();
            String originalSalt = manager.getSalt();

            System.out.println("📊 Original Hash: " + originalHash.substring(0, 20) + "...");
            System.out.println("🧂 Original Salt: " + originalSalt.substring(0, 20) + "...");

            // Test changing to the same password
            System.out.println("\n🔄 Attempting to change password to same value...");
            manager.updatePassword("ManagerPass123!");

            // Check if hash and salt changed (they should for security)
            String newHash = manager.getHashedPassword();
            String newSalt = manager.getSalt();

            System.out.println("📊 New Hash: " + newHash.substring(0, 20) + "...");
            System.out.println("🧂 New Salt: " + newSalt.substring(0, 20) + "...");

            if (!originalHash.equals(newHash) && !originalSalt.equals(newSalt)) {
                System.out.println("✅ SUCCESS: Password re-hashed with new salt");
                System.out.println("✅ Same password change is working correctly for managers");
            } else {
                System.out.println("❌ FAILED: Password not properly re-hashed");
            }

            // Verify authentication still works
            boolean authResult = manager.authenticatePassword("ManagerPass123!");
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
