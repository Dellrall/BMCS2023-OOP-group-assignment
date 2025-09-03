/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.DatabaseModule;

import hillclimmer.CustomerModule.Customer;
import java.time.LocalDate;
import java.util.List;

/**
 * CustomerDAO class extending DataAccessObject for Customer data management.
 *
 * @author las
 */
public class CustomerDAO extends DataAccessObject<Customer> {

    public CustomerDAO() {
        super("customers.csv"); // File for storing customer data
    }

    @Override
    protected String objectToCSV(Customer customer) {
        return customer.getCustomerID() + "," +
               customer.getName() + "," +
               customer.getIcNumber() + "," +
               customer.getPhoneNo() + "," +
               customer.getEmail() + "," +
               customer.getLicenseType() + "," +
               (customer.getLicenseExpiryDate() != null ? customer.getLicenseExpiryDate() : "") + "," +
               customer.getAddress() + "," +
               customer.getAge() + "," +
               customer.getRegistrationDate() + "," +
               customer.getOutstandingBalance() + "," +
               customer.isActive();
    }

    @Override
    protected Customer csvToObject(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 12) {
            String customerID = parts[0];
            String name = parts[1];
            String icNumber = parts[2];
            String phoneNo = parts[3];
            String email = parts[4];
            String licenseType = parts[5];
            LocalDate licenseExpiryDate = parts[6].isEmpty() ? null : LocalDate.parse(parts[6]);
            String address = parts[7];
            int age = Integer.parseInt(parts[8]);
            // registrationDate is set automatically in Customer constructor
            double outstandingBalance = Double.parseDouble(parts[10]);
            boolean isActive = Boolean.parseBoolean(parts[11]);

            Customer customer = new Customer(customerID, name, icNumber, phoneNo, email,
                licenseType, licenseExpiryDate, address, age, "password123"); // Default password for loaded customers
            customer.setOutstandingBalance(outstandingBalance);
            customer.setActive(isActive);
            return customer;
        }
        return null;
    }

    @Override
    protected String getId(Customer customer) {
        return customer.getCustomerID();
    }

    // Additional methods specific to CustomerDAO
    public List<Customer> getAll() {
        return loadAll();
    }
    public Customer findByEmail(String email) {
        for (Customer c : loadAll()) {
            if (c.getEmail().equals(email)) {
                return c;
            }
        }
        return null;
    }

    public void updateContactInfo(String customerID, String phoneNo, String email) {
        Customer c = load(customerID);
        if (c != null) {
            c.setPhoneNo(phoneNo);
            c.setEmail(email);
            update(c);
        }
    }
}
