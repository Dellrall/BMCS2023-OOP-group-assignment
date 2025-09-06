/*
 * Password Change CSV Update Test
 * Tests that password changes properly update the CSV file
 */
package test;

import hillclimmer.CustomerModule.Customer;
import hillclimmer.DatabaseModule.CustomerDAO;
import java.time.LocalDate;

public class PasswordChangeCSVTest {
    public static void main(String[] args) {
        System.out.println("🔐 PASSWORD CHANGE CSV UPDATE TEST");
        System.out.println("===================================");

        CustomerDAO customerDAO = new CustomerDAO();
        int passedTests = 0;
        int totalTests = 0;

        try {
            // Test 1: Load existing customer
            totalTests++;
            System.out.println("\n1. Loading existing customer from CSV...");
            Customer customer = customerDAO.load("C001");

            if (customer != null) {
                System.out.println("   ✅ Customer loaded successfully");
                System.out.println("   📋 Original hashed password: " +
                    (customer.getHashedPassword() != null ?
                     customer.getHashedPassword().substring(0, 20) + "..." :
                     "null"));
                System.out.println("   🧂 Original salt: " +
                    (customer.getSalt() != null ?
                     customer.getSalt().substring(0, 20) + "..." :
                     "null"));
                passedTests++;
            } else {
                System.out.println("   ❌ Failed to load customer");
                return;
            }

            // Test 2: Verify current password works
            totalTests++;
            System.out.println("\n2. Testing current password authentication...");
            // Note: We don't know the original password, so we'll test with a known password
            // Let's create a test customer with a known password instead
            Customer testCustomer = new Customer("PCT001", "Password Test", "900101-01-1234",
                "+60123456789", "password@test.com", "B", LocalDate.of(2030, 12, 31), 25, "OriginalPass123!");

            customerDAO.save(testCustomer);

            boolean originalAuth = testCustomer.authenticatePassword("OriginalPass123!");
            if (originalAuth) {
                System.out.println("   ✅ Original password authentication works");
                passedTests++;
            } else {
                System.out.println("   ❌ Original password authentication failed");
            }

            // Test 3: Change password
            totalTests++;
            System.out.println("\n3. Changing password...");
            String originalHash = testCustomer.getHashedPassword();
            String originalSalt = testCustomer.getSalt();

            testCustomer.updatePassword("NewSecurePass456!");

            if (!originalHash.equals(testCustomer.getHashedPassword()) &&
                !originalSalt.equals(testCustomer.getSalt())) {
                System.out.println("   ✅ Password hash and salt updated");
                passedTests++;
            } else {
                System.out.println("   ❌ Password hash/salt not updated properly");
            }

            // Test 4: Save to CSV and reload
            totalTests++;
            System.out.println("\n4. Saving updated customer to CSV...");
            customerDAO.update(testCustomer);

            Customer reloaded = customerDAO.load("PCT001");
            if (reloaded != null) {
                System.out.println("   ✅ Customer reloaded from CSV successfully");
                System.out.println("   📋 New hashed password: " +
                    (reloaded.getHashedPassword() != null ?
                     reloaded.getHashedPassword().substring(0, 20) + "..." :
                     "null"));
                System.out.println("   🧂 New salt: " +
                    (reloaded.getSalt() != null ?
                     reloaded.getSalt().substring(0, 20) + "..." :
                     "null"));
                passedTests++;
            } else {
                System.out.println("   ❌ Failed to reload customer from CSV");
            }

            // Test 5: Verify new password works, old doesn't
            totalTests++;
            System.out.println("\n5. Testing password authentication after change...");
            boolean newAuth = reloaded.authenticatePassword("NewSecurePass456!");
            boolean oldAuth = reloaded.authenticatePassword("OriginalPass123!");

            if (newAuth && !oldAuth) {
                System.out.println("   ✅ New password works, old password rejected");
                passedTests++;
            } else {
                System.out.println("   ❌ Password authentication failed after change");
                System.out.println("   📊 New auth: " + newAuth + ", Old auth: " + oldAuth);
            }

            // Test 6: Check CSV file integrity
            totalTests++;
            System.out.println("\n6. Verifying CSV file integrity...");
            try {
                java.util.List<Customer> allCustomers = customerDAO.getAll();
                boolean csvValid = true;
                for (Customer c : allCustomers) {
                    if (c.getCustomerID() == null || c.getName() == null) {
                        csvValid = false;
                        break;
                    }
                }

                if (csvValid) {
                    System.out.println("   ✅ CSV file integrity maintained");
                    System.out.println("   📊 Total customers in CSV: " + allCustomers.size());
                    passedTests++;
                } else {
                    System.out.println("   ❌ CSV file integrity compromised");
                }
            } catch (Exception e) {
                System.out.println("   ❌ CSV parsing error: " + e.getMessage());
            }

            // Cleanup
            System.out.println("\n🧹 Cleaning up test data...");
            customerDAO.delete("PCT001");

        } catch (Exception e) {
            System.err.println("❌ Test failed with exception: " + e.getMessage());
            e.printStackTrace();
        }

        // Results
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 PASSWORD CHANGE CSV TEST RESULTS");
        System.out.println("=".repeat(50));
        System.out.println("✅ Tests Passed: " + passedTests + "/" + totalTests);
        System.out.println("📈 Success Rate: " + String.format("%.1f", (passedTests * 100.0) / totalTests) + "%");

        if (passedTests == totalTests) {
            System.out.println("🎉 ALL PASSWORD CHANGE CSV TESTS PASSED!");
            System.out.println("✅ Password changes properly update CSV");
            System.out.println("✅ No parsing errors occur");
            System.out.println("✅ CSV integrity maintained");
        } else {
            System.out.println("⚠️ SOME TESTS FAILED! Password change CSV functionality needs fixes.");
        }
    }
}
