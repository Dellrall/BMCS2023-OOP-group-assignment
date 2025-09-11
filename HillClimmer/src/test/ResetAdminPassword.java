package test;

import hillclimmer.DatabaseModule.ManagerDAO;
import hillclimmer.DatabaseModule.Manager;

public class ResetAdminPassword {
    public static void main(String[] args) {
        ManagerDAO managerDAO = new ManagerDAO();

        // Load admin manager
        Manager admin = managerDAO.load("M001");
        if (admin == null) {
            System.out.println("Admin M001 not found");
            return;
        }

        // Reset password to Manager123!
        admin.updatePassword("Manager123!");
        managerDAO.update(admin);

        System.out.println("✅ Admin password reset to: Manager123!");
        System.out.println("✅ Password hash updated in CSV");
    }
}
