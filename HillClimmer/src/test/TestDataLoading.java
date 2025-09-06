package test;

import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.CustomerModule.Customer;

public class TestDataLoading {
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
        System.out.println("Loaded customers: " + dao.getAll().size());

        for (Customer c : dao.getAll()) {
            System.out.println(c.getCustomerID() + " - " + c.getName());
        }

        // Test loading a specific customer
        Customer c001 = dao.load("C001");
        if (c001 != null) {
            System.out.println("C001 loaded successfully: " + c001.getName());
        } else {
            System.out.println("C001 not found!");
        }
    }
}
