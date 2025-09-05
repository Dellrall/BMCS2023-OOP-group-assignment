/*
 * HillClimmer - PRODUCTION Main Application Class
 * Malaysia Hill Climbing Vehicle Rental System
 *
 * PRODUCTION FEATURES:
 * ✅ User Authentication (Customer & Manager Login)
 * ✅ Vehicle Rental System
 * ✅ Payment Processing (Credit Card, Online Banking, Cash)
 * ✅ Customer Profile Management
 * ✅ Payment History & Receipts
 * ✅ Vehicle Inventory Management
 *
 * FOR PRODUCTION: This is the main application class
 */
package hillclimmer;

import hillclimmer.CustomerModule.*;
import hillclimmer.VehicleModule.*;
import hillclimmer.PaymentModule.*;
import hillclimmer.RentalModule.*;
import hillclimmer.DurationModule.*;
import hillclimmer.DatabaseModule.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.Console;

/**
 * PRODUCTION Main Application Class for HillClimmer Malaysia Hill Climbing Vehicle Rental System
 * Contains full production functionality with login, rental, payment, and history features
 *
 * @author las
 */
public class HillClimmer {
    private static final Scanner scanner = new Scanner(System.in);
    private static CustomerDAO customerDAO = new CustomerDAO();
    private static ManagerDAO managerDAO = new ManagerDAO();
    private static VehicleManager vehicleManager;
    private static RentalManager rentalManager = new RentalManager();
    private static DurationManager durationManager = new DurationManager();
    private static TransactionManager transactionManager = new TransactionManager("TM001");

    // Current logged in user
    private static Customer currentCustomer = null;
    private static Manager currentManager = null;
    private static boolean isManagerMode = false;

    /**
     * Custom exception for user exit requests
     */
    public static class UserExitException extends Exception {
        public UserExitException(String message) {
            super(message);
        }
    }

    /**
     * Reads password input with asterisks masking for privacy
     * @param prompt The prompt message to display
     * @return The entered password
     * @throws UserExitException if user enters "0" to exit
     */
    public static String readPassword(String prompt) throws UserExitException {
        System.out.print(prompt);

        // Try using Console first (most secure)
        Console console = System.console();
        if (console != null) {
            char[] passwordArray = console.readPassword();
            System.out.println(); // Move to next line
            String password = new String(passwordArray);
            if (password.equals("0")) {
                throw new UserExitException("User requested to exit to previous menu");
            }
            return password;
        }

        // Fallback: Use Scanner for environments where Console is not available
        // This works better with Ant and IDEs
        String password = scanner.nextLine().trim();

        if (password.equals("0")) {
            throw new UserExitException("User requested to exit to previous menu");
        }

        // Show asterisks for the length of the password
        for (int i = 0; i < password.length(); i++) {
            System.out.print("*");
        }
        System.out.println(); // Move to next line

        return password;
    }

    /**
     * Clears the console screen
     */
    private static void clearScreen() {
        try {
            // Try ANSI escape codes first (works on most terminals)
            System.out.print("\033[2J\033[H");
            System.out.flush();
        } catch (Exception e) {
            // Fallback: print multiple newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    /**
     * Switches to alternate screen buffer (preserves original screen)
     */
    private static void enterAlternateScreen() {
        try {
            System.out.print("\033[?1049h");
            System.out.flush();
        } catch (Exception e) {
            // Fallback: just clear screen
            clearScreen();
        }
    }

    /**
     * Switches back to main screen buffer
     */
    private static void exitAlternateScreen() {
        try {
            System.out.print("\033[?1049l");
            System.out.flush();
        } catch (Exception e) {
            // No fallback needed
        }
    }

    /**
     * Reads and validates integer input within a specified range
     * @param prompt The prompt message
     * @param min Minimum acceptable value
     * @param max Maximum acceptable value
     * @return Valid integer input
     * @throws UserExitException if user enters "0" to exit
     */
    public static int readInt(String prompt, int min, int max) throws UserExitException {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                
                if (input.equals("0")) {
                    throw new UserExitException("User requested to exit to previous menu");
                }
                
                if (input.isEmpty()) {
                    System.out.println("❌ Input cannot be empty. Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                
                int value = Integer.parseInt(input);
                
                if (value < min || value > max) {
                    System.out.println("❌ Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number format. Please enter a valid number between " + min + " and " + max + ".");
            }
        }
    }

    /**
     * Reads and validates double input within a specified range
     * @param prompt The prompt message
     * @param min Minimum acceptable value
     * @param max Maximum acceptable value
     * @return Valid double input
     * @throws UserExitException if user enters "0" to exit
     */
    public static double readDouble(String prompt, double min, double max) throws UserExitException {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                
                if (input.equals("0")) {
                    throw new UserExitException("User requested to exit to previous menu");
                }
                
                if (input.isEmpty()) {
                    System.out.println("❌ Input cannot be empty. Please enter a valid amount.");
                    continue;
                }
                
                double value = Double.parseDouble(input);
                
                if (value < min || value > max) {
                    System.out.println("❌ Please enter an amount between " + String.format("%.2f", min) + " and " + String.format("%.2f", max) + ".");
                    continue;
                }
                
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number format. Please enter a valid decimal number.");
            }
        }
    }

    /**
     * Reads and validates non-empty string input
     * @param prompt The prompt message
     * @return Valid non-empty string
     * @throws UserExitException if user enters "0" to exit
     */
    public static String readString(String prompt) throws UserExitException {
        return readString(prompt, 1, Integer.MAX_VALUE);
    }

    /**
     * Reads and validates string input with length constraints
     * @param prompt The prompt message
     * @param minLength Minimum length required
     * @param maxLength Maximum length allowed
     * @return Valid string input
     * @throws UserExitException if user enters "0" to exit
     */
    static String readString(String prompt, int minLength, int maxLength) throws UserExitException {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            
            if (input.equals("0")) {
                throw new UserExitException("User requested to exit to previous menu");
            }
            
            if (input.isEmpty()) {
                System.out.println("❌ Input cannot be empty. Please enter a value.");
                continue;
            }
            
            if (input.length() < minLength) {
                System.out.println("❌ Input must be at least " + minLength + " characters long.");
                continue;
            }
            
            if (input.length() > maxLength) {
                System.out.println("❌ Input cannot exceed " + maxLength + " characters.");
                continue;
            }
            
            return input;
        }
    }

