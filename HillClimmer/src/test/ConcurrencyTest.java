package test;

import hillclimmer.DurationModule.DurationManager;
import hillclimmer.RentalModule.RentalManager;
import hillclimmer.VehicleModule.VehicleManager;
import hillclimmer.DatabaseModule.Manager;
import hillclimmer.DatabaseModule.ManagerDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test to demonstrate concurrency issues in the system
 */
public class ConcurrencyTest {

    public static void main(String[] args) {
        System.out.println("🧪 Concurrency Test");
        System.out.println("===================");

        try {
            // Initialize managers
            ManagerDAO managerDAO = new ManagerDAO();
            Manager testManager = managerDAO.getAllManagers().get(0);
            VehicleManager vehicleManager = new VehicleManager(testManager);
            RentalManager rentalManager = new RentalManager();
            DurationManager durationManager = new DurationManager();

            System.out.println("✅ Managers initialized");

            // Test 1: Concurrent DurationManager operations
            System.out.println("Starting DurationManager test...");
            try {
                testDurationManagerConcurrency(durationManager);
                System.out.println("DurationManager test completed");
            } catch (Exception e) {
                System.out.println("❌ DurationManager test failed: " + e.getMessage());
                e.printStackTrace();
            }

            // Test 2: Concurrent RentalManager operations
            System.out.println("Starting RentalManager test...");
            testRentalManagerConcurrency(rentalManager);
            System.out.println("RentalManager test completed");

            // Test 3: Concurrent VehicleManager operations
            System.out.println("Starting VehicleManager test...");
            testVehicleManagerConcurrency(vehicleManager);
            System.out.println("VehicleManager test completed");

            System.out.println("\n🎯 Concurrency Test Complete");
            System.out.println("If you see inconsistent results above, concurrency issues exist!");

        } catch (Exception e) {
            System.out.println("❌ Test FAILED with exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testDurationManagerConcurrency(DurationManager durationManager) {
        System.out.println("DURATION MANAGER TEST STARTED");
        System.out.println("\n🔄 Testing DurationManager Concurrency...");

        // Simple test first
        try {
            System.out.println("Testing simple DurationManager operation...");
            durationManager.createBasicRentalPeriod(1000, LocalDate.now(), LocalDate.now().plusDays(1), 50.0);
            System.out.println("Simple test passed");
        } catch (Exception e) {
            System.out.println("Simple test failed: " + e.getMessage());
            return;
        }

        final int THREAD_COUNT = 2; // Reduce thread count for testing
        final int OPERATIONS_PER_THREAD = 2; // Reduce operations for testing
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);

        System.out.println("Starting " + THREAD_COUNT + " threads with " + OPERATIONS_PER_THREAD + " operations each...");

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        // Create rental period
                        durationManager.createBasicRentalPeriod(
                            threadId * 100 + j,
                            LocalDate.now(),
                            LocalDate.now().plusDays(1),
                            50.0
                        );

                        // Create reminder
                        durationManager.createReturnReminder(
                            threadId * 100 + j,
                            LocalDateTime.now().plusDays(1)
                        );
                    }
                    successCount.incrementAndGet();
                    System.out.println("Thread " + threadId + " completed successfully");
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                    System.out.println("❌ DurationManager error in thread " + threadId + ": " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
            System.out.println("All threads completed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executor.shutdown();

        int expectedPeriods = THREAD_COUNT * OPERATIONS_PER_THREAD;
        int expectedReminders = THREAD_COUNT * OPERATIONS_PER_THREAD;
        int actualPeriods = durationManager.getAllRentalPeriods().size();
        int actualReminders = durationManager.getPendingReminders().size();

        System.out.println("📊 DurationManager Results:");
        System.out.println("   Expected periods: " + expectedPeriods + ", Actual: " + actualPeriods);
        System.out.println("   Expected reminders: " + expectedReminders + ", Actual: " + actualReminders);
        System.out.println("   Success threads: " + successCount.get() + ", Error threads: " + errorCount.get());

        if (actualPeriods != expectedPeriods || actualReminders != expectedReminders) {
            System.out.println("❌ CONCURRENCY ISSUE: Data loss or corruption detected!");
        } else {
            System.out.println("✅ DurationManager concurrency test passed");
        }
    }

    private static void testRentalManagerConcurrency(RentalManager rentalManager) {
        System.out.println("\n🔄 Testing RentalManager Concurrency...");

        final int THREAD_COUNT = 5;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    // Add rental
                    rentalManager.addRentalWithId(
                        5000 + threadId,
                        1,
                        1,
                        LocalDate.now(),
                        LocalDate.now().plusDays(1),
                        50.0
                    );
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                    System.out.println("❌ RentalManager error in thread " + threadId + ": " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executor.shutdown();

        System.out.println("📊 RentalManager Results:");
        System.out.println("   Success threads: " + successCount.get() + ", Error threads: " + errorCount.get());

        if (errorCount.get() > 0) {
            System.out.println("❌ CONCURRENCY ISSUE: Rental operations failed concurrently!");
        } else {
            System.out.println("✅ RentalManager concurrency test passed");
        }
    }

    private static void testVehicleManagerConcurrency(VehicleManager vehicleManager) {
        System.out.println("\n🔄 Testing VehicleManager Concurrency...");

        final int THREAD_COUNT = 5;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger errorCount = new AtomicInteger(0);

        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    // Try to access vehicle list (this could be problematic if not synchronized)
                    int vehicleCount = vehicleManager.getAllVehicles().size();
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    errorCount.incrementAndGet();
                    System.out.println("❌ VehicleManager error in thread " + threadId + ": " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executor.shutdown();

        System.out.println("📊 VehicleManager Results:");
        System.out.println("   Success threads: " + successCount.get() + ", Error threads: " + errorCount.get());

        if (errorCount.get() > 0) {
            System.out.println("❌ CONCURRENCY ISSUE: Vehicle operations failed concurrently!");
        } else {
            System.out.println("✅ VehicleManager concurrency test passed");
        }
    }
}
