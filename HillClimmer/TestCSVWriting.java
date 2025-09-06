import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.CustomerModule.Customer;
import java.time.LocalDate;

public class TestCSVWriting {
    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO();

        System.out.println("Testing CSV writing...");

        // Clear any existing data
        System.out.println("Creating customer C001...");
        Customer c1 = new Customer("C001", "Test User 1", "900101-01-1234", "+60123456789", "test1@test.com", "B", LocalDate.of(2030, 12, 31), 25, "TestPass123!");
        dao.save(c1);
        System.out.println("C001 saved");

        System.out.println("Creating customer C002...");
        Customer c2 = new Customer("C002", "Test User 2", "900202-02-2345", "+60123456780", "test2@test.com", "B", LocalDate.of(2030, 12, 31), 25, "TestPass456!");
        dao.save(c2);
        System.out.println("C002 saved");

        System.out.println("Creating customer C003...");
        Customer c3 = new Customer("C003", "Test User 3", "900303-03-3456", "+60123456781", "test3@test.com", "B", LocalDate.of(2030, 12, 31), 25, "TestPass789!");
        dao.save(c3);
        System.out.println("C003 saved");

        System.out.println("All customers saved. Loading all customers...");
        var customers = dao.loadAll();
        System.out.println("Loaded " + customers.size() + " customers:");
        for (var c : customers) {
            System.out.println("- " + c.getCustomerID() + ": " + c.getName());
        }
    }
}
