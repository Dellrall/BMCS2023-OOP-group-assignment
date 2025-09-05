/*
 * Manager Authentication Test
 * Tests the new manager authentication system with managers.csv
 */
package test;

import hillclimmer.DatabaseModule.ManagerDAO;
import hillclimmer.DatabaseModule.Manager;

/**
 * Test for the new manager authentication system
 * @author las
 */
public class ManagerAuthenticationTest {
    public static void main(String[] args) {
        System.out.println("🔐 Manager Authentication System Test");
        System.out.println("====================================");

        ManagerDAO managerDAO = new ManagerDAO();

        // Test authentication with different manager levels
        testManagerAuthentication(managerDAO, "VM002", "Manager123!", "Chin Wen Wei", 1);
        testManagerAuthentication(managerDAO, "VM003", "SecurePass456!", "Lye Wei Lun", 2);
        testManagerAuthentication(managerDAO, "VM004", "Nurburg2025!", "Neeshwran A/L Veera Chelvan", 3);
        testManagerAuthentication(managerDAO, "VM005", "OscarRacing!", "Oscar Lim Zheng You", 4);
        testManagerAuthentication(managerDAO, "VM006", "TehSecure789!", "Teh Guan Chen", 5);

        // Test invalid credentials
        testInvalidAuthentication(managerDAO, "VM002", "wrongpassword");
        testInvalidAuthentication(managerDAO, "INVALID", "password");

        System.out.println("\n✅ Manager Authentication Test Completed!");
    }

    private static void testManagerAuthentication(ManagerDAO dao, String managerId, String password,
                                                String expectedName, int expectedLevel) {
        System.out.println("\n👤 Testing Manager: " + expectedName + " (ID: " + managerId + ")");

        Manager authenticated = dao.authenticate(managerId, password);

        if (authenticated != null) {
            System.out.println("✅ Authentication successful");
            System.out.println("   Name: " + authenticated.getName());
            System.out.println("   Level: " + authenticated.getAuthorizationLevel());
            System.out.println("   Hashed Password: " + authenticated.getHashedPassword().substring(0, 20) + "...");
            System.out.println("   Salt: " + authenticated.getSalt().substring(0, 20) + "...");

            // Test permissions
            testPermissions(authenticated);
        } else {
            System.out.println("❌ Authentication failed");
        }
    }

    private static void testInvalidAuthentication(ManagerDAO dao, String managerId, String password) {
        System.out.println("\n❌ Testing Invalid Credentials: " + managerId);

        Manager authenticated = dao.authenticate(managerId, password);

        if (authenticated == null) {
            System.out.println("✅ Correctly rejected invalid credentials");
        } else {
            System.out.println("❌ Incorrectly accepted invalid credentials");
        }
    }

    private static void testPermissions(Manager manager) {
        System.out.println("   Permissions:");
        System.out.println("   - View: " + (manager.hasPermission("view") ? "✅" : "❌"));
        System.out.println("   - Add: " + (manager.hasPermission("add") ? "✅" : "❌"));
        System.out.println("   - Update: " + (manager.hasPermission("update") ? "✅" : "❌"));
        System.out.println("   - Remove: " + (manager.hasPermission("remove") ? "✅" : "❌"));
        System.out.println("   - Admin: " + (manager.hasPermission("admin") ? "✅" : "❌"));
    }
}
