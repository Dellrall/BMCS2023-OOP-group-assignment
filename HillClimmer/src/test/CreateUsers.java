/*
 * User Creation Script
 * Creates sample users for testing the HillClimmer system
 */
package test;

import hillclimmer.CustomerModule.Customer;
import hillclimmer.DatabaseModule.CustomerDAO;
import java.time.LocalDate;

/**
 * Script to create sample users for testing
 * @author las
 */
public class CreateUsers {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();

        System.out.println("🚗 Creating Sample Customers (Top Gear Personalities)");
        System.out.println("==================================================");

        // Top Gear inspired customers
        createCustomer(customerDAO, "C002", "Jeremy Clarkson", "850101-01-1234", "+60123456780", "jeremy@topgear.com", "B", LocalDate.of(2028, 12, 31), 39, "TopGear2025!");
        createCustomer(customerDAO, "C003", "Richard Hammond", "880202-02-2345", "+60123456781", "richard@topgear.com", "B", LocalDate.of(2027, 6, 15), 35, "HammondRacing123!");
        createCustomer(customerDAO, "C004", "James May", "820303-03-3456", "+60123456782", "james@topgear.com", "B", LocalDate.of(2029, 3, 20), 42, "CaptainSlow456!");
        createCustomer(customerDAO, "C005", "Sabine Schmitz", "790404-04-4567", "+60123456783", "sabine@topgear.com", "B", LocalDate.of(2026, 8, 10), 45, "QueenOfNurburgring789!");
        createCustomer(customerDAO, "C006", "Chris Evans", "860505-05-5678", "+60123456784", "chris@topgear.com", "B", LocalDate.of(2030, 1, 5), 38, "TopGearHostABC!");

        System.out.println("\n✅ Sample customers created successfully!");
        System.out.println("You can now login with the provided credentials.");
    }

    private static void createCustomer(CustomerDAO dao, String id, String name, String ic,
                                     String phone, String email, String licenseType,
                                     LocalDate expiry, int age, String password) {
        try {
            Customer customer = new Customer(id, name, ic, phone, email, licenseType, expiry, age, password);
            dao.save(customer);
            System.out.println("✅ Created: " + name + " (ID: " + id + ", Password: " + password + ")");
        } catch (Exception e) {
            System.out.println("❌ Error creating " + name + ": " + e.getMessage());
        }
    }
}
