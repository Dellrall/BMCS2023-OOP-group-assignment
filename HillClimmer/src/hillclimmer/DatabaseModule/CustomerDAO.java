/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.DatabaseModule;

import hillclimmer.CustomerModule.Customer;
import java.time.LocalDate;
import java.util.List;
import java.util.Date;

/**
 * CustomerDAO class extending DataAccessObject for Customer data management.
 *
 * @author las
 */
public class CustomerDAO extends DataAccessObject<Customer> {

    public CustomerDAO() {
        super("data/customers.csv"); // File for storing customer data
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
               customer.getAge() + "," +
               customer.getRegistrationDate() + "," +
               customer.getOutstandingBalance() + "," +
               customer.isActive() + "," +
               "\"" + (customer.getHashedPassword() != null ? customer.getHashedPassword() : "") + "\"" + "," +
               "\"" + (customer.getSalt() != null ? customer.getSalt() : "") + "\"" + "," +
               customer.isSafetyCheckPassed() + "," +
               (customer.getSafetyCheckID() != null ? customer.getSafetyCheckID() : "") + "," +
               (customer.getSafetyCheckDate() != null ? customer.getSafetyCheckDate().getTime() : "");
    }

    @Override
    protected Customer csvToObject(String csvLine) {
        try {
            // Handle quoted fields that may contain commas (like Base64 strings)
            String[] parts = parseCSVLine(csvLine);
            if (parts.length >= 12) {  // Support both old (12 fields) and new (16 fields) formats
                String customerID = parts[0];
                String name = parts[1];
                String icNumber = parts[2];
                String phoneNo = parts[3];
                String email = parts[4];
                String licenseType = parts[5];
                LocalDate licenseExpiryDate = parts[6].isEmpty() ? null : LocalDate.parse(parts[6]);
                int age = Integer.parseInt(parts[7].trim());
                String registrationDate = parts[8];
                double outstandingBalance = Double.parseDouble(parts[9].trim());
                boolean isActive = Boolean.parseBoolean(parts[10].trim());
                String password = parts[11];
                boolean safetyCheckPassed = parts.length > 12 ? Boolean.parseBoolean(parts[12].trim()) : false;
                String safetyCheckID = parts.length > 13 && !parts[13].trim().isEmpty() ? parts[13].trim() : null;
                Date safetyCheckDate = parts.length > 14 && !parts[14].trim().isEmpty() ?
                    new Date(Long.parseLong(parts[14].trim())) : null;

                // Handle both old and new formats
                String hashedPassword = null;
                String salt = null;

                if (parts.length >= 16) {
                    // New format with hashed password and salt
                    hashedPassword = parts[11];  // Fixed: hashed password is at index 11
                    salt = parts[12];            // Fixed: salt is at index 12
                    safetyCheckPassed = parts.length > 13 ? Boolean.parseBoolean(parts[13].trim()) : false;
                    safetyCheckID = parts.length > 14 && !parts[14].trim().isEmpty() ? parts[14].trim() : null;
                    safetyCheckDate = parts.length > 15 && !parts[15].trim().isEmpty() ?
                        new Date(Long.parseLong(parts[15].trim())) : null;
                }

                // Create customer with a temporary password (will be replaced)
                Customer customer = new Customer(customerID, name, icNumber, phoneNo, email,
                    licenseType, licenseExpiryDate, age, "temp");
                customer.setOutstandingBalance(outstandingBalance);
                customer.setActive(isActive);
                customer.setRegistrationDate(registrationDate);
                customer.setSafetyCheckPassed(safetyCheckPassed);
                customer.setSafetyCheckID(safetyCheckID);
                customer.setSafetyCheckDate(safetyCheckDate);

                // Set password information based on format
                if (hashedPassword != null && salt != null && !hashedPassword.isEmpty() && !salt.isEmpty()) {
                    // New format: use hashed password
                    customer.setHashedPassword(hashedPassword);
                    customer.setSalt(salt);
                    customer.clearPlainPassword();
                } else {
                    // Old format: re-hash the plain text password
                    customer.setPassword(password);
                }

                return customer;
            }
        } catch (Exception e) {
            System.err.println("Error parsing customer CSV line: " + e.getMessage());
            System.err.println("CSV line: " + csvLine);
        }
        return null;
    }

    /**
     * Parse CSV line handling quoted fields that may contain commas
     */
    private String[] parseCSVLine(String line) {
        java.util.List<String> parts = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                parts.add(current.toString().trim());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }

        // Add the last part
        parts.add(current.toString().trim());

        return parts.toArray(new String[0]);
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