    /**
     * Reads and validates email input
     * @param prompt The prompt message
     * @return Valid email address
     * @throws UserExitException if user enters "0" to exit
     */
    public static String readEmail(String prompt) throws UserExitException {
        while (true) {
            String email = readString(prompt);
            
            // Basic email validation
            if (!email.contains("@") || !email.contains(".")) {
                System.out.println("❌ Invalid email format. Please enter a valid email address (e.g., user@example.com).");
                continue;
            }
            
            if (email.length() < 5) {
                System.out.println("❌ Email address is too short. Please enter a valid email address.");
                continue;
            }
            
            return email;
        }
    }

    /**
     * Reads and validates date input in DD/MM/YYYY format
     * @param prompt The prompt message
     * @param allowPastDates Whether to allow past dates
     * @return Valid LocalDate
     * @throws UserExitException if user enters "0" to exit
     */
    public static LocalDate readDate(String prompt, boolean allowPastDates) throws UserExitException {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                
                if (input.equals("0")) {
                    throw new UserExitException("User requested to exit to previous menu");
                }
                
                if (input.isEmpty()) {
                    System.out.println("❌ Date cannot be empty. Please enter a date in DD/MM/YYYY format.");
                    continue;
                }
                
                LocalDate date = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                
                if (!allowPastDates && date.isBefore(LocalDate.now())) {
                    System.out.println("❌ Date cannot be in the past. Please enter a future date.");
                    continue;
                }
                
                return date;
            } catch (Exception e) {
                System.out.println("❌ Invalid date format. Please enter a date in DD/MM/YYYY format (e.g., 31/12/2025).");
            }
        }
    }

    /**
     * Reads and validates Malaysian IC number
     * @param prompt The prompt message
     * @return Valid IC number
     * @throws UserExitException if user enters "0" to exit
     */
    public static String readIC(String prompt) throws UserExitException {
        while (true) {
            String ic = readString(prompt);
            
            if (!Customer.isValidIC(ic)) {
                System.out.println("❌ Invalid IC number format. Please use XXXXXX-XX-XXXX format (e.g., 950101-14-5678).");
                continue;
            }
            
            return ic;
        }
    }

    /**
     * Reads and validates Malaysian phone number
     * @param prompt The prompt message
     * @return Valid phone number in +60XXXXXXXXX format
     * @throws UserExitException if user enters "0" to exit
     */
    public static String readPhone(String prompt) throws UserExitException {
        while (true) {
            String phone = readString(prompt);
            
            if (!Customer.isValidMalaysianPhoneInput(phone)) {
                System.out.println("❌ Invalid Malaysian phone number format. Please use +60XXXXXXXXX, 0XXXXXXXXX, 0xx-xxx-xxxx, or 0xx xxx xxxx format.");
                continue;
            }
            
            return Customer.normalizeMalaysianPhone(phone);
        }
    }

    /**
     * Reads and validates license type
     * @param prompt The prompt message
     * @return Valid license type
     * @throws UserExitException if user enters "0" to exit
     */
    public static String readLicenseType(String prompt) throws UserExitException {
        while (true) {
            System.out.print(prompt);
            String licenseType = scanner.nextLine().trim().toUpperCase();
            
            if (licenseType.equals("0")) {
                throw new UserExitException("User requested to exit to previous menu");
            }
            
            if (!Customer.isValidLicenseType(licenseType)) {
                System.out.println("❌ Invalid license type. Valid types: B, B2, D, DA, E, E1, E2");
                continue;
            }
            
            return licenseType;
        }
    }

    /**
     * Reads and validates password with minimum length
     * @param prompt The prompt message
     * @param minLength Minimum password length
     * @return Valid password
     * @throws UserExitException if user enters "0" to exit
     */
    static String readPassword(String prompt, int minLength) throws UserExitException {
        while (true) {
            String password = readPassword(prompt);
            
            if (password.length() < minLength) {
                System.out.println("❌ Password must be at least " + minLength + " characters long.");
                continue;
            }
            
            return password;
        }
    }

    /**
     * Reads and validates customer ID format
     * @param prompt The prompt message
     * @return Valid customer ID
     * @throws UserExitException if user enters "0" to exit
     */
    public static String readCustomerId(String prompt) throws UserExitException {
        while (true) {
            String customerId = readString(prompt).toUpperCase();
            
            if (!customerId.matches("C\\d{3,}")) {
                System.out.println("❌ Invalid customer ID format. Please use format CXXX (e.g., C001).");
                continue;
            }
            
            return customerId;
        }
    }

    /**
     * Reads and validates manager ID format
     * @param prompt The prompt message
     * @return Valid manager ID
     * @throws UserExitException if user enters "0" to exit
     */
    public static String readManagerId(String prompt) throws UserExitException {
        while (true) {
            String managerId = readString(prompt).toUpperCase();
            
            if (!managerId.matches("VM\\d{3,}")) {
                System.out.println("❌ Invalid manager ID format. Please use format VMXXX (e.g., VM001).");
                continue;
            }
            
            return managerId;
        }
    }

    /**
     * Clears screen and displays new content with a smooth transition
     */
    private static void transitionToScreen() {
        clearScreen();
        try {
            Thread.sleep(100); // Small delay for smooth transition
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        // Initialize garbage collection monitoring
        Runtime runtime = Runtime.getRuntime();
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();

        try {
            // Enter alternate screen buffer for clean experience
            enterAlternateScreen();

            System.out.println("=========================================");
            System.out.println("   🏔️  HillClimmer VEHICLE RENTAL   🏔️");
            System.out.println("        Malaysia's Premier Hill");
            System.out.println("        Climbing Vehicle Service");
            System.out.println("=========================================");
            System.out.println("          PRODUCTION VERSION");
            System.out.println("=========================================");

            initializeSystem();
            runProductionMode();

        } catch (Exception e) {
            System.err.println("\n❌ CRITICAL ERROR: " + e.getMessage());
            e.printStackTrace();
            System.err.println("\n🔄 Attempting graceful shutdown...");

            // Attempt cleanup
            try {
                cleanupResources();
            } catch (Exception cleanupError) {
                System.err.println("❌ Cleanup failed: " + cleanupError.getMessage());
            }

        } catch (Throwable t) {
            System.err.println("\n💥 FATAL ERROR: " + t.getMessage());
            t.printStackTrace();

        } finally {
            // Always exit alternate screen buffer
            try {
                exitAlternateScreen();
            } catch (Exception e) {
                // Ignore cleanup errors in finally block
            }

            // Force garbage collection before exit
            System.gc();
            Thread.yield();

            // Memory usage report
            long finalMemory = runtime.totalMemory() - runtime.freeMemory();
            long memoryUsed = finalMemory - initialMemory;

            System.out.println("\n📊 Memory Usage Report:");
            System.out.println("   Initial Memory: " + (initialMemory / 1024) + " KB");
            System.out.println("   Final Memory: " + (finalMemory / 1024) + " KB");
            System.out.println("   Memory Used: " + (memoryUsed / 1024) + " KB");
            System.out.println("   🗑️ Garbage collection completed");

            System.out.println("\n👋 Thank you for using HillClimmer!");
            System.out.println("🏔️ Safe travels and happy climbing!");
        }
    }

    private static void initializeSystem() {
        try {
            System.out.println("🔧 Initializing HillClimmer system...");

            // Initialize default vehicle manager with exception handling
            try {
                vehicleManager = new VehicleManager("VM001", 2, "Ahmad Abdullah", 5);
                System.out.println("   ✅ Vehicle manager initialized");
            } catch (Exception e) {
                System.err.println("   ❌ Vehicle manager initialization failed: " + e.getMessage());
                throw new RuntimeException("Failed to initialize vehicle manager", e);
            }

            // Load vehicles from CSV file with exception handling
            try {
                loadVehiclesFromCSV();
                System.out.println("   ✅ Vehicles loaded from CSV");
            } catch (Exception e) {
                System.err.println("   ❌ Vehicle loading failed: " + e.getMessage());
                throw new RuntimeException("Failed to load vehicles", e);
            }

            // Add sample customer for testing with exception handling
            try {
                Customer sampleCustomer = new Customer("C001", "Muhammad Ali", "950101-14-5678",
                    "+60123456789", "muhammad@email.com", "B", LocalDate.of(2026, 12, 31),
                    29, "password123");
                customerDAO.save(sampleCustomer);
                System.out.println("   ✅ Sample customer created");
            } catch (Exception e) {
                System.out.println("   ⚠️ Sample customer creation skipped (may already exist): " + e.getMessage());
            }

            System.out.println("✅ System initialization completed successfully");

        } catch (RuntimeException e) {
            System.err.println("❌ System initialization failed: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println("❌ Unexpected error during initialization: " + e.getMessage());
            throw new RuntimeException("System initialization failed", e);
        }
    }

    private static void loadVehiclesFromCSV() {
        try {
            // The vehicles are loaded automatically by the VehicleManager from the CSV file
            System.out.println("Loading vehicles from CSV file...");
        } catch (Exception e) {
            System.out.println("Error loading vehicles from CSV: " + e.getMessage());
        }
    }

    private static void cleanupResources() {
        System.out.println("🧹 Cleaning up resources...");

        try {
            // Clear current user sessions
            currentCustomer = null;
            currentManager = null;
            isManagerMode = false;

            // Force garbage collection
            System.gc();
            Thread.sleep(100); // Allow GC to complete

            // Clear scanner buffer if possible
            if (scanner != null) {
                // Note: Scanner doesn't have a direct close method for System.in
                // but we can clear any pending input
            }

            System.out.println("   ✅ Resources cleaned up successfully");

        } catch (Exception e) {
            System.err.println("   ❌ Resource cleanup error: " + e.getMessage());
        }
    }

    private static void runProductionMode() {
        try {
            System.out.println("Running in PRODUCTION MODE");
            System.out.println("Loading vehicle inventory from CSV...");

            // Display vehicle statistics with exception handling
            try {
                displayVehicleStatistics();
            } catch (Exception e) {
                System.err.println("⚠️ Error displaying vehicle statistics: " + e.getMessage());
                System.err.println("Continuing with system startup...");
            }

            System.out.println("\n=== SYSTEM READY ===");
            System.out.println("✅ Vehicle inventory loaded successfully");
            System.out.println("✅ All modules initialized");
            System.out.println("✅ System ready for operations");

            // Start the main menu system directly
            showMainMenu();

        } catch (Exception e) {
            System.err.println("❌ Error in production mode: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Production mode failed", e);
        }
    }

    private static void displayVehicleStatistics() {
        try {
            System.out.println("\n=== VEHICLE INVENTORY STATISTICS ===");
            List<Vehicle> allVehicles = vehicleManager.getAllVehicles();

            if (allVehicles == null) {
                System.out.println("❌ No vehicle data available");
                pauseForUserConfirmation();
                return;
            }

            // Count vehicles by type
            long mountainBikes = allVehicles.stream().filter(v -> v.getVehicleType().equals("Mountain Bike")).count();
            long dirtBikes = allVehicles.stream().filter(v -> v.getVehicleType().equals("Dirt Bike")).count();
            long buggies = allVehicles.stream().filter(v -> v.getVehicleType().equals("Buggy")).count();
            long crossovers = allVehicles.stream().filter(v -> v.getVehicleType().equals("Crossover")).count();
            long availableVehicles = allVehicles.stream().filter(Vehicle::isAvailable).count();

            System.out.println("Total Vehicles: " + allVehicles.size());
            System.out.println("🚵 Mountain Bikes: " + mountainBikes);
            System.out.println("🏍️  Dirt Bikes: " + dirtBikes);
            System.out.println("🚙 Buggies: " + buggies);
            System.out.println("🚗 Crossovers: " + crossovers);
            System.out.println("✅ Available for Rent: " + availableVehicles);
            System.out.println("❌ Currently Rented: " + (allVehicles.size() - availableVehicles));

            // Show price range
            double minPrice = allVehicles.stream().mapToDouble(Vehicle::getModelPricing).min().orElse(0);
            double maxPrice = allVehicles.stream().mapToDouble(Vehicle::getModelPricing).max().orElse(0);
            double avgPrice = allVehicles.stream().mapToDouble(Vehicle::getModelPricing).average().orElse(0);

            System.out.println("💰 Price Range: RM" + String.format("%.0f", minPrice) + " - RM" + String.format("%.0f", maxPrice));
            System.out.println("📊 Average Price: RM" + String.format("%.1f", avgPrice));

        } catch (Exception e) {
            System.err.println("❌ Error calculating vehicle statistics: " + e.getMessage());
            throw new RuntimeException("Vehicle statistics calculation failed", e);
        }
    }

    private static void showMainMenu() {
        while (true) {
            // Clear screen for clean menu display
            transitionToScreen();
            
            System.out.println("=========================================");
            System.out.println("   🏔️  HillClimmer VEHICLE RENTAL   🏔️");
            System.out.println("        Malaysia's Premier Hill");
            System.out.println("        Climbing Vehicle Service");
            System.out.println("=========================================");
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. 🚗 Customer Login");
            System.out.println("2. 👨‍💼 Vehicle Manager Login");
            System.out.println("3. 📝 New Customer Registration");
            System.out.println("4. ℹ️  About HillClimmer");
            System.out.println("5. ❌ Exit System");
            System.out.println("\n💡 Enter '0' at any input to return to this menu");
            
            try {
                int choice = readInt("Please select an option (1-5): ", 1, 5);

                switch (choice) {
                    case 1:
                        customerLogin();
                        break;
                    case 2:
                        managerLogin();
                        break;
                    case 3:
                            customerRegistration();
                            break;
                        case 4:
                            showAbout();
                            break;
                        case 5:
                            System.out.println("Thank you for using HillClimmer! Selamat tinggal!");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("❌ Invalid option. Please select 1-5.");
                    }
            } catch (UserExitException e) {
                System.out.println("🔙 Returned to main menu.");
                continue;
            }
        }
    }

    private static void customerLogin() {
        // Clear screen for login page
        transitionToScreen();
        
        System.out.println("=========================================");
        System.out.println("   🏔️  HillClimmer VEHICLE RENTAL   🏔️");
        System.out.println("        Malaysia's Premier Hill");
        System.out.println("        Climbing Vehicle Service");
        System.out.println("=========================================");
        System.out.println("\n=== CUSTOMER LOGIN ===");
        System.out.println("Please enter your credentials:");
        System.out.println("💡 You can login using either Customer ID (e.g., C001) or Email address");
        System.out.println("\n💡 Enter '0' at any input to return to main menu");

        try {
            String loginInput = readString("Customer ID or Email: ");
            String password = readPassword("Password: ");

            Customer customer = null;
            
            // Try to find customer by ID first
            if (loginInput.toUpperCase().matches("C\\d{3,}")) {
                customer = customerDAO.load(loginInput.toUpperCase());
            } else {
                // Try to find customer by email
                customer = customerDAO.findByEmail(loginInput);
            }

            if (customer != null && customer.authenticate(password)) {
                if (!customer.isLicenseValid()) {
                    System.out.println("❌ Your driving license has expired. Please renew before renting vehicles.");
                    System.out.println("License expiry date: " +
                        customer.getLicenseExpiryDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    return;
                }

                currentCustomer = customer;
                isManagerMode = false;
                System.out.println("✅ Login successful!");
                System.out.println("🎉 Welcome back, " + customer.getName() + "!");
                System.out.println("🏔️ Ready to explore Malaysia's hill climbing adventures?");
                showCustomerMenu();
            } else {
                System.out.println("❌ Invalid customer ID/email or password. Please try again.");
            }
        } catch (UserExitException e) {
            System.out.println("🔙 Returned to main menu.");
        }
    }

    private static void managerLogin() {
        // Clear screen for manager login page
        transitionToScreen();
        
        System.out.println("=========================================");
        System.out.println("   🏔️  HillClimmer VEHICLE RENTAL   🏔️");
        System.out.println("        Malaysia's Premier Hill");
        System.out.println("        Climbing Vehicle Service");
        System.out.println("=========================================");
        System.out.println("\n=== VEHICLE MANAGER LOGIN ===");
        System.out.println("Authorized personnel only");
        System.out.println("💡 Login using Manager ID (e.g., VM002)");
        System.out.println("\n💡 Enter '0' at any input to return to main menu");

        try {
            String managerId = readManagerId("Manager ID: ");
            String accessCode = readPassword("Access Code: ");

            // Authenticate using ManagerDAO and managers.csv
            Manager authenticatedManager = managerDAO.authenticate(managerId, accessCode);
            
            if (authenticatedManager != null) {
                currentManager = authenticatedManager;
                isManagerMode = true;
                
                // Initialize managers with authenticated manager
                vehicleManager = new VehicleManager(currentManager);
                rentalManager = new RentalManager(currentManager);
            
                System.out.println("✅ Manager login successful!");
                System.out.println("� Welcome back, " + currentManager.getName() + "!");
                System.out.println("🔐 Authorization Level: " + currentManager.getAuthorizationLevel());
                System.out.println("📊 Access to both Vehicle and Rental Management");
                System.out.println("🏢 Ready to manage HillClimmer operations?");
                
                showManagerMenu();
            } else {
                System.out.println("❌ Invalid manager credentials. Access denied.");
                System.out.println("💡 Available Manager IDs: VM002, VM003, VM004, VM005, VM006");
            }
        } catch (UserExitException e) {
            System.out.println("🔙 Returned to main menu.");
        }
    }

    private static void customerRegistration() {
        // Clear screen for registration page
        transitionToScreen();
        
        System.out.println("=========================================");
        System.out.println("   🏔️  HillClimmer VEHICLE RENTAL   🏔️");
        System.out.println("        Malaysia's Premier Hill");
        System.out.println("        Climbing Vehicle Service");
        System.out.println("=========================================");
        System.out.println("\n=== NEW CUSTOMER REGISTRATION ===");
        System.out.println("Please provide your information for registration:");
        System.out.println("\n💡 Enter '0' at any input to return to main menu");

        try {
            String name = readString("Full Name (as per IC): ");
            String icNumber = readIC("IC Number (XXXXXX-XX-XXXX): ");
            String phoneNo = readPhone("Phone Number (you can enter in formats: +60XXXXXXXXX, 0XXXXXXXXX, 0xx-xxx-xxxx, or 0xx xxx xxxx): ");
            String email = readEmail("Email Address: ");
            String licenseType = readLicenseType("License Type (B, B2, D, DA, E, E1, E2): ");
            LocalDate licenseExpiry = readDate("License Expiry Date (DD/MM/YYYY): ", false);
            String password = readPassword("Create Password (min 6 characters): ", 6);

            // Generate customer ID
            String customerId = "C" + String.format("%03d", customerDAO.getAll().size() + 1);

            // Calculate age from IC
            Customer tempCustomer = new Customer(customerId, name, icNumber, phoneNo, email,
                licenseType, licenseExpiry, 0, password);
            int age = tempCustomer.getAgeFromIC();

            if (age < 18) {
                System.out.println("❌ You must be at least 18 years old to register.");
                return;
            }

            Customer newCustomer = new Customer(customerId, name, icNumber, phoneNo, email,
                licenseType, licenseExpiry, age, password);

            customerDAO.save(newCustomer);

            System.out.println("✅ Registration successful!");
            System.out.println("🎉 Welcome to HillClimmer, " + name + "!");
            System.out.println("Your Customer ID is: " + customerId);
            System.out.println("Please remember this ID for login.");
            System.out.println("\n" + newCustomer.toString());

        } catch (UserExitException e) {
            System.out.println("🔙 Returned to main menu.");
        } catch (Exception e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
            System.out.println("Please try again.");
        }
    }

    private static void showCustomerMenu() {
        while (currentCustomer != null) {
            // Clear screen for customer menu
            transitionToScreen();
            
            System.out.println("=========================================");
            System.out.println("   🏔️  HillClimmer VEHICLE RENTAL   🏔️");
            System.out.println("        Malaysia's Premier Hill");
            System.out.println("        Climbing Vehicle Service");
            System.out.println("=========================================");
            System.out.println("\n=== CUSTOMER DASHBOARD ===");
            System.out.println("👋 Hello, " + currentCustomer.getName() + "! Welcome to your dashboard.");
            System.out.println("💰 Outstanding Balance: RM" + String.format("%.2f", currentCustomer.getOutstandingBalance()));
            System.out.println("\n1. 🚗 New Vehicle Rental");
            System.out.println("2. 📋 View My Rentals");
            System.out.println("3. 👤 My Profile");
            System.out.println("4. 🔒 Safety Check");
            System.out.println("5. 💳 Make Payment");
            System.out.println("6. 🚪 Logout");
            System.out.println("\n💡 Enter '0' at any input to return to this menu");
            
            try {
                int choice = readInt("Please select an option (1-6): ", 1, 6);

            switch (choice) {
                case 1:
                    newRental();
                    break;
                case 2:
                    viewRentals();
                    break;
                case 3:
                    viewProfile();
                    break;
                case 4:
                    safetyCheck();
                    break;
                case 5:
                    makePayment();
                    break;
                case 6:
                    System.out.println("👋 Thank you for using HillClimmer, " + currentCustomer.getName() + "!");
                    System.out.println("🏔️ We hope to see you again for your next adventure!");
                    currentCustomer = null;
                    return;
                default:
                    System.out.println("❌ Invalid option. Please select 1-6.");
            }
            } catch (UserExitException e) {
                System.out.println("🔙 Returned to customer menu.");
                continue;
            }
        }
    }

    private static void showManagerMenu() {
        while (isManagerMode) {
            // Clear screen for manager menu
            transitionToScreen();
            
            System.out.println("=========================================");
            System.out.println("   🏔️  HillClimmer VEHICLE RENTAL   🏔️");
            System.out.println("        Malaysia's Premier Hill");
            System.out.println("        Climbing Vehicle Service");
            System.out.println("=========================================");
            System.out.println("\n=== MANAGER ADMINISTRATION PANEL ===");
            System.out.println("� Welcome back, " + (currentManager != null ? currentManager.getName() : "Manager") + "!");
            System.out.println("🔐 Authorization Level: " + (currentManager != null ? currentManager.getAuthorizationLevel() : "N/A"));
            System.out.println("🏢 Ready to manage HillClimmer operations?");
            System.out.println("\n🚗 VEHICLE MANAGEMENT:");
            System.out.println("1. 📊 View All Vehicles");
            System.out.println("2. ➕ Add New Vehicle");
            System.out.println("3. 🗑️  Remove Vehicle");
            System.out.println("4. ✏️  Update Vehicle Details");
            System.out.println("\n📅 RENTAL MANAGEMENT:");
            System.out.println("5. 📋 View All Rentals");
            System.out.println("6. ➕ Add New Rental");
            System.out.println("7. �️  Remove Rental");
            System.out.println("\n�👥 CUSTOMER MANAGEMENT:");
            System.out.println("8. 👥 View All Customers");
            System.out.println("\n📈 SYSTEM REPORTS:");
            System.out.println("9. 📈 System Reports");
            System.out.println("10. 🚪 Logout");
            System.out.println("\n💡 Enter '0' at any input to return to this menu");
            
            try {
                int choice = readInt("Please select an option (1-10): ", 1, 10);

            switch (choice) {
                case 1:
                    viewAllVehicles();
                    break;
                case 2:
                    addNewVehicle();
                    break;
                case 3:
                    removeVehicle();
                    break;
                case 4:
                    updateVehicleDetails();
                    break;
                case 5:
                    viewAllRentals();
                    break;
                case 6:
                    addNewRental();
                    break;
                case 7:
                    removeRental();
                    break;
                case 8:
                    viewAllCustomers();
                    break;
                case 9:
                    showSystemReports();
                    break;
                case 10:
                    System.out.println("👋 Manager logout successful.");
                    System.out.println("👤 Goodbye, " + (currentManager != null ? currentManager.getName() : "Manager") + "!");
                    System.out.println("🏢 Thank you for managing HillClimmer operations.");
                    currentManager = null;
                    vehicleManager = null;
                    rentalManager = new RentalManager(); // Reset to unauthenticated
                    isManagerMode = false;
                    return;
                default:
                    System.out.println("❌ Invalid option. Please select 1-10.");
            }
            } catch (UserExitException e) {
                System.out.println("🔙 Returned to manager menu.");
                continue;
            }
        }
    }

    // Customer menu methods
    private static void newRental() {
        System.out.println("\n=== NEW VEHICLE RENTAL ===");

        // SAFETY CHECK VERIFICATION - Required before booking
        if (!currentCustomer.isSafetyCheckPassed()) {
            System.out.println("🛡️  SAFETY CHECK REQUIRED");
            System.out.println("=".repeat(50));
            System.out.println("❌ You must pass the safety check before booking any vehicle.");
            System.out.println("This is a mandatory requirement for all customers.");
            System.out.println("Safety check includes traffic rules, penalties, and vehicle usage.");
            System.out.println("=".repeat(50));
            System.out.println("Please complete the safety check from the main menu first.");
            pauseForUserConfirmation();
            return;
        }

        // Show available vehicles
        List<Vehicle> availableVehicles = vehicleManager.getAllVehicles().stream()
            .filter(Vehicle::isAvailable)
            .toList();

        if (availableVehicles.isEmpty()) {
            System.out.println("❌ No vehicles are currently available for rental.");
            pauseForUserConfirmation();
            return;
        }

        System.out.println("Available vehicles:");
        for (int i = 0; i < availableVehicles.size(); i++) {
            Vehicle v = availableVehicles.get(i);
            System.out.println((i + 1) + ". " + v.getVehicleID() + " - " + v.getVehicleModel() +
                " (RM" + v.getModelPricing() + "/day)");
        }

        System.out.print("Select vehicle (1-" + availableVehicles.size() + "): ");
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("❌ Please enter a valid selection.");
                pauseForUserConfirmation();
                return;
            }
            int vehicleChoice = Integer.parseInt(input) - 1;

            if (vehicleChoice < 0 || vehicleChoice >= availableVehicles.size()) {
                System.out.println("❌ Invalid vehicle selection.");
                pauseForUserConfirmation();
                return;
            }

            Vehicle selectedVehicle = availableVehicles.get(vehicleChoice);

            System.out.print("Rental start date (DD/MM/YYYY): ");
            String startDateStr = scanner.nextLine().trim();
            LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("Rental end date (DD/MM/YYYY): ");
            String endDateStr = scanner.nextLine().trim();
            LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (startDate.isAfter(endDate) || startDate.isBefore(LocalDate.now())) {
                System.out.println("❌ Invalid date range.");
                pauseForUserConfirmation();
                return;
            }

            // Calculate rental cost
            long days = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
            double totalCost = days * selectedVehicle.getModelPricing();

            System.out.println("\n=== RENTAL CONFIRMATION ===");
            System.out.println("Vehicle: " + selectedVehicle.getVehicleModel());
            System.out.println("Duration: " + days + " days");
            System.out.println("Total Cost: RM" + String.format("%.2f", totalCost));
            System.out.print("Confirm rental? (Y/N): ");

            String confirm = scanner.nextLine().trim().toUpperCase();
            if (!"Y".equals(confirm)) {
                System.out.println("Rental cancelled.");
                return;
            }

            // Create rental
            int rentalId = rentalManager.getAllRentals().size() + 1;
            rentalManager.addRental(Integer.parseInt(currentCustomer.getCustomerID().substring(1)),
                Integer.parseInt(selectedVehicle.getVehicleID().substring(2)), startDate, endDate, totalCost);

            // Create rental period with timer
            durationManager.createBasicRentalPeriod(rentalId, startDate, endDate,
                selectedVehicle.getModelPricing());

            // Create return reminder
            durationManager.createReturnReminder(rentalId, endDate.atStartOfDay());

            System.out.println("✅ Rental created successfully!");
            System.out.println("Rental ID: R" + rentalId);
            System.out.println("Please make payment to confirm the rental.");

        } catch (Exception e) {
            System.out.println("❌ Error creating rental: " + e.getMessage());
            pauseForUserConfirmation();
        }
    }

    private static void viewRentals() {
        System.out.println("\n=== MY RENTALS ===");

        List<Rental> customerRentals = rentalManager.getAllRentals().stream()
            .filter(r -> r.getCustomerId() == Integer.parseInt(currentCustomer.getCustomerID().substring(1)))
            .toList();

        if (customerRentals.isEmpty()) {
            System.out.println("You have no rental history.");
            return;
        }

        for (Rental rental : customerRentals) {
            System.out.println("Rental ID: R" + rental.getRentalId());
            System.out.println("Vehicle ID: V" + rental.getVehicleId());
            System.out.println("Period: " + rental.getStartDate() + " to " + rental.getEndDate());
            System.out.println("Total Cost: RM" + String.format("%.2f", rental.getTotalCost()));
            System.out.println("---");
        }
    }

    private static void viewProfile() {
        System.out.println("\n" + currentCustomer.toString());

        System.out.println("\n=== PROFILE OPTIONS ===");
        System.out.println("1. ✏️  Update Phone Number");
        System.out.println("2. ✏️  Update Email");
        System.out.println("3. 🔑 Change Password");
        System.out.println("4. 📄 View Payment History");
        System.out.println("5. 🔙 Back to Main Menu");
        System.out.print("Select option (1-5): ");

        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("❌ Please enter a valid option.");
                return;
            }
            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    System.out.print("New phone number: ");
                    String newPhone = scanner.nextLine().trim();
                    currentCustomer.setPhoneNo(newPhone);
                    customerDAO.update(currentCustomer);
                    System.out.println("✅ Phone number updated successfully!");
                    break;
                case 2:
                    System.out.print("New email: ");
                    String newEmail = scanner.nextLine().trim();
                    currentCustomer.setEmail(newEmail);
                    customerDAO.update(currentCustomer);
                    System.out.println("✅ Email updated successfully!");
                    break;
                case 3:
                    String currentPass = readPassword("Current password: ");
                    if (currentCustomer.authenticate(currentPass)) {
                        String newPass = readPassword("New password: ");
                        currentCustomer.updatePassword(newPass);
                        customerDAO.update(currentCustomer);
                        System.out.println("✅ Password changed successfully!");
                    } else {
                        System.out.println("❌ Current password is incorrect.");
                    }
                    break;
                case 4:
                    viewPaymentHistory();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("❌ Invalid option.");
            }
        } catch (Exception e) {
            System.out.println("❌ Error updating profile: " + e.getMessage());
        }
    }

    private static void viewPaymentHistory() {
        System.out.println("\n=== PAYMENT HISTORY ===");
        System.out.println("Customer ID: " + currentCustomer.getCustomerID());

        List<Payment> customerPayments = transactionManager.getTransactionList().stream()
            .filter(p -> p.getCustomerID().equals(currentCustomer.getCustomerID()))
            .toList();

        if (customerPayments.isEmpty()) {
            System.out.println("No payment history found.");
            return;
        }

        System.out.println("\nPayment Records:");
        System.out.println("=".repeat(80));

        for (Payment payment : customerPayments) {
            System.out.println("Payment ID: " + payment.getPaymentID());
            System.out.println("Reference: " + payment.getReferenceNumber());
            System.out.println("Amount: RM" + String.format("%.2f", payment.getTotalAmount()));
            System.out.println("Method: " + payment.getPaymentMethod());
            System.out.println("Status: " + payment.getPaymentStatus());
            System.out.println("Date: " + payment.getTimestamp());

            if (!payment.getPaymentSlip().isEmpty()) {
                System.out.println("Payment Slip:");
                System.out.println("-".repeat(40));
                System.out.println(payment.getPaymentSlip());
                System.out.println("-".repeat(40));
            }

            System.out.println("=".repeat(80));
        }
    }

    private static void safetyCheck() {
        System.out.println("\n=== COMPREHENSIVE SAFETY ASSESSMENT ===");

        // Check if customer has already passed safety check
        if (currentCustomer.isSafetyCheckPassed()) {
            System.out.println("✅ You have already passed the safety check!");
            System.out.println("Safety Check ID: " + currentCustomer.getSafetyCheckID());
            System.out.println("Completed: " + currentCustomer.getSafetyCheckDate());
            System.out.println("You can proceed with vehicle bookings.");
            return;
        }

        // Conduct new safety check
        SafetyCheck safetyCheck = new SafetyCheck(currentCustomer.getCustomerID());
        boolean passed = safetyCheck.conductSafetyCheck();

        if (passed) {
            // Update customer profile with safety check results
            currentCustomer.setSafetyCheckPassed(true);
            currentCustomer.setSafetyCheckID(safetyCheck.getCheckID());
            currentCustomer.setSafetyCheckDate(safetyCheck.getCompletedDate());

            // Save updated customer data
            customerDAO.save(currentCustomer);

            System.out.println("\n🎉 Safety check results saved to your profile!");
            System.out.println("You can now book vehicles without retaking the assessment.");
        } else {
            System.out.println("\n❌ Safety check failed. You must pass the assessment before booking vehicles.");
            System.out.println("You can retake the safety check anytime from the main menu.");
        }
    }

    private static void makePayment() {
        System.out.println("\n=== PAYMENT SYSTEM ===");
        System.out.println("Outstanding Balance: RM" + String.format("%.2f", currentCustomer.getOutstandingBalance()));

        if (currentCustomer.getOutstandingBalance() == 0) {
            System.out.println("✅ No outstanding payments.");
            return;
        }

        System.out.println("Payment Methods:");
        System.out.println("1. 💳 Credit/Debit Card");
        System.out.println("2. 🏦 Online Banking");
        System.out.println("3. 💵 Cash (Payment Slip)");
        System.out.print("Select payment method (1-3): ");

        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("❌ Please enter a valid selection.");
                return;
            }
            int method = Integer.parseInt(input);

            if (method < 1 || method > 3) {
                System.out.println("❌ Invalid payment method.");
                return;
            }

            System.out.print("Enter payment amount (RM): ");
            String amountInput = scanner.nextLine().trim();
            if (amountInput.isEmpty()) {
                System.out.println("❌ Please enter a valid amount.");
                return;
            }
            double amount = Double.parseDouble(amountInput);

            if (amount <= 0 || amount > currentCustomer.getOutstandingBalance()) {
                System.out.println("❌ Invalid payment amount.");
                return;
            }

            // Create payment based on method
            String paymentID = "P" + System.currentTimeMillis();
            String timestamp = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Payment payment = null;

            switch (method) {
                case 1: // Credit Card
                    payment = new CreditCardPayment(paymentID, amount, timestamp, currentCustomer.getCustomerID());
                    break;
                case 2: // Online Banking
                    payment = new OnlineBankingPayment(paymentID, amount, timestamp, currentCustomer.getCustomerID());
                    break;
                case 3: // Cash
                    payment = new CashPayment(paymentID, amount, timestamp, currentCustomer.getCustomerID());
                    break;
            }

            // Process the payment
            payment.processPayment();

            // Record transaction if payment was successful
            if ("Paid".equals(payment.getPaymentStatus())) {
                transactionManager.recordTransaction(payment);
                currentCustomer.setOutstandingBalance(currentCustomer.getOutstandingBalance() - amount);
                customerDAO.update(currentCustomer);
                System.out.println("✅ Payment of RM" + String.format("%.2f", amount) + " processed successfully!");
                System.out.println("Remaining balance: RM" + String.format("%.2f", currentCustomer.getOutstandingBalance()));
            } else if ("Prebooked - Awaiting Cash Payment".equals(payment.getPaymentStatus())) {
                // For cash payments, record as pending
                transactionManager.recordTransaction(payment);
                System.out.println("📄 Payment slip generated. Please complete payment at the counter.");
                System.out.println("Reference: " + payment.getReferenceNumber());
            } else {
                System.out.println("❌ Payment was not completed.");
            }

        } catch (Exception e) {
            System.out.println("❌ Payment failed: " + e.getMessage());
        }
    }

    // Manager menu methods
    private static void viewAllVehicles() {
        System.out.println("\n=== ALL VEHICLES ===");
        List<Vehicle> vehicles = vehicleManager.getAllVehicles();

        for (Vehicle v : vehicles) {
            System.out.println("ID: " + v.getVehicleID() + " | Model: " + v.getVehicleModel() +
                " | Price: RM" + v.getModelPricing() + " | Condition: " + v.getVehicleCon() +
                " | Available: " + (v.isAvailable() ? "Yes" : "No"));
        }
    }

    private static void addNewVehicle() {
        System.out.println("\n=== ADD NEW VEHICLE ===");
        System.out.println("Vehicle Types: 1.Mountain Bike, 2.Dirt Bike, 3.Buggy, 4.Crossover");

        System.out.print("Select vehicle type (1-4): ");
        try {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("❌ Please enter a valid selection.");
                return;
            }
            int type = Integer.parseInt(input);

            System.out.print("Vehicle ID: ");
            String vehicleId = scanner.nextLine().trim();

            System.out.print("Model: ");
            String model = scanner.nextLine().trim();

            System.out.print("Daily Price (RM): ");
            String priceInput = scanner.nextLine().trim();
            if (priceInput.isEmpty()) {
                System.out.println("❌ Please enter a valid price.");
                return;
            }
            double price = Double.parseDouble(priceInput);

            System.out.print("Condition: ");
            String condition = scanner.nextLine().trim();

            Vehicle newVehicle;
            switch (type) {
                case 1:
                    newVehicle = new MountainBike(vehicleId, model, price, condition, true);
                    break;
                case 2:
                    newVehicle = new DirtBike(vehicleId, model, price, condition, true);
                    break;
                case 3:
                    newVehicle = new Buggy(vehicleId, model, price, condition, true);
                    break;
                case 4:
                    newVehicle = new Crossover(vehicleId, model, price, condition, true);
                    break;
                default:
                    System.out.println("❌ Invalid vehicle type.");
                    return;
            }

            vehicleManager.addVehicle(newVehicle);
            System.out.println("✅ Vehicle added successfully!");

        } catch (Exception e) {
            System.out.println("❌ Error adding vehicle: " + e.getMessage());
        }
    }

    private static void removeVehicle() {
        System.out.print("Enter vehicle ID to remove: ");
        String vehicleId = scanner.nextLine().trim();
        vehicleManager.removeVehicle(vehicleId);
    }

    private static void updateVehicleDetails() {
        System.out.print("Enter vehicle ID to update: ");
        String vehicleId = scanner.nextLine().trim();

        System.out.print("New condition: ");
        String newCondition = scanner.nextLine().trim();

        vehicleManager.setVehicleDetails(vehicleId, newCondition);
    }

    private static void viewAllCustomers() {
        System.out.println("\n=== ALL CUSTOMERS ===");
        List<Customer> customers = customerDAO.getAll();

        for (Customer c : customers) {
            System.out.println("ID: " + c.getCustomerID() + " | Name: " + c.getName() +
                " | Phone: " + c.getPhoneNo() + " | Balance: RM" + String.format("%.2f", c.getOutstandingBalance()));
        }
    }

    private static void showSystemReports() {
        System.out.println("\n=== SYSTEM REPORTS ===");
        System.out.println("Total Vehicles: " + vehicleManager.getAllVehicles().size());
        System.out.println("Total Customers: " + customerDAO.getAll().size());
        System.out.println("Active Rentals: " + rentalManager.getAllRentals().size());
        System.out.println("Pending Reminders: " + durationManager.getPendingReminders().size());
        System.out.println("Total Revenue: RM" + durationManager.getTotalRevenueFromActivePeriods());
    }

    // Rental Management Methods
    private static void viewAllRentals() {
        System.out.println("\n=== ALL RENTALS ===");
        List<Rental> rentals = rentalManager.getAllRentals();
        
        if (rentals.isEmpty()) {
            System.out.println("📭 No rentals found.");
            return;
        }
        
        System.out.println("📋 Current Rentals:");
        System.out.println("=".repeat(80));
        for (Rental rental : rentals) {
            System.out.printf("ID: %d | Customer: %d | Vehicle: %d | Period: %s to %s | Cost: RM%.2f%n",
                rental.getRentalId(), rental.getCustomerId(), rental.getVehicleId(),
                rental.getStartDate(), rental.getEndDate(), rental.getTotalCost());
        }
        System.out.println("=".repeat(80));
        System.out.println("Total Rentals: " + rentals.size());
    }

    private static void addNewRental() {
        System.out.println("\n=== ADD NEW RENTAL ===");
        System.out.println("\n💡 Enter '0' at any input to cancel and return to manager menu");
        
        try {
            int customerId = readInt("Customer ID: ", 1, Integer.MAX_VALUE);
            int vehicleId = readInt("Vehicle ID: ", 1, Integer.MAX_VALUE);
            LocalDate startDate = readDate("Start Date (DD/MM/YYYY): ", true);
            LocalDate endDate = readDate("End Date (DD/MM/YYYY): ", false);
            double dailyRate = readDouble("Daily Rate (RM): ", 0, Double.MAX_VALUE);
            
            double totalCost = rentalManager.calculateTotalCost(startDate, endDate, dailyRate);
            System.out.printf("Calculated Total Cost: RM%.2f%n", totalCost);
            
            String confirm = readString("Confirm rental creation? (y/n): ");
            if (confirm.toLowerCase().startsWith("y")) {
                rentalManager.addRental(customerId, vehicleId, startDate, endDate, totalCost);
            } else {
                System.out.println("❌ Rental creation cancelled.");
            }
        } catch (UserExitException e) {
            System.out.println("🔙 Rental creation cancelled. Returned to manager menu.");
        }
    }

    private static void removeRental() {
        System.out.println("\n=== REMOVE RENTAL ===");
        System.out.println("\n💡 Enter '0' at any input to cancel and return to manager menu");
        
        try {
            int rentalId = readInt("Rental ID to remove: ", 1, Integer.MAX_VALUE);
            
            String confirm = readString("Are you sure you want to remove rental " + rentalId + "? (y/n): ");
            if (confirm.toLowerCase().startsWith("y")) {
                rentalManager.deleteRental(rentalId);
            } else {
                System.out.println("❌ Rental removal cancelled.");
            }
        } catch (UserExitException e) {
            System.out.println("🔙 Rental removal cancelled. Returned to manager menu.");
        }
    }

    private static void showAbout() {
        // Clear screen for about page
        transitionToScreen();
        
        System.out.println("=========================================");
        System.out.println("   🏔️  HillClimmer VEHICLE RENTAL   🏔️");
        System.out.println("        Malaysia's Premier Hill");
        System.out.println("        Climbing Vehicle Service");
        System.out.println("=========================================");
        System.out.println("\n=== ABOUT HillClimmer ===");
        System.out.println("🏔️ HillClimmer Vehicle Rental");
        System.out.println("Malaysia Premier Hill Climbing Vehicle Service");
        System.out.println("");
        System.out.println("We provide high-quality vehicles for hill climbing");
        System.out.println("and outdoor adventures across Malaysia.");
        System.out.println("");
        System.out.println("Our Services:");
        System.out.println("• Mountain Bike Rentals");
        System.out.println("• Dirt Bike Rentals");
        System.out.println("• All-Terrain Buggy Rentals");
        System.out.println("• 4x4 Crossover Rentals");
        System.out.println("");
        System.out.println("Safety First: All rentals include safety briefings");
        System.out.println("and equipment checks.");
        System.out.println("");
        System.out.println("Contact: +603-1234-5678 | info@hillclimmer.my");
        System.out.println("");
        System.out.print("Press Enter to return to main menu...");
        scanner.nextLine();
    }

    /**
     * Utility method to pause execution and wait for user confirmation
     * Allows users to read error messages before proceeding
     */
    private static void pauseForUserConfirmation() {
        System.out.println("\n" + "=".repeat(50));
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // Method to reset scanner for testing
    public static void resetScannerForTesting() {
        try {
            java.lang.reflect.Field scannerField = HillClimmer.class.getDeclaredField("scanner");
            scannerField.setAccessible(true);
            scannerField.set(null, new Scanner(System.in));
        } catch (Exception e) {
            // Ignore for production
        }
    }
}
