package test;

import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.CustomerModule.Customer;

/**
 * Update customer safety check status
 */
public class UpdateSafetyCheck {
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
        Customer c = dao.load("C001");

        if (c != null) {
            c.setSafetyCheckPassed(true);
            dao.update(c);
            System.out.println("✅ Safety check updated for C001");
        } else {
            System.out.println("❌ Customer C001 not found");
        }
    }
}
