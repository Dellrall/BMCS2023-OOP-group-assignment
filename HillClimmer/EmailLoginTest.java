import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.CustomerModule.Customer;

public class EmailLoginTest {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();

        // Test login by ID
        System.out.println("Testing login by ID:");
        Customer customerById = customerDAO.load("C001");
        if (customerById != null) {
            System.out.println("✅ Found customer by ID: " + customerById.getName());
            System.out.println("   Email: " + customerById.getEmail());
        } else {
            System.out.println("❌ Customer not found by ID");
        }

        // Test login by email
        System.out.println("\nTesting login by email:");
        Customer customerByEmail = customerDAO.findByEmail("muhammad@email.com");
        if (customerByEmail != null) {
            System.out.println("✅ Found customer by email: " + customerByEmail.getName());
            System.out.println("   ID: " + customerByEmail.getCustomerID());
        } else {
            System.out.println("❌ Customer not found by email");
        }

        // Test authentication
        System.out.println("\nTesting authentication:");
        if (customerByEmail != null && customerByEmail.authenticate("password123")) {
            System.out.println("✅ Authentication successful");
        } else {
            System.out.println("❌ Authentication failed");
        }
    }
}
