package test;

import hillclimmer.DatabaseModule.CustomerDAO;

public class TestCustomerIdGeneration {
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
        int currentCount = dao.getAll().size();
        String nextId = "C" + String.format("%03d", currentCount + 1);

        System.out.println("Current customer count: " + currentCount);
        System.out.println("Next customer ID should be: " + nextId);
    }
}
