package test;

import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.CustomerModule.Customer;
import java.time.LocalDate;

public class TestRegistrationIssue {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();

        System.out.println("=== Testing Registration ID Generation ===");

        // Check current customer count
        int currentCount = customerDAO.getAll().size();
        System.out.println("Current customer count: " + currentCount);

        // Simulate ID generation like in registration
        String generatedId = "C" + String.format("%03d", currentCount + 1);
        System.out.println("Generated ID should be: " + generatedId);

        // Create a test customer
        Customer testCustomer = new Customer(generatedId, "Test User", "900101-01-1234",
            "+60123456785", "test@email.com", "B", LocalDate.of(2026, 12, 31), 25, "TestPass123!");

        System.out.println("Test customer ID before save: " + testCustomer.getCustomerID());

        // Save the customer
        customerDAO.save(testCustomer);

        System.out.println("Test customer ID after save: " + testCustomer.getCustomerID());

        // Check final count
        int finalCount = customerDAO.getAll().size();
        System.out.println("Final customer count: " + finalCount);

        // Load the saved customer to verify
        Customer loaded = customerDAO.load(generatedId);
        if (loaded != null) {
            System.out.println("✅ Successfully loaded customer with ID: " + loaded.getCustomerID());
        } else {
            System.out.println("❌ Failed to load customer with ID: " + generatedId);
        }
    }
}
