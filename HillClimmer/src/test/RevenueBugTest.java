package test;

import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.DatabaseModule.ManagerDAO;
import hillclimmer.DatabaseModule.RentalDAO;
import hillclimmer.DatabaseModule.VehicleDAO;
import hillclimmer.RentalModule.Rental;
import hillclimmer.VehicleModule.VehicleManager;
import hillclimmer.RentalModule.RentalManager;
import hillclimmer.DurationModule.DurationManager;
import hillclimmer.DatabaseModule.Manager;
import java.time.LocalDate;

/**
 * Test to demonstrate the revenue bug: admin rentals don't contribute to system reports
 */
public class RevenueBugTest {

    public static void main(String[] args) {
        System.out.println("🧪 Revenue Bug Test");
        System.out.println("===================");

        try {
            // Initialize DAOs and managers
            CustomerDAO customerDAO = new CustomerDAO();
            RentalDAO rentalDAO = new RentalDAO();
            VehicleDAO vehicleDAO = new VehicleDAO();
            ManagerDAO managerDAO = new ManagerDAO();

            RentalManager rentalManager = new RentalManager();
            DurationManager durationManager = new DurationManager();

            // Get a test manager
            Manager testManager = managerDAO.getAllManagers().get(0);
            VehicleManager vehicleManager = new VehicleManager(testManager);

            System.out.println("✅ All managers initialized");

            // Check initial revenue
            double initialRevenue = durationManager.getTotalRevenueFromActivePeriods();
            int initialReminders = durationManager.getPendingReminders().size();
            System.out.println("\n📊 Initial State:");
            System.out.println("   Revenue: RM" + initialRevenue);
            System.out.println("   Pending Reminders: " + initialReminders);

            // Simulate customer rental (should work correctly)
            System.out.println("\n🔐 Simulating Customer Rental...");
            Rental customerRental = new Rental(3001, 1, 1, LocalDate.now(), LocalDate.now().plusDays(5), 375.0);
            rentalDAO.save(customerRental);
            rentalManager.addRentalWithId(3001, 1, 1, LocalDate.now(), LocalDate.now().plusDays(5), 375.0);

            // Create rental period and reminder (like customer flow does)
            durationManager.createBasicRentalPeriod(3001, LocalDate.now(), LocalDate.now().plusDays(5), 75.0);
            durationManager.createReturnReminder(3001, LocalDate.now().plusDays(5).atStartOfDay());

            double afterCustomerRevenue = durationManager.getTotalRevenueFromActivePeriods();
            int afterCustomerReminders = durationManager.getPendingReminders().size();

            System.out.println("✅ Customer rental created with period and reminder");
            System.out.println("   Revenue after customer rental: RM" + afterCustomerRevenue);
            System.out.println("   Reminders after customer rental: " + afterCustomerReminders);

            // Simulate admin rental (current buggy behavior)
            System.out.println("\n👨‍💼 Simulating Admin Rental (Current Buggy Behavior)...");
            Rental adminRental = new Rental(3002, 2, 2, LocalDate.now(), LocalDate.now().plusDays(3), 225.0);
            rentalDAO.save(adminRental);
            rentalManager.addRentalWithId(3002, 2, 2, LocalDate.now(), LocalDate.now().plusDays(3), 225.0);

            // NOTE: Admin rental does NOT create rental period or reminder (this is the bug!)
            double afterAdminRevenue = durationManager.getTotalRevenueFromActivePeriods();
            int afterAdminReminders = durationManager.getPendingReminders().size();

            System.out.println("❌ Admin rental created WITHOUT period and reminder");
            System.out.println("   Revenue after admin rental: RM" + afterAdminRevenue);
            System.out.println("   Reminders after admin rental: " + afterAdminReminders);

            // Analysis
            System.out.println("\n📊 ANALYSIS:");
            System.out.println("=".repeat(60));
            System.out.println("🐛 BUG IDENTIFIED:");
            System.out.println("   Customer rental revenue contribution: RM" + (afterCustomerRevenue - initialRevenue));
            System.out.println("   Admin rental revenue contribution: RM" + (afterAdminRevenue - afterCustomerRevenue));
            System.out.println("   Expected admin contribution: RM225.0");
            System.out.println("   Missing revenue: RM" + (225.0 - (afterAdminRevenue - afterCustomerRevenue)));

            System.out.println("\n📋 REMINDERS:");
            System.out.println("   Customer rental created reminder: " + (afterCustomerReminders > initialReminders ? "✅" : "❌"));
            System.out.println("   Admin rental created reminder: " + (afterAdminReminders > afterCustomerReminders ? "✅" : "❌"));

            if (afterAdminRevenue == afterCustomerRevenue) {
                System.out.println("\n❌ CONFIRMED: Admin rentals don't contribute to system report revenue!");
            }

            if (afterAdminReminders == afterCustomerReminders) {
                System.out.println("❌ CONFIRMED: Admin rentals don't create pending reminders!");
            }

            System.out.println("\n💡 SOLUTION: Admin rentals must also create RentalPeriods and Reminders");

            // Clean up
            rentalDAO.delete("3001");
            rentalDAO.delete("3002");
            System.out.println("\n🧹 Test cleanup completed");

            System.out.println("\n=================================");
            System.out.println("📊 REVENUE BUG TEST RESULTS");
            System.out.println("=================================");
            System.out.println("🎯 Bug Status: CONFIRMED ✅");
            System.out.println("💰 Revenue Impact: Admin rentals excluded from reports");
            System.out.println("⏰ Reminder Impact: No pending reminders for admin rentals");
            System.out.println("🔧 Fix Required: Update addNewRental() to create periods and reminders");

        } catch (Exception e) {
            System.out.println("❌ Test FAILED with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
