package test;

import hillclimmer.DatabaseModule.CustomerDAO;
import java.io.File;

public class TestFilePath {
    public static void main(String[] args) {
        System.out.println("=== Testing File Path Resolution ===");

        // Check current working directory
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        // Check if data directory exists
        File dataDir = new File("data");
        System.out.println("Data directory exists: " + dataDir.exists());
        System.out.println("Data directory path: " + dataDir.getAbsolutePath());

        // Check if customers.csv exists
        File customersFile = new File("data/customers.csv");
        System.out.println("Customers.csv exists: " + customersFile.exists());
        System.out.println("Customers.csv path: " + customersFile.getAbsolutePath());

        // Test CustomerDAO
        CustomerDAO dao = new CustomerDAO();
        System.out.println("CustomerDAO created successfully");

        int count = dao.getAll().size();
        System.out.println("Customer count: " + count);

        // List all customers
        System.out.println("Customers:");
        dao.getAll().forEach(c -> System.out.println("  " + c.getCustomerID() + ": " + c.getName()));
    }
}
