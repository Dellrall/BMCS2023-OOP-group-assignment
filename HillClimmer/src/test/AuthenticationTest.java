/*
 * Authentication System Test
 * Tests the complete authentication flow for customers and managers
 */
package test;

import hillclimmer.CustomerModule.Customer;
import hillclimmer.DatabaseModule.CustomerDAO;

/**
 * Comprehensive test for the authentication system
 * @author las
 */
public class AuthenticationTest {
    public static void main(String[] args) {
        System.out.println("🔐 Authentication System Test");
        System.out.println("============================");

        CustomerDAO customerDAO = new CustomerDAO();

        // Test customer authentication
        testCustomerAuthentication(customerDAO);

        // Test data persistence
        testDataPersistence(customerDAO);

        System.out.println("\n🎉 Authentication system test completed!");
    }

    private static void testCustomerAuthentication(CustomerDAO dao) {
        System.out.println("\n👤 Testing Customer Authentication:");

        // Test existing customers
        String[][] testCases = {
            {"C001", "password123", "Muhammad Ali"},
            {"C002", "TopGear2025!", "Jeremy Clarkson"},
            {"C003", "HammondRacing!", "Richard Hammond"},
            {"C004", "CaptainSlow!", "James May"},
            {"C005", "QueenOfNurburgring!", "Sabine Schmitz"},
            {"C006", "TopGearHost!", "Chris Evans"}
        };

        int passed = 0;
        for (String[] testCase : testCases) {
            String customerId = testCase[0];
            String password = testCase[1];
            String expectedName = testCase[2];

            Customer customer = dao.load(customerId);
            if (customer != null) {
                boolean authResult = customer.authenticate(password);
                if (authResult) {
                    System.out.println("✅ " + expectedName + " - Authentication successful");
                    passed++;
                } else {
                    System.out.println("❌ " + expectedName + " - Authentication failed");
                }
            } else {
                System.out.println("❌ " + expectedName + " - Customer not found");
            }
        }

        System.out.println("\n📊 Customer Authentication Results:");
        System.out.println("Passed: " + passed + "/" + testCases.length);
        System.out.println("Success Rate: " + (passed * 100 / testCases.length) + "%");
    }

    private static void testDataPersistence(CustomerDAO dao) {
        System.out.println("\n💾 Testing Data Persistence:");

        // Load and verify all customers
        String[] customerIds = {"C001", "C002", "C003", "C004", "C005", "C006"};
        int validCustomers = 0;

        for (String id : customerIds) {
            Customer customer = dao.load(id);
            if (customer != null) {
                boolean hasHash = customer.getHashedPassword() != null && !customer.getHashedPassword().isEmpty();
                boolean hasSalt = customer.getSalt() != null && !customer.getSalt().isEmpty();
                boolean noPlainPassword = customer.getPassword() == null;

                if (hasHash && hasSalt && noPlainPassword) {
                    System.out.println("✅ " + customer.getName() + " - Data integrity verified");
                    validCustomers++;
                } else {
                    System.out.println("❌ " + customer.getName() + " - Data integrity issue");
                    System.out.println("   Has Hash: " + hasHash + ", Has Salt: " + hasSalt + ", Plain Password Cleared: " + noPlainPassword);
                }
            } else {
                System.out.println("❌ Customer " + id + " - Not found");
            }
        }

        System.out.println("\n📊 Data Persistence Results:");
        System.out.println("Valid Customers: " + validCustomers + "/" + customerIds.length);
        System.out.println("Integrity Rate: " + (validCustomers * 100 / customerIds.length) + "%");
    }
}
