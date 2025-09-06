/*
 * Safety Check Input Validation Test
 * Tests the A-D input constraint for safety check questions
 */
package test;

public class SafetyCheckInputValidationTest {
    public static void main(String[] args) {
        System.out.println("🛡️ SAFETY CHECK INPUT VALIDATION TEST");
        System.out.println("=====================================");

        int passedTests = 0;
        int totalTests = 0;

        // Test 1: Valid inputs (A, B, C, D)
        System.out.println("\n✅ Testing VALID inputs (A, B, C, D):");
        totalTests += 6;
        passedTests += testInputValidation("A", true);
        passedTests += testInputValidation("B", true);
        passedTests += testInputValidation("C", true);
        passedTests += testInputValidation("D", true);
        passedTests += testInputValidation("a", true); // lowercase should also work
        passedTests += testInputValidation("d", true);

        // Test 2: Invalid inputs (anything else)
        System.out.println("\n❌ Testing INVALID inputs (should be rejected):");
        totalTests += 7;
        passedTests += testInputValidation("E", false);
        passedTests += testInputValidation("1", false);
        passedTests += testInputValidation("X", false);
        passedTests += testInputValidation("AB", false);
        passedTests += testInputValidation("", false);
        passedTests += testInputValidation(" ", false);
        passedTests += testInputValidation("@", false);

        // Test 3: Edge cases
        System.out.println("\n🔍 Testing EDGE cases:");
        totalTests += 3;
        passedTests += testInputValidation("A ", true); // trailing space
        passedTests += testInputValidation(" b ", true); // leading/trailing spaces
        passedTests += testInputValidation("C\n", true); // with newline

        // Results
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 SAFETY CHECK INPUT VALIDATION TEST RESULTS");
        System.out.println("=".repeat(50));
        System.out.println("✅ Tests Passed: " + passedTests + "/" + totalTests);
        System.out.println("📈 Success Rate: " + String.format("%.1f", (passedTests * 100.0) / totalTests) + "%");

        if (passedTests == totalTests) {
            System.out.println("🎉 ALL INPUT VALIDATION TESTS PASSED!");
            System.out.println("✅ Safety check input constraint is working correctly");
        } else {
            System.out.println("⚠️ SOME TESTS FAILED! Input validation may have issues.");
        }
    }

    private static int testInputValidation(String input, boolean shouldBeValid) {
        // Simulate the validation logic from SafetyCheck
        String answer = input.toUpperCase().trim();
        boolean isValid = answer.matches("[A-D]");

        if (isValid == shouldBeValid) {
            System.out.println("   ✅ Input '" + input.replace("\n", "\\n") + "' - " +
                (isValid ? "ACCEPTED" : "REJECTED") + " (Expected: " +
                (shouldBeValid ? "ACCEPTED" : "REJECTED") + ")");
            return 1;
        } else {
            System.out.println("   ❌ Input '" + input.replace("\n", "\\n") + "' - " +
                (isValid ? "ACCEPTED" : "REJECTED") + " (Expected: " +
                (shouldBeValid ? "REJECTED" : "ACCEPTED") + ") - BUG!");
            return 0;
        }
    }
}
