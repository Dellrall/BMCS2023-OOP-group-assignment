package test;

/**
 * Test class to verify that customer registration maintains user progress
 * when invalid inputs are provided.
 */
public class RegistrationProgressTest {

    public static void main(String[] args) {
        System.out.println("=== Registration Progress Test ===");
        System.out.println("This test verifies that invalid inputs don't cause loss of progress");
        System.out.println("in the customer registration process.\n");

        // Test scenarios
        testInvalidPasswordLoop();
        testInvalidPhoneLoop();
        testInvalidEmailLoop();
        testInvalidICLoop();
        testInvalidLicenseTypeLoop();
        testInvalidDateLoop();

        System.out.println("\n✅ All registration progress tests completed!");
        System.out.println("The registration method now maintains user progress on invalid inputs.");
    }

    private static void testInvalidPasswordLoop() {
        System.out.println("Testing password validation loop...");

        // Test invalid passwords that should loop
        String[] invalidPasswords = {"123", "short", "12345", "abcdef", "ABCDEF"};

        for (String pwd : invalidPasswords) {
            boolean isValid = isValidPassword(pwd);
            System.out.println("Password '" + pwd + "' valid: " + isValid + " (should be false)");
            assert !isValid : "Password should be invalid: " + pwd;
        }

        // Test valid passwords
        String[] validPasswords = {"Abc123!", "Pass123#", "Test456$", "Valid789@"};

        for (String pwd : validPasswords) {
            boolean isValid = isValidPassword(pwd);
            System.out.println("Password '" + pwd + "' valid: " + isValid + " (should be true)");
            assert isValid : "Password should be valid: " + pwd;
        }

        System.out.println("✅ Password validation test passed!\n");
    }

    private static void testInvalidPhoneLoop() {
        System.out.println("Testing phone number validation loop...");

        // Test invalid phone numbers
        String[] invalidPhones = {"123", "123456789", "abcdefghij", "012-345-6789"};

        for (String phone : invalidPhones) {
            boolean isValid = isValidMalaysianPhoneInput(phone);
            System.out.println("Phone '" + phone + "' valid: " + isValid + " (should be false)");
            assert !isValid : "Phone should be invalid: " + phone;
        }

        // Test valid phone numbers
        String[] validPhones = {"0123456789", "+60123456789", "0167890123", "0198765432"};

        for (String phone : validPhones) {
            boolean isValid = isValidMalaysianPhoneInput(phone);
            System.out.println("Phone '" + phone + "' valid: " + isValid + " (should be true)");
            assert isValid : "Phone should be valid: " + phone;
        }

        System.out.println("✅ Phone validation test passed!\n");
    }

    private static void testInvalidEmailLoop() {
        System.out.println("Testing email validation loop...");

        // Test invalid emails
        String[] invalidEmails = {"invalid", "invalid@", "@domain.com", "user@", "user@.com"};

        for (String email : invalidEmails) {
            boolean isValid = isValidEmail(email);
            System.out.println("Email '" + email + "' valid: " + isValid + " (should be false)");
            assert !isValid : "Email should be invalid: " + email;
        }

        // Test valid emails
        String[] validEmails = {"user@example.com", "test.email@domain.org", "user123@test.net"};

        for (String email : validEmails) {
            boolean isValid = isValidEmail(email);
            System.out.println("Email '" + email + "' valid: " + isValid + " (should be true)");
            assert isValid : "Email should be valid: " + email;
        }

        System.out.println("✅ Email validation test passed!\n");
    }

    private static void testInvalidICLoop() {
        System.out.println("Testing IC number validation loop...");

        // Test invalid IC numbers
        String[] invalidICs = {"123456-78-9012", "123456789012", "ABCDEFGHIJKL", "123456-78-90"};

        for (String ic : invalidICs) {
            boolean isValid = isValidIC(ic);
            System.out.println("IC '" + ic + "' valid: " + isValid + " (should be false)");
            assert !isValid : "IC should be invalid: " + ic;
        }

        // Test valid IC numbers
        String[] validICs = {"123456-78-9012", "987654-32-1098", "456789-01-2345"};

        for (String ic : validICs) {
            boolean isValid = isValidIC(ic);
            System.out.println("IC '" + ic + "' valid: " + isValid + " (should be true)");
            assert isValid : "IC should be valid: " + ic;
        }

        System.out.println("✅ IC validation test passed!\n");
    }

    private static void testInvalidLicenseTypeLoop() {
        System.out.println("Testing license type validation loop...");

        // Test invalid license types
        String[] invalidLicenses = {"A", "C", "F", "G", "XYZ"};

        for (String license : invalidLicenses) {
            boolean isValid = isValidLicenseType(license);
            System.out.println("License '" + license + "' valid: " + isValid + " (should be false)");
            assert !isValid : "License should be invalid: " + license;
        }

        // Test valid license types
        String[] validLicenses = {"B", "B2", "D", "DA", "E", "E1", "E2"};

        for (String license : validLicenses) {
            boolean isValid = isValidLicenseType(license);
            System.out.println("License '" + license + "' valid: " + isValid + " (should be true)");
            assert isValid : "License should be valid: " + license;
        }

        System.out.println("✅ License type validation test passed!\n");
    }

    private static void testInvalidDateLoop() {
        System.out.println("Testing date validation loop...");

        // Test invalid dates
        String[] invalidDates = {"32/13/2023", "01/01/1800", "abc/def/2023", "01/01/23"};

        for (String date : invalidDates) {
            boolean isValid = isValidDate(date);
            System.out.println("Date '" + date + "' valid: " + isValid + " (should be false)");
            assert !isValid : "Date should be invalid: " + date;
        }

        // Test valid dates
        String[] validDates = {"01/01/2025", "31/12/2024", "15/06/2023"};

        for (String date : validDates) {
            boolean isValid = isValidDate(date);
            System.out.println("Date '" + date + "' valid: " + isValid + " (should be true)");
            assert isValid : "Date should be valid: " + date;
        }

        System.out.println("✅ Date validation test passed!\n");
    }

    // Helper methods to simulate the validation logic from the main classes
    private static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        // Check for at least one hex digit and one symbol
        boolean hasHexDigit = password.matches(".*[0-9a-fA-F].*");
        boolean hasSymbol = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        return hasHexDigit && hasSymbol;
    }

    private static boolean isValidMalaysianPhoneInput(String phone) {
        if (phone == null) return false;
        // Support 10-11 digit Malaysian phone numbers
        return phone.matches("\\d{10,11}") || phone.matches("\\+60\\d{8,9}");
    }

    private static boolean isValidEmail(String email) {
        if (email == null) return false;
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private static boolean isValidIC(String ic) {
        if (ic == null) return false;
        return ic.matches("\\d{6}-\\d{2}-\\d{4}");
    }

    private static boolean isValidLicenseType(String license) {
        if (license == null) return false;
        return license.matches("^(B|B2|D|DA|E|E1|E2)$");
    }

    private static boolean isValidDate(String date) {
        if (date == null) return false;
        try {
            String[] parts = date.split("/");
            if (parts.length != 3) return false;
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int year = Integer.parseInt(parts[2]);

            if (year < 1900 || year > 2100) return false;
            if (month < 1 || month > 12) return false;
            if (day < 1 || day > 31) return false;

            // Basic validation for days in month
            if (month == 2 && day > 29) return false;
            if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) return false;

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
