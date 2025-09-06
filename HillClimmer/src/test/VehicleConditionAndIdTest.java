import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class VehicleConditionAndIdTest {
    public static void main(String[] args) {
        System.out.println("Testing Vehicle Condition Selection and Auto ID Generation...");

        // Test condition selection validation
        testConditionInput("a", "Valid condition - Good");
        testConditionInput("b", "Valid condition - Excellent");
        testConditionInput("c", "Valid condition - New");
        testConditionInput("A", "Valid condition - uppercase A");
        testConditionInput("B", "Valid condition - uppercase B");
        testConditionInput("C", "Valid condition - uppercase C");
        testConditionInput("d", "Invalid condition - d");
        testConditionInput("1", "Invalid condition - number");
        testConditionInput("", "Invalid condition - empty");

        // Test ID generation logic (simulated)
        testIdGeneration(1, "Mountain Bike - MB021");
        testIdGeneration(2, "Dirt Bike - DB021");
        testIdGeneration(3, "Buggy - BG016");
        testIdGeneration(4, "Crossover - CR016");

        System.out.println("\nVehicle condition and ID generation tests completed!");
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

    private static void testIdGeneration(int type, String description) {
        System.out.println("\n--- Testing: " + description + " ---");

        String prefix;
        switch (type) {
            case 1:
                prefix = "MB"; // Mountain Bike
                break;
            case 2:
                prefix = "DB"; // Dirt Bike
                break;
            case 3:
                prefix = "BG"; // Buggy
                break;
            case 4:
                prefix = "CR"; // Crossover
                break;
            default:
                prefix = "V"; // Fallback
        }

        // Simulate finding max ID (based on current data)
        int maxId;
        switch (type) {
            case 1: maxId = 20; break; // MB020 is the last one
            case 2: maxId = 20; break; // DB020 is the last one
            case 3: maxId = 15; break; // BG015 is the last one
            case 4: maxId = 15; break; // CR015 is the last one
            default: maxId = 0;
        }

        String generatedId = prefix + String.format("%03d", maxId + 1);
        System.out.println("Generated ID: " + generatedId);
    }
}
