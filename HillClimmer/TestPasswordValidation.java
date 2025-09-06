import hillclimmer.CustomerModule.Customer;
import java.time.LocalDate;

public class TestPasswordValidation {
    public static void main(String[] args) {
        try {
            Customer testCustomer = new Customer("TEST001", "Test", "900101-01-1234",
                "+60123456789", "test@test.com", "B", LocalDate.of(2030, 12, 31), 25, "ValidPass123!");

            System.out.println("Testing updatePassword with invalid password...");
            testCustomer.updatePassword("XYZGHI!");
            System.out.println("ERROR: Should have thrown exception!");
        } catch (IllegalArgumentException e) {
            System.out.println("SUCCESS: Exception thrown: " + e.getMessage());
        }
    }
}
