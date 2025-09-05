import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.CustomerModule.Customer;

public class LoginTest {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();

        System.out.println("🧪 LOGIN FUNCTIONALITY TEST");
        System.out.println("===========================");

        // Test cases for customer login
        String[][] testCases = {
            {"C001", "muhammad@email.com", "Muhammad Ali"},
            {"C002", "jeremy@topgear.com", "Jeremy Clarkson"},
            {"C003", "richard@topgear.com", "Richard Hammond"}
        };

        int passedTests = 0;
        int totalTests = testCases.length * 2; // Test both ID and email for each customer

        for (String[] testCase : testCases) {
            String customerId = testCase[0];
            String email = testCase[1];
            String expectedName = testCase[2];

            // Test login by ID
            Customer customerById = customerDAO.load(customerId);
            if (customerById != null && customerById.getName().equals(expectedName)) {
                System.out.println("✅ Login by ID '" + customerId + "' successful: " + customerById.getName());
                passedTests++;
            } else {
                System.out.println("❌ Login by ID '" + customerId + "' failed");
            }

            // Test login by email
            Customer customerByEmail = customerDAO.findByEmail(email);
            if (customerByEmail != null && customerByEmail.getName().equals(expectedName)) {
                System.out.println("✅ Login by email '" + email + "' successful: " + customerByEmail.getName());
                passedTests++;
            } else {
                System.out.println("❌ Login by email '" + email + "' failed");
            }
        }

        System.out.println("\n===========================");
        System.out.println("📊 LOGIN TEST RESULTS");
        System.out.println("===========================");
        System.out.println("📈 Tests Passed: " + passedTests + "/" + totalTests);
        System.out.println("📊 Success Rate: " + (passedTests * 100.0 / totalTests) + "%");

        if (passedTests == totalTests) {
            System.out.println("🎉 ALL LOGIN TESTS PASSED!");
            System.out.println("✅ Customers can now login using either ID or email!");
        } else {
            System.out.println("❌ Some login tests failed. Please check the implementation.");
        }
    }
}
