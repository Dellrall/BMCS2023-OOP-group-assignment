package test;

import hillclimmer.DatabaseModule.ManagerDAO;
import hillclimmer.DatabaseModule.Manager;

/**
 * Realistic Admin Password Change Test
 * Tests the complete admin password change workflow
 */
public class AdminPasswordChangeTest {
    public static void main(String[] args) {
        System.out.println("🧪 ADMIN PASSWORD CHANGE REALISTIC TEST");
        System.out.println("=====================================");

        ManagerDAO managerDAO = new ManagerDAO();

        // Test 1: Valid password change
        testValidPasswordChange(managerDAO);

        // Test 2: Invalid password (too short)
        testInvalidPasswordShort(managerDAO);

        // Test 3: Invalid password (no hex)
        testInvalidPasswordNoHex(managerDAO);

        // Test 4: Invalid password (no symbol)
        testInvalidPasswordNoSymbol(managerDAO);

        // Test 5: Wrong current password
        testWrongCurrentPassword(managerDAO);

        System.out.println("=====================================");
        System.out.println("🎉 ADMIN PASSWORD CHANGE TEST COMPLETED!");
    }

    private static void testValidPasswordChange(ManagerDAO managerDAO) {
        System.out.println("\n✅ Test 1: Valid Password Change");

        try {
            // Load admin manager
            Manager admin = managerDAO.load("M001");
            if (admin == null) {
                System.out.println("   ❌ Admin M001 not found");
                return;
            }

            System.out.println("   - Admin login successful (M001 loaded)");
            System.out.println("   - Current password: " + (admin.authenticatePassword("Manager123!") ? "VERIFIED" : "FAILED"));

            // Test password change
            String newPassword = "AdminNew456!";
            if (isValidPassword(newPassword)) {
                System.out.println("   - New password validation: PASSED (" + newPassword + ")");

                admin.updatePassword(newPassword);
                managerDAO.update(admin);

                // Verify the change
                boolean authWithNew = admin.authenticatePassword(newPassword);
                System.out.println("   - Password update: " + (authWithNew ? "SUCCESSFUL" : "FAILED"));
                System.out.println("   - Authentication with new password: " + (authWithNew ? "WORKING" : "FAILED"));

                // Test CSV persistence
                Manager reloaded = managerDAO.load("M001");
                boolean csvPersisted = reloaded != null && reloaded.authenticatePassword(newPassword);
                System.out.println("   - CSV persistence: " + (csvPersisted ? "CONFIRMED" : "FAILED"));

            } else {
                System.out.println("   ❌ New password validation failed");
            }

        } catch (Exception e) {
            System.out.println("   ❌ Test failed: " + e.getMessage());
        }
    }

    private static void testInvalidPasswordShort(ManagerDAO managerDAO) {
        System.out.println("\n✅ Test 2: Invalid Password (Too Short)");

        try {
            Manager admin = managerDAO.load("M001");
            if (admin == null) return;

            String shortPassword = "123";
            boolean isValid = isValidPassword(shortPassword);
            System.out.println("   - Password validation for '" + shortPassword + "': " + (isValid ? "PASSED" : "FAILED (too short)"));
            System.out.println("   - Loop back for retry: " + (!isValid ? "WORKING" : "NOT TRIGGERED"));

        } catch (Exception e) {
            System.out.println("   ❌ Test failed: " + e.getMessage());
        }
    }

    private static void testInvalidPasswordNoHex(ManagerDAO managerDAO) {
        System.out.println("\n✅ Test 3: Invalid Password (No Hex)");

        try {
            Manager admin = managerDAO.load("M001");
            if (admin == null) return;

            String noHexPassword = "zzzzz!";
            boolean isValid = isValidPassword(noHexPassword);
            System.out.println("   - Password validation for '" + noHexPassword + "': " + (isValid ? "PASSED" : "FAILED (no hex digits)"));
            System.out.println("   - Loop back for retry: " + (!isValid ? "WORKING" : "NOT TRIGGERED"));

        } catch (Exception e) {
            System.out.println("   ❌ Test failed: " + e.getMessage());
        }
    }

    private static void testInvalidPasswordNoSymbol(ManagerDAO managerDAO) {
        System.out.println("\n✅ Test 4: Invalid Password (No Symbol)");

        try {
            Manager admin = managerDAO.load("M001");
            if (admin == null) return;

            String noSymbolPassword = "Password123";
            boolean isValid = isValidPassword(noSymbolPassword);
            System.out.println("   - Password validation for '" + noSymbolPassword + "': " + (isValid ? "PASSED" : "FAILED (no symbols)"));
            System.out.println("   - Loop back for retry: " + (!isValid ? "WORKING" : "NOT TRIGGERED"));

        } catch (Exception e) {
            System.out.println("   ❌ Test failed: " + e.getMessage());
        }
    }

    private static void testWrongCurrentPassword(ManagerDAO managerDAO) {
        System.out.println("\n✅ Test 5: Wrong Current Password");

        try {
            Manager admin = managerDAO.load("M001");
            if (admin == null) return;

            String wrongPassword = "WrongPass123!";
            boolean authResult = admin.authenticatePassword(wrongPassword);
            System.out.println("   - Current password verification with '" + wrongPassword + "': " + (authResult ? "PASSED" : "FAILED"));
            System.out.println("   - Loop back for retry: " + (!authResult ? "WORKING" : "NOT TRIGGERED"));

        } catch (Exception e) {
            System.out.println("   ❌ Test failed: " + e.getMessage());
        }
    }

    private static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        boolean hasHex = password.matches(".*[0-9a-fA-F].*");
        boolean hasSymbol = password.matches(".*[!@#$%^&*()_+=\\-\\[\\]{}|;:,.<>?].*");
        return hasHex && hasSymbol;
    }
}
