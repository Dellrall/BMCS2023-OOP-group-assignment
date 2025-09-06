package test;

import hillclimmer.DurationModule.DurationManager;
import hillclimmer.DatabaseModule.ManagerDAO;
import hillclimmer.VehicleModule.VehicleManager;
import hillclimmer.DatabaseModule.Manager;
import java.time.LocalDate;

/**
 * Simple test to verify concurrency fixes
 */
public class SimpleConcurrencyTest {

    public static void main(String[] args) {
        System.out.println("🧪 Simple Concurrency Test");
        System.out.println("==========================");

        try {
            // Initialize managers
            ManagerDAO managerDAO = new ManagerDAO();
            Manager testManager = managerDAO.getAllManagers().get(0);
            VehicleManager vehicleManager = new VehicleManager(testManager);
            DurationManager durationManager = new DurationManager();

            System.out.println("✅ Managers initialized");

            // Test basic operations
            System.out.println("Testing basic operations...");

            // Test DurationManager
            durationManager.createBasicRentalPeriod(1000, LocalDate.now(), LocalDate.now().plusDays(1), 50.0);
            System.out.println("✅ DurationManager operation successful");

            // Test VehicleManager
            int vehicleCount = vehicleManager.getAllVehicles().size();
            System.out.println("✅ VehicleManager operation successful, vehicles: " + vehicleCount);

            // Test concurrent access
            System.out.println("Testing concurrent access...");

            Thread t1 = new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    durationManager.createBasicRentalPeriod(2000 + i, LocalDate.now(), LocalDate.now().plusDays(1), 50.0);
                }
                System.out.println("✅ Thread 1 completed");
            });

            Thread t2 = new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    durationManager.createReturnReminder(2000 + i, LocalDate.now().plusDays(1).atStartOfDay());
                }
                System.out.println("✅ Thread 2 completed");
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            int finalPeriods = durationManager.getAllRentalPeriods().size();
            int finalReminders = durationManager.getPendingReminders().size();

            System.out.println("📊 Final state:");
            System.out.println("   Rental periods: " + finalPeriods);
            System.out.println("   Pending reminders: " + finalReminders);

            if (finalPeriods >= 6 && finalReminders >= 5) { // 1 initial + 5 from threads
                System.out.println("✅ CONCURRENCY TEST PASSED: Operations completed successfully");
            } else {
                System.out.println("❌ CONCURRENCY TEST FAILED: Data inconsistency detected");
            }

        } catch (Exception e) {
            System.out.println("❌ Test FAILED with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
