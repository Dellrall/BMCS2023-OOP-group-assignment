package test;

import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.CustomerModule.Customer;
import java.time.LocalDate;

public class TestRegistrationWithNewId {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();

        System.out.println("=== Testing Registration with New ID Generation ===");

        // Check current state
        int initialCount = customerDAO.getAll().size();
        System.out.println("Initial customer count: " + initialCount);

        // Simulate registration process
        String customerId = customerDAO.generateNextCustomerId();
        System.out.println("Generated customer ID: " + customerId);

        // Create test customer
        Customer testCustomer = new Customer(customerId, "Test Registration User",
            "900101-01-1234", "+60123456785", "registration@test.com", "B",
            LocalDate.of(2026, 12, 31), 25, "TestPass123!");

        // Save the customer
        customerDAO.save(testCustomer);

        // Verify
        int finalCount = customerDAO.getAll().size();
        System.out.println("Final customer count: " + finalCount);

        Customer loaded = customerDAO.load(customerId);
        if (loaded != null) {
            System.out.println("✅ Registration successful! Customer ID: " + loaded.getCustomerID());
        } else {
            System.out.println("❌ Registration failed!");
        }

        // Test next ID generation
        String nextId = customerDAO.generateNextCustomerId();
        System.out.println("Next available ID: " + nextId);
    }
}
