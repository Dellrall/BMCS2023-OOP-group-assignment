/*
 * Simple Customer Test
 * Tests customer creation and data integrity
 */
package test;

import hillclimmer.CustomerModule.Customer;
import java.time.LocalDate;

/**
 * Simple test to check customer data
 * @author las
 */
public class SimpleCustomerTest {
    public static void main(String[] args) {
        System.out.println("🧪 Simple Customer Test");
        System.out.println("=======================");

        // Create a customer
        Customer customer = new Customer("TEST001", "Test User", "900101-01-1234",
            "+60123456789", "test@email.com", "B", LocalDate.of(2028, 12, 31), 25, "TestPassword123!");

        // Check the data
        System.out.println("Customer ID: " + customer.getCustomerID());
        System.out.println("Name: " + customer.getName());
        System.out.println("Hashed Password: " + customer.getHashedPassword());
        System.out.println("Salt: " + customer.getSalt());
        System.out.println("Plain Password: " + customer.getPassword());

        // Test authentication
        boolean auth = customer.authenticate("TestPassword123!");
        System.out.println("Authentication: " + (auth ? "✅ SUCCESS" : "❌ FAILED"));

        // Check field values
        System.out.println("\nField Analysis:");
        System.out.println("Hashed Password null: " + (customer.getHashedPassword() == null));
        System.out.println("Salt null: " + (customer.getSalt() == null));
        System.out.println("Hashed Password empty: " + (customer.getHashedPassword() != null && customer.getHashedPassword().isEmpty()));
        System.out.println("Salt empty: " + (customer.getSalt() != null && customer.getSalt().isEmpty()));
    }
}
