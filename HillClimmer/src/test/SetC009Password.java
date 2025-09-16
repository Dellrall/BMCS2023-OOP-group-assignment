package test;

import hillclimmer.CustomerModule.Customer;
import hillclimmer.DatabaseModule.CustomerDAO;

public class SetC009Password {
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();
        Customer customer = dao.load("C009");

        if (customer == null) {
            System.out.println("Customer C009 not found");
            return;
        }

        System.out.println("Current customer: " + customer.getName());
        customer.setPassword("test123!");
        dao.update(customer);
        System.out.println("Password set to 'test123' for C009");
    }
}