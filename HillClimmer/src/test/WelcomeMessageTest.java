/*
 * Welcome Message Test
 * Tests the personalized welcome messages for different user types
 */
package test;

import hillclimmer.DatabaseModule.ManagerDAO;
import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.DatabaseModule.Manager;
import hillclimmer.CustomerModule.Customer;

/**
 * Test for personalized welcome messages
 * @author las
 */
public class WelcomeMessageTest {
    public static void main(String[] args) {
        System.out.println("🎉 Welcome Message Enhancement Test");
        System.out.println("===================================");

        ManagerDAO managerDAO = new ManagerDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        System.out.println("\n📋 Testing Manager Welcome Messages:");
        testManagerWelcome(managerDAO, "VM002", "Manager123!", "Chin Wen Wei");
        testManagerWelcome(managerDAO, "VM003", "SecurePass456!", "Lye Wei Lun");
        testManagerWelcome(managerDAO, "VM006", "TehSecure789!", "Teh Guan Chen");

        System.out.println("\n📋 Testing Customer Welcome Messages:");
        testCustomerWelcome(customerDAO, "C001", "password123", "Muhammad Ali");
        testCustomerWelcome(customerDAO, "C002", "TopGear2025!", "Jeremy Clarkson");

        System.out.println("\n✅ Welcome Message Test Completed!");
        System.out.println("🎊 All personalized welcome messages are working!");
    }

    private static void testManagerWelcome(ManagerDAO dao, String managerId, String password, String expectedName) {
        System.out.println("\n👨‍💼 Manager: " + expectedName + " (" + managerId + ")");

        Manager manager = dao.authenticate(managerId, password);
        if (manager != null) {
            System.out.println("✅ Authentication successful!");
            System.out.println("👋 Welcome back, " + manager.getName() + "!");
            System.out.println("🔐 Authorization Level: " + manager.getAuthorizationLevel());
            System.out.println("📊 Access to both Vehicle and Rental Management");
            System.out.println("🏢 Ready to manage Hill Climber operations?");
        } else {
            System.out.println("❌ Authentication failed");
        }
    }

    private static void testCustomerWelcome(CustomerDAO dao, String customerId, String password, String expectedName) {
        System.out.println("\n👤 Customer: " + expectedName + " (" + customerId + ")");

        Customer customer = dao.load(customerId);
        if (customer != null && customer.authenticate(password)) {
            System.out.println("✅ Login successful!");
            System.out.println("🎉 Welcome back, " + customer.getName() + "!");
            System.out.println("🏔️ Ready to explore Malaysia's hill climbing adventures?");
            System.out.println("👋 Hello, " + customer.getName() + "! Welcome to your dashboard.");
        } else {
            System.out.println("❌ Authentication failed");
        }
    }
}
