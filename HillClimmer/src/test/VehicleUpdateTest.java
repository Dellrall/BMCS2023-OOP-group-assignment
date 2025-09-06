package test;

public class VehicleUpdateTest {
    public static void main(String[] args) {
        System.out.println("Testing Vehicle Update Functionality...");

        // Test condition validation
        testConditionInput("a", "Valid condition - Good");
        testConditionInput("b", "Valid condition - Excellent");
        testConditionInput("c", "Valid condition - New");
        testConditionInput("d", "Invalid condition - d");

        // Test price validation
        testPriceInput("100.50", "Valid price");
        testPriceInput("0", "Invalid price - zero");
        testPriceInput("-50", "Invalid price - negative");
        testPriceInput("abc", "Invalid price - non-numeric");

        // Test availability validation
        testAvailabilityInput("a", "Valid availability - Available");
        testAvailabilityInput("b", "Valid availability - Unavailable");
        testAvailabilityInput("c", "Invalid availability - c");

        System.out.println("\nVehicle update functionality tests completed!");
    }

    private static void testConditionInput(String input, String description) {
        System.out.println("\n--- Testing: " + description + " ---");
        System.out.println("Input: '" + input + "'");

        String conditionInput = input.trim().toLowerCase();
        String condition;
        switch (conditionInput) {
            case "a":
                condition = "Good";
                System.out.println("✅ Valid: " + condition);
                break;
            case "b":
                condition = "Excellent";
                System.out.println("✅ Valid: " + condition);
                break;
            case "c":
                condition = "New";
                System.out.println("✅ Valid: " + condition);
                break;
            default:
                System.out.println("❌ Invalid condition. Please select a, b, or c.");
        }
    }

    private static void testPriceInput(String input, String description) {
        System.out.println("\n--- Testing: " + description + " ---");
        System.out.println("Input: '" + input + "'");

        try {
            double newPrice = Double.parseDouble(input);
            if (newPrice <= 0) {
                System.out.println("❌ Price must be greater than 0.");
            } else {
                System.out.println("✅ Valid price: RM" + String.format("%.2f", newPrice));
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid price format.");
        }
    }

    private static void testAvailabilityInput(String input, String description) {
        System.out.println("\n--- Testing: " + description + " ---");
        System.out.println("Input: '" + input + "'");

        String availabilityInput = input.trim().toLowerCase();
        switch (availabilityInput) {
            case "a":
                System.out.println("✅ Valid: Available");
                break;
            case "b":
                System.out.println("✅ Valid: Unavailable");
                break;
            default:
                System.out.println("❌ Invalid option. Please select a or b.");
        }
    }
}
