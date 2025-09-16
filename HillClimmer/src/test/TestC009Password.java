package test;

import hillclimmer.CustomerModule.Customer;
import hillclimmer.DatabaseModule.CustomerDAO;

public class TestC009Password {
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
        Customer customer = dao.load("C009");

        if (customer == null) {
            System.out.println("Customer C009 not found");
            return;
        }

        System.out.println("Customer: " + customer.getName());
        System.out.println("Outstanding Balance: RM" + customer.getOutstandingBalance());

        System.out.println("Customer: " + customer.getName());
        System.out.println("Outstanding Balance: RM" + customer.getOutstandingBalance());
    }
}