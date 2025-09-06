package test;

import hillclimmer.DatabaseModule.CustomerDAO;

/**
 * Simple script to remove the future test user
 */
public class RemoveFutureUser {
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();

        System.out.println("🗑️ Removing Future Test User (C007)");
        System.out.println("==================================");

        try {
            dao.delete("C007");
            System.out.println("✅ Future user C007 removed successfully!");
        } catch (Exception e) {
            System.out.println("❌ Error removing user: " + e.getMessage());
        }

        // Verify the user is gone
        if (dao.load("C007") == null) {
            System.out.println("✅ Verification: C007 no longer exists");
        } else {
            System.out.println("❌ Verification failed: C007 still exists");
        }
    }
}
