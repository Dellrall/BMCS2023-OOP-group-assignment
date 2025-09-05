/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.CustomerModule;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import java.util.Date;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

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
    private String password; // For authentication (deprecated - use hashedPassword)
    private String hashedPassword; // Hashed password with salt
    private String salt; // Salt used for password hashing
    private boolean isActive;
    private double outstandingBalance;
    private boolean safetyCheckPassed; // Safety check completion status
    private String safetyCheckID; // ID of the safety check assessment
    private Date safetyCheckDate; // Date when safety check was completed

    // Malaysian IC pattern: YYMMDD-PB-###G (12 digits + hyphen + 1 digit)
    private static final Pattern IC_PATTERN = Pattern.compile("\\d{6}-\\d{2}-\\d{4}");
    // Malaysian phone pattern: +60XXXXXXXXX or 0XXXXXXXXX
    private static final Pattern PHONE_PATTERN = Pattern.compile("(?:\\+60|0)[1-9]\\d{7,8}");
    // Malaysian phone input pattern: allows various formats for input
    private static final Pattern PHONE_INPUT_PATTERN = Pattern.compile("(?:\\+60|0)[1-9]\\d{7,8}|0\\d{2}-\\d{3}-\\d{4}|0\\d{2} \\d{3} \\d{4}");

    /**
     * Generates a random salt for password hashing
     * @return Base64 encoded salt string
     */
    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 128-bit salt
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hashes a password with the provided salt using SHA-256
     * @param password Plain text password
     * @param salt Salt to use for hashing
     * @return Base64 encoded hashed password
     */
    private static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Sets and hashes a new password with a new salt
     * @param plainPassword Plain text password
     */
    public void setPassword(String plainPassword) {
        this.salt = generateSalt();
        this.hashedPassword = hashPassword(plainPassword, this.salt);
        this.password = null; // Clear plain text password for security
    }

    /**
     * Authenticates a password against the stored hash
     * @param inputPassword Password to verify
     * @return true if password matches, false otherwise
     */
    public boolean authenticatePassword(String inputPassword) {
        if (this.hashedPassword == null || this.salt == null) {
            // Fallback to plain text password for backward compatibility
            return this.password != null && this.password.equals(inputPassword);
        }

        String inputHash = hashPassword(inputPassword, this.salt);
        return this.hashedPassword.equals(inputHash);
    }

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
        setPassword(password); // Hash and salt the password
        this.registrationDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.isActive = true;
        this.outstandingBalance = 0.0;
        this.safetyCheckPassed = false; // New customers haven't passed safety check yet
    }

    // Validation methods for Malaysian context
    public static boolean isValidIC(String icNumber) {
        if (!IC_PATTERN.matcher(icNumber).matches()) {
            return false;
        }

        // Extract birth date from IC (YYMMDD format)
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

    /**
     * Validates Malaysian phone number input formats
     * @param phoneInput Phone number in input format
     * @return true if valid input format
     */
    public static boolean isValidMalaysianPhoneInput(String phoneInput) {
        return PHONE_INPUT_PATTERN.matcher(phoneInput).matches();
    }

    /**
     * Normalizes phone number to +60XXXXXXXXX format
     * @param phoneInput Phone number in any valid input format
     * @return Normalized phone number
     */
    public static String normalizeMalaysianPhone(String phoneInput) {
        if (phoneInput.startsWith("+60")) {
            return phoneInput;
        }
        // Remove hyphens and spaces
        String cleaned = phoneInput.replaceAll("[-\\s]", "");
        if (cleaned.startsWith("0")) {
            return "+60" + cleaned.substring(1);
        }
        return cleaned; // Should not happen if validated
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
        return authenticatePassword(inputPassword) && isActive;
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
    public boolean isSafetyCheckPassed() { return safetyCheckPassed; }
    public String getSafetyCheckID() { return safetyCheckID; }
    public Date getSafetyCheckDate() { return safetyCheckDate; }
    public String getHashedPassword() { return hashedPassword; }
    public String getSalt() { return salt; }

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

    public void setSafetyCheckPassed(boolean safetyCheckPassed) {
        this.safetyCheckPassed = safetyCheckPassed;
    }

    public void setSafetyCheckID(String safetyCheckID) {
        this.safetyCheckID = safetyCheckID;
    }

    public void setSafetyCheckDate(Date safetyCheckDate) {
        this.safetyCheckDate = safetyCheckDate;
    }

    // Setters for password hashing (used by DAO)
    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    // Special method for DAO to clear plain text password
    public void clearPlainPassword() {
        this.password = null;
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
               "License Valid: " + (isLicenseValid() ? "Yes" : "No") + "\n" +
               "Safety Check Passed: " + (safetyCheckPassed ? "Yes" : "No");
    }
}
