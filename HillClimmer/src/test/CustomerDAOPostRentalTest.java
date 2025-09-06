package test;

import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.DatabaseModule.RentalDAO;
import hillclimmer.RentalModule.Rental;
import java.time.LocalDate;

/**
 * Test to check if customer DAO works properly after rental operations
 */
public class CustomerDAOPostRentalTest {

    public static void main(String[] args) {
        System.out.println("🧪 Customer DAO Post-Rental Test");
        System.out.println("=================================");

        try {
            CustomerDAO customerDAO = new CustomerDAO();
            RentalDAO rentalDAO = new RentalDAO();

            // Check initial customer count
            int initialCustomerCount = customerDAO.getAll().size();
            System.out.println("✅ Initial customer count: " + initialCustomerCount);

            // Create a test rental
            Rental testRental = new Rental(999, 1, 1, LocalDate.now(), LocalDate.now().plusDays(1), 100.0);
            rentalDAO.save(testRental);
            System.out.println("✅ Test rental created");

            // Check customer count after rental
            int postRentalCustomerCount = customerDAO.getAll().size();
            System.out.println("✅ Customer count after rental: " + postRentalCustomerCount);

            if (initialCustomerCount == postRentalCustomerCount) {
                System.out.println("✅ Customer count remained consistent");
            } else {
                System.out.println("❌ Customer count changed unexpectedly!");
                System.out.println("   Initial: " + initialCustomerCount + ", After rental: " + postRentalCustomerCount);
            }

            // Clean up test rental
            rentalDAO.delete("999");
            System.out.println("✅ Test rental cleaned up");

            // Final check
            int finalCustomerCount = customerDAO.getAll().size();
            System.out.println("✅ Final customer count: " + finalCustomerCount);

            if (initialCustomerCount == finalCustomerCount) {
                System.out.println("\n=================================");
                System.out.println("📊 CUSTOMER DAO POST-RENTAL TEST RESULTS");
                System.out.println("=================================");
                System.out.println("📈 Test Passed: Customer DAO remains stable after rental operations");
                System.out.println("📊 Success Rate: 100.0%");
                System.out.println("🎉 Customer data integrity maintained!");
            } else {
                System.out.println("\n❌ Test FAILED: Customer data corrupted after rental");
            }

        } catch (Exception e) {
            System.out.println("❌ Test FAILED with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
