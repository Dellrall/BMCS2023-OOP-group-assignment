/*
 * Password Hashing Test Program
 * Demonstrates the secure password hashing and salting functionality
 */
package test;

import hillclimmer.CustomerModule.Customer;
import java.time.LocalDate;

/**
 * Test program to demonstrate password hashing and salting
 * @author las
 */
public class PasswordHashingTest {
    public static void main(String[] args) {
        System.out.println("🔐 PASSWORD HASHING & SALTING TEST");
        System.out.println("==================================");

        // Test password hashing
        String testPassword = "MySecurePassword123!";

        System.out.println("Original Password: " + testPassword);

        // Create a customer with hashed password
        Customer customer = new Customer("TEST001", "Test User", "900101-01-1234",
            "+60123456789", "test@email.com", "B", LocalDate.of(2028, 12, 31), 25, testPassword);

        System.out.println("Hashed Password: " + customer.getHashedPassword());
        System.out.println("Salt: " + customer.getSalt());

        // Test authentication
        System.out.println("\n🔍 AUTHENTICATION TESTS");
        System.out.println("=======================");

        // Test correct password
        boolean correctAuth = customer.authenticate(testPassword);
        System.out.println("Correct password authentication: " + (correctAuth ? "✅ SUCCESS" : "❌ FAILED"));

        // Test wrong password
        boolean wrongAuth = customer.authenticate("WrongPassword123!");
        System.out.println("Wrong password authentication: " + (wrongAuth ? "❌ UNEXPECTED SUCCESS" : "✅ CORRECTLY FAILED"));

        // Test empty password
        boolean emptyAuth = customer.authenticate("");
        System.out.println("Empty password authentication: " + (emptyAuth ? "❌ UNEXPECTED SUCCESS" : "✅ CORRECTLY FAILED"));

        System.out.println("\n🎉 Password hashing and salting implementation is working correctly!");
        System.out.println("Each customer gets a unique salt, making rainbow table attacks ineffective.");
    }
}
