/*
 * Fix Customer Data Script
 * Regenerates customer data with proper password hashing
 */
package test;

import hillclimmer.CustomerModule.Customer;
import hillclimmer.DatabaseModule.CustomerDAO;
import java.time.LocalDate;

/**
 * Script to fix customer data with proper password hashing
 * @author las
 */
public class FixCustomerData {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();

        System.out.println("🔧 Fixing Customer Data with Proper Password Hashing");
        System.out.println("==================================================");

        // Clear existing data by recreating the file
        try {
            java.io.File file = new java.io.File("data/customers.csv");
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            System.out.println("✅ Cleared existing customer data");
        } catch (Exception e) {
            System.err.println("❌ Error clearing data: " + e.getMessage());
            return;
        }

        // Recreate all customers with proper password hashing
        createCustomer(customerDAO, "C001", "Muhammad Ali", "950101-14-5678",
            "+60123456789", "muhammad@email.com", "B", LocalDate.of(2026, 12, 31),
            29, "password123", "Original sample user");

        createCustomer(customerDAO, "C002", "Jeremy Clarkson", "850101-01-1234",
            "+60123456780", "jeremy@topgear.com", "B", LocalDate.of(2028, 12, 31),
            39, "TopGear2025!", "Top Gear Presenter");

        createCustomer(customerDAO, "C003", "Richard Hammond", "880202-02-2345",
            "+60123456781", "richard@topgear.com", "B", LocalDate.of(2027, 6, 15),
            35, "HammondRacing!", "Top Gear Enthusiast");

        createCustomer(customerDAO, "C004", "James May", "820303-03-3456",
            "+60123456782", "james@topgear.com", "B", LocalDate.of(2029, 3, 20),
            42, "CaptainSlow!", "Top Gear Engineer");

        createCustomer(customerDAO, "C005", "Sabine Schmitz", "790404-04-4567",
            "+60123456783", "sabine@topgear.com", "B", LocalDate.of(2026, 8, 10),
            45, "QueenOfNurburgring!", "Top Gear Racing Driver");

        createCustomer(customerDAO, "C006", "Chris Evans", "860505-05-5678",
            "+60123456784", "chris@topgear.com", "B", LocalDate.of(2030, 1, 5),
            38, "TopGearHost!", "Top Gear Host");

        System.out.println("\n✅ All customer data regenerated successfully!");
        System.out.println("🔍 Verifying data integrity...");

        // Verify the data
        verifyCustomerData(customerDAO);
    }

    private static void createCustomer(CustomerDAO dao, String id, String name, String ic,
                                     String phone, String email, String licenseType,
                                     LocalDate expiry, int age, String password, String notes) {
        try {
            Customer customer = new Customer(id, name, ic, phone, email, licenseType, expiry, age, password);
            dao.save(customer);

            // Verify the customer was saved with proper hashing
            Customer loaded = dao.load(id);
            if (loaded != null) {
                boolean hasHashedPassword = loaded.getHashedPassword() != null && !loaded.getHashedPassword().isEmpty();
                boolean hasSalt = loaded.getSalt() != null && !loaded.getSalt().isEmpty();

                if (hasHashedPassword && hasSalt) {
                    System.out.println("✅ " + name + " (ID: " + id + ") - Password properly hashed");
                } else {
                    System.out.println("❌ " + name + " (ID: " + id + ") - Password hashing failed");
                }
            } else {
                System.out.println("❌ " + name + " (ID: " + id + ") - Failed to save");
            }

        } catch (Exception e) {
            System.out.println("❌ Error creating " + name + ": " + e.getMessage());
        }
    }

    private static void verifyCustomerData(CustomerDAO dao) {
        System.out.println("\n📊 DATA VERIFICATION RESULTS:");
        System.out.println("============================");

        String[] customerIds = {"C001", "C002", "C003", "C004", "C005", "C006"};
        int totalCustomers = 0;
        int hashedCustomers = 0;

        for (String id : customerIds) {
            Customer customer = dao.load(id);
            if (customer != null) {
                totalCustomers++;
                boolean hasHashedPassword = customer.getHashedPassword() != null && !customer.getHashedPassword().isEmpty();
                boolean hasSalt = customer.getSalt() != null && !customer.getSalt().isEmpty();

                if (hasHashedPassword && hasSalt) {
                    hashedCustomers++;
                    System.out.println("✅ " + customer.getName() + " - Properly secured");
                } else {
                    System.out.println("❌ " + customer.getName() + " - Security issue");
                }
            }
        }

        System.out.println("\n📈 SUMMARY:");
        System.out.println("Total Customers: " + totalCustomers);
        System.out.println("Securely Hashed: " + hashedCustomers);
        System.out.println("Success Rate: " + (totalCustomers > 0 ? (hashedCustomers * 100 / totalCustomers) : 0) + "%");

        if (hashedCustomers == totalCustomers) {
            System.out.println("🎉 All customer data is now properly secured!");
        } else {
            System.out.println("⚠️  Some customers still have security issues.");
        }
    }
}
