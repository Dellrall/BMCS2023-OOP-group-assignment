package test;

import hillclimmer.DatabaseModule.CustomerDAO;

public class TestNewIdGeneration {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();

        System.out.println("=== Testing New ID Generation Method ===");

        // Test the new generateNextCustomerId method
        String nextId = customerDAO.generateNextCustomerId();
        System.out.println("Next customer ID should be: " + nextId);

        // Check current customer count
        int currentCount = customerDAO.getAll().size();
        System.out.println("Current customer count: " + currentCount);

        // Verify the ID is correct
        if (currentCount > 0) {
            // Find the max existing ID
            int maxId = customerDAO.getAll().stream()
                    .mapToInt(c -> {
                        try {
                            return Integer.parseInt(c.getCustomerID().substring(1));
                        } catch (NumberFormatException e) {
                            return 0;
                        }
                    })
                    .max()
                    .orElse(0);

            String expectedId = "C" + String.format("%03d", maxId + 1);
            System.out.println("Expected ID based on max existing: " + expectedId);

            if (nextId.equals(expectedId)) {
                System.out.println("✅ ID generation is correct!");
            } else {
                System.out.println("❌ ID generation mismatch!");
            }
        } else {
            if (nextId.equals("C001")) {
                System.out.println("✅ ID generation is correct for empty database!");
            } else {
                System.out.println("❌ ID generation should be C001 for empty database!");
            }
        }
    }
}
