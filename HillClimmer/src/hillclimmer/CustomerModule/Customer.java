/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.CustomerModule;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Customer class for managing customer information in Malaysia Hill Climbing vehicle rental system.
 *
 * @author las
 */
public class Customer {
    private String customerID;
    private String name;
    private String icNumber; // Malaysian IC number (12 digits)
    private String phoneNo; // Malaysian phone number format
    private String email;
    private String licenseType; // Malaysian license types: B, B2, D, etc.
    private LocalDate licenseExpiryDate;
    private int age;
    private String registrationDate;
    private String password; // For authentication
    private boolean isActive;
    private double outstandingBalance;

    // Malaysian IC pattern: YYMMDD-PB-###G (12 digits + hyphen + 1 digit)
    private static final Pattern IC_PATTERN = Pattern.compile("\\d{6}-\\d{2}-\\d{4}");
    // Malaysian phone pattern: +60XXXXXXXXX or 0XXXXXXXXX
    private static final Pattern PHONE_PATTERN = Pattern.compile("(?:\\+60|0)[1-9]\\d{7,8}");

    public Customer(String customerID, String name, String icNumber, String phoneNo,
                   String email, String licenseType, LocalDate licenseExpiryDate,
                   int age, String password) {
        this.customerID = customerID;
        this.name = name;
        this.icNumber = icNumber;
        this.phoneNo = phoneNo;
        this.email = email;
        this.licenseType = licenseType;
        this.licenseExpiryDate = licenseExpiryDate;
        this.age = age;
        this.password = password;
        this.registrationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.isActive = true;
        this.outstandingBalance = 0.0;
    }

    // Validation methods for Malaysian context
    public static boolean isValidIC(String icNumber) {
        if (!IC_PATTERN.matcher(icNumber).matches()) {
            return false;
        }

        // Extract birth date from IC
        String datePart = icNumber.substring(0, 6);
        try {
            int year = Integer.parseInt(datePart.substring(0, 2));
            int month = Integer.parseInt(datePart.substring(2, 4));
            int day = Integer.parseInt(datePart.substring(4, 6));

            // Convert 2-digit year to 4-digit
            year = year >= 0 && year <= 25 ? 2000 + year : 1900 + year;

            LocalDate birthDate = LocalDate.of(year, month, day);
            LocalDate now = LocalDate.now();

            // Check if birth date is not in future and age is reasonable
            return !birthDate.isAfter(now) && birthDate.isAfter(now.minusYears(100));
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidMalaysianPhone(String phoneNo) {
        return PHONE_PATTERN.matcher(phoneNo).matches();
    }

    public static boolean isValidLicenseType(String licenseType) {
        String[] validTypes = {"B", "B2", "D", "DA", "E", "E1", "E2"};
        for (String type : validTypes) {
            if (type.equals(licenseType.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean isLicenseValid() {
        return licenseExpiryDate != null && licenseExpiryDate.isAfter(LocalDate.now());
    }

    public int getAgeFromIC() {
        if (!isValidIC(icNumber)) return 0;

        String datePart = icNumber.substring(0, 6);
        int year = Integer.parseInt(datePart.substring(0, 2));
        year = year >= 0 && year <= 25 ? 2000 + year : 1900 + year;

        LocalDate birthDate = LocalDate.of(year,
            Integer.parseInt(datePart.substring(2, 4)),
            Integer.parseInt(datePart.substring(4, 6)));

        return LocalDate.now().getYear() - birthDate.getYear();
    }

    // Authentication method
    public boolean authenticate(String inputPassword) {
        return password != null && password.equals(inputPassword) && isActive;
    }

    // Getters
    public String getCustomerID() { return customerID; }
    public String getName() { return name; }
    public String getIcNumber() { return icNumber; }
    public String getPhoneNo() { return phoneNo; }
    public String getEmail() { return email; }
    public String getLicenseType() { return licenseType; }
    public LocalDate getLicenseExpiryDate() { return licenseExpiryDate; }
    public int getAge() { return age; }
    public String getRegistrationDate() { return registrationDate; }
    public String getPassword() { return password; }
    public boolean isActive() { return isActive; }
    public double getOutstandingBalance() { return outstandingBalance; }

    // Setters with validation
    public void setPhoneNo(String phoneNo) {
        if (isValidMalaysianPhone(phoneNo)) {
            this.phoneNo = phoneNo;
        } else {
            throw new IllegalArgumentException("Invalid Malaysian phone number format");
        }
    }

    public void setEmail(String email) {
        if (email != null && email.contains("@") && email.contains(".")) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

    public void setLicenseExpiryDate(LocalDate licenseExpiryDate) {
        this.licenseExpiryDate = licenseExpiryDate;
    }

    public void setOutstandingBalance(double outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void updatePassword(String newPassword) {
        if (newPassword != null && newPassword.length() >= 6) {
            this.password = newPassword;
        } else {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
    }

    @Override
    public String toString() {
        return "=== Customer Profile ===\n" +
               "Customer ID: " + customerID + "\n" +
               "Name: " + name + "\n" +
               "IC Number: " + icNumber + "\n" +
               "Phone: " + phoneNo + "\n" +
               "Email: " + email + "\n" +
               "License Type: " + licenseType + "\n" +
               "License Expiry: " + (licenseExpiryDate != null ? licenseExpiryDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "N/A") + "\n" +
               "Age: " + age + "\n" +
               "Registration Date: " + registrationDate + "\n" +
               "Outstanding Balance: RM" + String.format("%.2f", outstandingBalance) + "\n" +
               "Status: " + (isActive ? "Active" : "Inactive") + "\n" +
               "License Valid: " + (isLicenseValid() ? "Yes" : "No");
    }
}
