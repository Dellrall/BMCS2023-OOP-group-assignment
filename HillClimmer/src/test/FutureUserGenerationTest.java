/*
 * Future User Generation Test
 * Tests that new customer creation maintains consistency with existing data
 */
package test;

import hillclimmer.CustomerModule.Customer;
import hillclimmer.DatabaseModule.CustomerDAO;
import java.time.LocalDate;

/**
 * Test to verify future user generation consistency
 * @author las
 */
public class FutureUserGenerationTest {
    public static void main(String[] args) {
        System.out.println("🔮 Future User Generation Consistency Test");
        System.out.println("==========================================");

        CustomerDAO customerDAO = new CustomerDAO();

        // Create a new customer to test future generation
        Customer newCustomer = new Customer("C007", "Future User", "900505-05-6789",
            "+60123456785", "future@email.com", "B", LocalDate.of(2030, 12, 31), 35, "FuturePass123!");

        System.out.println("🆕 New Customer Details:");
        System.out.println("ID: " + newCustomer.getCustomerID());
        System.out.println("Name: " + newCustomer.getName());
        System.out.println("Hashed Password: " + newCustomer.getHashedPassword());
        System.out.println("Salt: " + newCustomer.getSalt());
        System.out.println("Plain Password: " + newCustomer.getPassword());

        // Test authentication
        boolean auth = newCustomer.authenticatePassword("FuturePass123!");
        System.out.println("Authentication: " + (auth ? "✅ SUCCESS" : "❌ FAILED"));

        // Save the customer
        customerDAO.save(newCustomer);
        System.out.println("✅ Customer saved to database");

        // Load and verify
        Customer loaded = customerDAO.load("C007");
        if (loaded != null) {
            System.out.println("\n🔍 Verification from Database:");
            System.out.println("Loaded Name: " + loaded.getName());
            System.out.println("Loaded Hashed Password: " + loaded.getHashedPassword());
            System.out.println("Loaded Salt: " + loaded.getSalt());
            System.out.println("Plain Password Cleared: " + (loaded.getPassword() == null));

            boolean loadAuth = loaded.authenticatePassword("FuturePass123!");
            System.out.println("Loaded Authentication: " + (loadAuth ? "✅ SUCCESS" : "❌ FAILED"));

        // Check CSV format consistency by reading the file
        try {
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader("data/customers.csv"));
            String lastLine = "";
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
            reader.close();

            System.out.println("\n📄 CSV Format Check:");
            System.out.println("Last CSV Line: " + lastLine);

            // Verify CSV contains proper Base64 strings for the new customer
            boolean hasQuotedHash = lastLine.contains("\"" + loaded.getHashedPassword() + "\"");
            boolean hasQuotedSalt = lastLine.contains("\"" + loaded.getSalt() + "\"");
            System.out.println("Has Quoted Hash: " + (hasQuotedHash ? "✅ YES" : "❌ NO"));
            System.out.println("Has Quoted Salt: " + (hasQuotedSalt ? "✅ YES" : "❌ NO"));

            if (hasQuotedHash && hasQuotedSalt && loadAuth) {
                System.out.println("\n🎉 FUTURE USER GENERATION IS CONSISTENT!");
                System.out.println("✅ Password hashing: Working");
                System.out.println("✅ Salt generation: Working");
                System.out.println("✅ Authentication: Working");
                System.out.println("✅ CSV format: Consistent");
                System.out.println("✅ Data integrity: Maintained");
            } else {
                System.out.println("\n⚠️  CONSISTENCY ISSUES DETECTED!");
            }

        } catch (Exception e) {
            System.out.println("❌ Error reading CSV file: " + e.getMessage());
        }

        } else {
            System.out.println("❌ Failed to load customer from database");
        }
    }
}
