import hillclimmer.CustomerModule.Customer;

public class PhoneTest {
    public static void main(String[] args) {
        // Test input validation
        String[] testInputs = {
            "+60123456789",    // existing format
            "0123456789",      // existing format
            "012-345-6789",    // new format
            "012 345 6789"     // new format
        };

        for (String input : testInputs) {
            boolean valid = Customer.isValidMalaysianPhoneInput(input);
            String normalized = valid ? Customer.normalizeMalaysianPhone(input) : "INVALID";
            System.out.println("Input: " + input + " -> Valid: " + valid + " -> Normalized: " + normalized);
        }
    }
}
