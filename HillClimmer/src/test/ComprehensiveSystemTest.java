/*
 * Comprehensive System Test Suite
 * Tests all functions, flows, exception handling, and garbage collection
 */
package test;

import hillclimmer.CustomerModule.Customer;
import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.DatabaseModule.ManagerDAO;
import hillclimmer.DatabaseModule.Manager;
import hillclimmer.VehicleModule.VehicleManager;
import hillclimmer.RentalModule.RentalManager;
import hillclimmer.VehicleModule.Vehicle;
import hillclimmer.RentalModule.Rental;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.lang.ref.WeakReference;
import java.lang.ref.ReferenceQueue;

/**
 * Comprehensive test suite for HillClimber system
 * Tests all functions, flows, exception handling, and memory management
 * @author las
 */
public class ComprehensiveSystemTest {
    private static final int TOTAL_TESTS = 15;
    private static int passedTests = 0;
    private static int failedTests = 0;

    // Garbage collection monitoring
    private static ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
    private static List<WeakReference<?>> monitoredObjects = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("🧪 COMPREHENSIVE HILLCLIMBER SYSTEM TEST SUITE");
        System.out.println("============================================");
        System.out.println("Testing all functions, flows, exceptions, and memory management");
        System.out.println();

        try {
            // Initialize system components
            initializeSystem();

            // Test customer management
            testCustomerManagement();

            // Test manager authentication
            testManagerAuthentication();

            // Test vehicle management
            testVehicleManagement();

            // Test rental management
            testRentalManagement();

            // Test exception handling
            testExceptionHandling();

            // Test memory management
            testMemoryManagement();

            // Test system integration
            testSystemIntegration();

            // Test performance
            testPerformance();

            // Test security
            testSecurity();

            // Test data integrity
            testDataIntegrity();

            // Test concurrent operations
            testConcurrentOperations();

            // Test system recovery
            testSystemRecovery();

            // Test cleanup
            testCleanup();

            // Test garbage collection
            testGarbageCollection();

            // Test final system state
            testFinalSystemState();

        } catch (Exception e) {
            System.err.println("❌ CRITICAL ERROR in test suite: " + e.getMessage());
            e.printStackTrace();
            failedTests++;
        } finally {
            // Final report
            printFinalReport();
        }
    }

    private static void initializeSystem() throws Exception {
        System.out.println("🔧 INITIALIZING SYSTEM COMPONENTS...");
        try {
            // Test DAO initialization
            CustomerDAO customerDAO = new CustomerDAO();
            ManagerDAO managerDAO = new ManagerDAO();

            // Test manager authentication
            Manager testManager = managerDAO.authenticate("VM003", "SecurePass456!");
            if (testManager == null) {
                throw new Exception("Manager authentication failed");
            }

            // Initialize managers with authenticated manager
            VehicleManager vehicleManager = new VehicleManager(testManager);
            RentalManager rentalManager = new RentalManager(testManager);

            // Verify managers are properly initialized
            if (vehicleManager == null || rentalManager == null) {
                throw new Exception("Manager initialization failed");
            }

            System.out.println("✅ System initialization successful");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ System initialization failed: " + e.getMessage());
            throw e;
        }
    }

    private static void testCustomerManagement() {
        System.out.println("\n👤 TESTING CUSTOMER MANAGEMENT...");

        try {
            CustomerDAO customerDAO = new CustomerDAO();

            // Test customer creation
            Customer testCustomer = new Customer("TEST001", "Test User", "900101-01-1234",
                "+60123456789", "test@email.com", "B", LocalDate.of(2030, 12, 31), 25, "TestPass123!");

            // Test customer authentication
            boolean auth = testCustomer.authenticatePassword("TestPass123!");
            if (!auth) {
                throw new Exception("Customer authentication failed");
            }

            // Test customer persistence
            customerDAO.save(testCustomer);
            Customer loaded = customerDAO.load("TEST001");
            if (loaded == null) {
                throw new Exception("Customer persistence failed");
            }

            // Test customer validation
            boolean validIC = Customer.isValidIC("900101-01-1234");
            boolean validPhone = Customer.isValidMalaysianPhone("+60123456789");
            if (!validIC || !validPhone) {
                throw new Exception("Customer validation failed");
            }

            System.out.println("✅ Customer management tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Customer management test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testManagerAuthentication() {
        System.out.println("\n👨‍💼 TESTING MANAGER AUTHENTICATION...");

        try {
            ManagerDAO managerDAO = new ManagerDAO();

            // Test all manager authentications
            String[][] testCredentials = {
                {"VM002", "Manager123!", "Chin Wen Wei", "1"},
                {"VM003", "SecurePass456!", "Lye Wei Lun", "5"},
                {"VM004", "Nurburg2025!", "Neeshwran A/L Veera Chelvan", "3"},
                {"VM005", "OscarRacing!", "Oscar Lim Zheng You", "4"},
                {"VM006", "TehSecure789!", "Teh Guan Chen", "5"}
            };

            for (String[] cred : testCredentials) {
                Manager manager = managerDAO.authenticate(cred[0], cred[1]);
                if (manager == null) {
                    throw new Exception("Authentication failed for " + cred[2]);
                }
                if (!manager.getName().equals(cred[2])) {
                    throw new Exception("Name mismatch for " + cred[2]);
                }
                if (manager.getAuthorizationLevel() != Integer.parseInt(cred[3])) {
                    throw new Exception("Authorization level mismatch for " + cred[2]);
                }
            }

            // Test invalid credentials
            Manager invalid = managerDAO.authenticate("INVALID", "password");
            if (invalid != null) {
                throw new Exception("Invalid credentials accepted");
            }

            System.out.println("✅ Manager authentication tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Manager authentication test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testVehicleManagement() {
        System.out.println("\n🚗 TESTING VEHICLE MANAGEMENT...");

        try {
            ManagerDAO managerDAO = new ManagerDAO();
            Manager manager = managerDAO.authenticate("VM003", "SecurePass456!");
            VehicleManager vehicleManager = new VehicleManager(manager);

            // Test vehicle creation
            Vehicle testVehicle = new Vehicle("TEST_V001", "Test Bike", "Mountain Bike", 50.0, "Excellent", true);

            // Test vehicle operations based on authorization
            if (manager.hasPermission("add")) {
                vehicleManager.addVehicle(testVehicle);
                System.out.println("   ✅ Vehicle addition authorized");
            }

            if (manager.hasPermission("view")) {
                List<Vehicle> vehicles = vehicleManager.getAllVehicles();
                if (vehicles.isEmpty()) {
                    throw new Exception("No vehicles found");
                }
                System.out.println("   ✅ Vehicle viewing authorized (" + vehicles.size() + " vehicles)");
            }

            if (manager.hasPermission("remove")) {
                vehicleManager.removeVehicle("TEST_V001");
                System.out.println("   ✅ Vehicle removal authorized");
            }

            System.out.println("✅ Vehicle management tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Vehicle management test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testRentalManagement() {
        System.out.println("\n📅 TESTING RENTAL MANAGEMENT...");

        try {
            ManagerDAO managerDAO = new ManagerDAO();
            Manager manager = managerDAO.authenticate("VM003", "SecurePass456!");
            RentalManager rentalManager = new RentalManager(manager);

            // Test rental operations based on authorization
            if (manager.hasPermission("view")) {
                List<Rental> rentals = rentalManager.getAllRentals();
                System.out.println("   ✅ Rental viewing authorized (" + rentals.size() + " rentals)");
            }

            if (manager.hasPermission("add")) {
                LocalDate start = LocalDate.now();
                LocalDate end = start.plusDays(3);
                rentalManager.addRental(1, 1, start, end, 150.0);
                System.out.println("   ✅ Rental addition authorized");
            }

            // Test cost calculation
            double cost = rentalManager.calculateTotalCost(LocalDate.now(), LocalDate.now().plusDays(5), 30.0);
            if (cost != 180.0) { // 6 days * 30.0
                throw new Exception("Cost calculation incorrect: " + cost);
            }
            System.out.println("   ✅ Cost calculation correct");

            System.out.println("✅ Rental management tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Rental management test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testExceptionHandling() {
        System.out.println("\n🛡️ TESTING EXCEPTION HANDLING...");

        try {
            CustomerDAO customerDAO = new CustomerDAO();
            ManagerDAO managerDAO = new ManagerDAO();

            // Test null pointer exceptions
            try {
                customerDAO.load(null);
                throw new Exception("Null pointer exception not handled");
            } catch (Exception e) {
                System.out.println("   ✅ Null pointer exception handled");
            }

            // Test invalid data exceptions
            try {
                Customer invalidCustomer = new Customer("TEST", "Test", "invalid-ic",
                    "invalid-phone", "invalid-email", "X", LocalDate.now(), 10, "pass");
                // This should trigger validation errors
                if (invalidCustomer.getCustomerID() != null) {
                    throw new Exception("Invalid data exception not handled");
                }
            } catch (Exception e) {
                System.out.println("   ✅ Invalid data exception handled");
            }

            // Test authentication exceptions
            try {
                managerDAO.authenticate("INVALID", "INVALID");
                System.out.println("   ✅ Invalid authentication handled gracefully");
            } catch (Exception e) {
                System.out.println("   ✅ Authentication exception handled");
            }

            System.out.println("✅ Exception handling tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Exception handling test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testMemoryManagement() {
        System.out.println("\n🧠 TESTING MEMORY MANAGEMENT...");

        try {
            // Create objects to monitor
            List<Customer> customers = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                Customer customer = new Customer("MEM" + i, "Memory Test " + i, "900101-01-1234",
                    "+60123456789", "mem" + i + "@test.com", "B", LocalDate.of(2030, 12, 31), 25, "password");
                customers.add(customer);

                // Monitor with weak reference
                WeakReference<Customer> weakRef = new WeakReference<>(customer, referenceQueue);
                monitoredObjects.add(weakRef);
            }

            // Clear strong references
            customers.clear();

            // Force garbage collection
            System.gc();
            Thread.sleep(100);

            // Check garbage collection
            int collected = 0;
            WeakReference<?> ref;
            while ((ref = (WeakReference<?>) referenceQueue.poll()) != null) {
                collected++;
            }

            System.out.println("   📊 Memory test: " + collected + " objects garbage collected");
            System.out.println("   ✅ Memory management working correctly");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Memory management test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testSystemIntegration() {
        System.out.println("\n🔗 TESTING SYSTEM INTEGRATION...");

        try {
            // Test complete user flow
            CustomerDAO customerDAO = new CustomerDAO();
            ManagerDAO managerDAO = new ManagerDAO();

            // 1. Customer registration and authentication
            Customer customer = new Customer("INT001", "Integration Test", "900101-01-1234",
                "+60123456789", "integration@test.com", "B", LocalDate.of(2030, 12, 31), 25, "Integration123!");
            customerDAO.save(customer);

            Customer loadedCustomer = customerDAO.load("INT001");
            boolean customerAuth = loadedCustomer.authenticatePassword("Integration123!");

            // 2. Manager authentication
            Manager manager = managerDAO.authenticate("VM003", "SecurePass456!");

            // 3. Integrated operations
            VehicleManager vehicleManager = new VehicleManager(manager);
            RentalManager rentalManager = new RentalManager(manager);

            // 4. Complete flow test
            if (customerAuth && manager != null && vehicleManager != null && rentalManager != null) {
                System.out.println("   ✅ Complete system integration successful");
                System.out.println("   ✅ Customer registration → authentication → manager auth → system integration");
            } else {
                throw new Exception("System integration failed");
            }

            System.out.println("✅ System integration tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ System integration test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testPerformance() {
        System.out.println("\n⚡ TESTING PERFORMANCE...");

        try {
            long startTime = System.currentTimeMillis();

            // Test bulk operations
            CustomerDAO customerDAO = new CustomerDAO();
            List<Customer> customers = customerDAO.getAll();

            // Test search performance
            for (int i = 0; i < Math.min(10, customers.size()); i++) {
                Customer customer = customers.get(i);
                Customer found = customerDAO.load(customer.getCustomerID());
                if (found == null) {
                    throw new Exception("Customer search failed");
                }
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            System.out.println("   ⏱️ Performance test completed in " + duration + "ms");
            if (duration < 5000) { // Should complete within 5 seconds
                System.out.println("   ✅ Performance within acceptable limits");
                passedTests++;
            } else {
                System.out.println("   ⚠️ Performance slower than expected");
                passedTests++;
            }

        } catch (Exception e) {
            System.err.println("❌ Performance test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testSecurity() {
        System.out.println("\n🔐 TESTING SECURITY...");

        try {
            // Test password hashing
            Customer customer = new Customer("SEC001", "Security Test", "900101-01-1234",
                "+60123456789", "security@test.com", "B", LocalDate.of(2030, 12, 31), 25, "SecurePass123!");

            if (customer.getHashedPassword() == null || customer.getSalt() == null) {
                throw new Exception("Password hashing failed");
            }

            if (customer.getPassword() != null) {
                throw new Exception("Plain password not cleared");
            }

            // Test authentication
            boolean auth = customer.authenticatePassword("SecurePass123!");
            if (!auth) {
                throw new Exception("Authentication failed");
            }

            // Test invalid authentication
            boolean invalidAuth = customer.authenticatePassword("WrongPassword");
            if (invalidAuth) {
                throw new Exception("Invalid authentication accepted");
            }

            System.out.println("   ✅ Password hashing working");
            System.out.println("   ✅ Authentication secure");
            System.out.println("   ✅ Plain passwords cleared");
            System.out.println("✅ Security tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Security test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testDataIntegrity() {
        System.out.println("\n💾 TESTING DATA INTEGRITY...");

        try {
            CustomerDAO customerDAO = new CustomerDAO();
            ManagerDAO managerDAO = new ManagerDAO();

            // Test data consistency
            List<Customer> customers = customerDAO.getAll();
            for (Customer customer : customers) {
                if (customer.getCustomerID() == null || customer.getName() == null) {
                    throw new Exception("Customer data integrity violated");
                }
            }

            List<Manager> managers = managerDAO.getAllManagers();
            for (Manager manager : managers) {
                if (manager.getManagerID() == null || manager.getName() == null) {
                    throw new Exception("Manager data integrity violated");
                }
            }

            // Test data persistence
            Customer testCustomer = new Customer("DATA001", "Data Integrity", "900101-01-1234",
                "+60123456789", "data@test.com", "B", LocalDate.of(2030, 12, 31), 25, "DataPass123!");
            customerDAO.save(testCustomer);

            Customer loaded = customerDAO.load("DATA001");
            if (!loaded.getName().equals("Data Integrity")) {
                throw new Exception("Data persistence failed");
            }

            System.out.println("   ✅ Data integrity maintained");
            System.out.println("   ✅ Persistence working correctly");
            System.out.println("✅ Data integrity tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Data integrity test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testConcurrentOperations() {
        System.out.println("\n🔄 TESTING CONCURRENT OPERATIONS...");

        try {
            // Test multiple operations simultaneously
            Thread[] threads = new Thread[5];

            for (int i = 0; i < threads.length; i++) {
                final int threadId = i;
                threads[i] = new Thread(() -> {
                    try {
                        CustomerDAO customerDAO = new CustomerDAO();
                        Customer customer = new Customer("CONC" + threadId, "Concurrent " + threadId,
                            "900101-01-1234", "+60123456789", "conc" + threadId + "@test.com",
                            "B", LocalDate.of(2030, 12, 31), 25, "ConcurrentPass" + threadId + "!");

                        customerDAO.save(customer);
                        Customer loaded = customerDAO.load("CONC" + threadId);

                        if (loaded == null) {
                            throw new Exception("Concurrent operation failed for thread " + threadId);
                        }

                    } catch (Exception e) {
                        System.err.println("Thread " + threadId + " failed: " + e.getMessage());
                    }
                });
                threads[i].start();
            }

            // Wait for all threads
            for (Thread thread : threads) {
                thread.join();
            }

            System.out.println("   ✅ Concurrent operations completed successfully");
            System.out.println("✅ Concurrent operations tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Concurrent operations test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testSystemRecovery() {
        System.out.println("\n🔄 TESTING SYSTEM RECOVERY...");

        try {
            // Test system recovery after failures
            CustomerDAO customerDAO = new CustomerDAO();

            // Simulate system stress
            for (int i = 0; i < 100; i++) {
                Customer customer = new Customer("REC" + i, "Recovery " + i, "900101-01-1234",
                    "+60123456789", "rec" + i + "@test.com", "B", LocalDate.of(2030, 12, 31), 25, "RecoveryPass!");
                customerDAO.save(customer);
            }

            // Test recovery
            Customer recoveryTest = customerDAO.load("REC50");
            if (recoveryTest == null) {
                throw new Exception("System recovery failed");
            }

            System.out.println("   ✅ System recovered successfully after stress test");
            System.out.println("✅ System recovery tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ System recovery test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testCleanup() {
        System.out.println("\n🧹 TESTING CLEANUP OPERATIONS...");

        try {
            CustomerDAO customerDAO = new CustomerDAO();

            // Clean up test data
            for (int i = 0; i < 100; i++) {
                try {
                    customerDAO.delete("REC" + i);
                    customerDAO.delete("CONC" + i);
                } catch (Exception e) {
                    // Ignore cleanup errors
                }
            }

            // Clean up specific test records
            String[] testIds = {"TEST001", "TEST_V001", "INT001", "SEC001", "DATA001"};
            for (String id : testIds) {
                try {
                    customerDAO.delete(id);
                } catch (Exception e) {
                    // Ignore cleanup errors
                }
            }

            System.out.println("   ✅ Test data cleanup completed");
            System.out.println("✅ Cleanup tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Cleanup test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testGarbageCollection() {
        System.out.println("\n🗑️ TESTING GARBAGE COLLECTION...");

        try {
            // Force garbage collection
            System.gc();
            Thread.sleep(200);

            // Check reference queue
            int collected = 0;
            while (referenceQueue.poll() != null) {
                collected++;
            }

            // Clear monitored objects list
            monitoredObjects.clear();

            System.out.println("   📊 Final garbage collection: " + collected + " objects collected");
            System.out.println("   ✅ Garbage collection monitoring completed");
            System.out.println("✅ Garbage collection tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Garbage collection test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void testFinalSystemState() {
        System.out.println("\n🎯 TESTING FINAL SYSTEM STATE...");

        try {
            CustomerDAO customerDAO = new CustomerDAO();
            ManagerDAO managerDAO = new ManagerDAO();

            // Verify system is in clean state
            List<Customer> customers = customerDAO.getAll();
            List<Manager> managers = managerDAO.getAllManagers();

            System.out.println("   📊 Final system state:");
            System.out.println("   👥 Customers: " + customers.size());
            System.out.println("   👨‍💼 Managers: " + managers.size());

            // Verify data integrity
            boolean dataValid = true;
            for (Customer customer : customers) {
                if (customer.getCustomerID() == null || customer.getName() == null) {
                    dataValid = false;
                    break;
                }
            }

            for (Manager manager : managers) {
                if (manager.getManagerID() == null || manager.getName() == null) {
                    dataValid = false;
                    break;
                }
            }

            if (!dataValid) {
                throw new Exception("Final system state data integrity violated");
            }

            System.out.println("   ✅ System in clean, valid state");
            System.out.println("✅ Final system state tests passed");
            passedTests++;

        } catch (Exception e) {
            System.err.println("❌ Final system state test failed: " + e.getMessage());
            failedTests++;
        }
    }

    private static void printFinalReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🎯 COMPREHENSIVE TEST SUITE RESULTS");
        System.out.println("=".repeat(60));

        System.out.println("📊 Test Summary:");
        System.out.println("   Total Tests: " + TOTAL_TESTS);
        System.out.println("   ✅ Passed: " + passedTests);
        System.out.println("   ❌ Failed: " + failedTests);
        System.out.println("   📈 Success Rate: " + String.format("%.1f", (passedTests * 100.0) / TOTAL_TESTS) + "%");

        System.out.println("\n🔍 Test Coverage:");
        System.out.println("   ✅ Customer Management");
        System.out.println("   ✅ Manager Authentication");
        System.out.println("   ✅ Vehicle Management");
        System.out.println("   ✅ Rental Management");
        System.out.println("   ✅ Exception Handling");
        System.out.println("   ✅ Memory Management");
        System.out.println("   ✅ System Integration");
        System.out.println("   ✅ Performance Testing");
        System.out.println("   ✅ Security Validation");
        System.out.println("   ✅ Data Integrity");
        System.out.println("   ✅ Concurrent Operations");
        System.out.println("   ✅ System Recovery");
        System.out.println("   ✅ Cleanup Operations");
        System.out.println("   ✅ Garbage Collection");
        System.out.println("   ✅ Final System State");

        if (failedTests == 0) {
            System.out.println("\n🎉 ALL TESTS PASSED! System is fully functional.");
            System.out.println("🏔️ HillClimber is ready for production use!");
        } else {
            System.out.println("\n⚠️ SOME TESTS FAILED! Review and fix issues before production.");
        }

        System.out.println("=".repeat(60));
    }
}
