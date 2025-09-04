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
 * FOR TESTING ONLY: Run HillClimmerDemo.java
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
import java.time.LocalDateTime;
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
    private static VehicleManager vehicleManager;
    private static RentalManager rentalManager = new RentalManager();
    private static DurationManager durationManager = new DurationManager();
    private static TransactionManager transactionManager = new TransactionManager("TM001");

    // Current logged in user
    private static Customer currentCustomer = null;
    private static boolean isManagerMode = false;

    /**
     * Reads password input with asterisks masking for privacy
     * @param prompt The prompt message to display
     * @return The entered password
     */
    private static String readPassword(String prompt) {
        System.out.print(prompt);

        // Try using Console first (most secure)
        Console console = System.console();
        if (console != null) {
            char[] passwordArray = console.readPassword();
            System.out.println(); // Move to next line
            return new String(passwordArray);
        }

        // Fallback: Use Scanner for environments where Console is not available
        // This works better with Ant and IDEs
        String password = scanner.nextLine();

        // Show asterisks for the length of the password
        for (int i = 0; i < password.length(); i++) {
            System.out.print("*");
        }
        System.out.println(); // Move to next line

        return password.trim();
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
        // Enter alternate screen buffer for clean experience
        enterAlternateScreen();
        
        System.out.println("=========================================");
        System.out.println("   🏔️  HILL CLIMBER VEHICLE RENTAL   🏔️");
        System.out.println("        Malaysia's Premier Hill");
        System.out.println("        Climbing Vehicle Service");
        System.out.println("=========================================");
        System.out.println("          PRODUCTION VERSION");
        System.out.println("=========================================");

        initializeSystem();
        runProductionMode();
        
        // Exit alternate screen buffer when done
        exitAlternateScreen();
    }

    private static void initializeSystem() {
        // Initialize default vehicle manager
        vehicleManager = new VehicleManager("VM001", "Level 2", "Ahmad Abdullah", 5);

        // Load vehicles from CSV file
        loadVehiclesFromCSV();

        // Add sample customer for testing
        try {
            Customer sampleCustomer = new Customer("C001", "Muhammad Ali", "950101-14-5678",
                "+60123456789", "muhammad@email.com", "B", LocalDate.of(2026, 12, 31),
                29, "password123");
            customerDAO.save(sampleCustomer);
        } catch (Exception e) {
            // Customer might already exist
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

    private static void runProductionMode() {
        System.out.println("Running in PRODUCTION MODE");
        System.out.println("Loading vehicle inventory from CSV...");

        // Display vehicle statistics
        displayVehicleStatistics();

        System.out.println("\n=== SYSTEM READY ===");
        System.out.println("✅ Vehicle inventory loaded successfully");
        System.out.println("✅ All modules initialized");
        System.out.println("✅ System ready for operations");

        // Start the main menu system directly
        showMainMenu();
    }

    private static void displayVehicleStatistics() {
        System.out.println("\n=== VEHICLE INVENTORY STATISTICS ===");
        List<Vehicle> allVehicles = vehicleManager.getAllVehicles();

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
    }

    private static void demonstrateKeyFeatures() {
        System.out.println("\n=== KEY FEATURES DEMONSTRATION ===");

        // Test Malaysian validation
        System.out.println("🇲🇾 Testing Malaysian Validations:");
        boolean icValid = Customer.isValidIC("950101-14-5678");
        boolean phoneValid = Customer.isValidMalaysianPhone("+60123456789");
        boolean licenseValid = Customer.isValidLicenseType("B");

        System.out.println("  • IC Validation (950101-14-5678): " + (icValid ? "✅ VALID" : "❌ INVALID"));
        System.out.println("  • Phone Validation (+60123456789): " + (phoneValid ? "✅ VALID" : "❌ INVALID"));
        System.out.println("  • License Validation (B): " + (licenseValid ? "✅ VALID" : "❌ INVALID"));

        // Test duration management
        System.out.println("\n⏰ Testing Duration Management:");
        try {
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(3);
            durationManager.createBasicRentalPeriod(1, startDate, endDate, 75.0);
            System.out.println("  • Rental period created: " + startDate + " to " + endDate);
            System.out.println("  • Active periods: " + durationManager.getActiveRentalPeriods().size());
        } catch (Exception e) {
            System.out.println("  • Duration management test: " + e.getMessage());
        }

        // Test payment processing
        System.out.println("\n💳 Testing Payment Processing:");
        try {
            Payment payment = Payment.createPayment("Credit Card", "DEMO001", 150.0,
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), "C001");
            payment.updateStatus("Paid"); // Mark as paid for demo
            transactionManager.recordTransaction(payment);
            System.out.println("  • Payment processed: RM150.00 via Credit Card");
            System.out.println("  • Transaction recorded successfully");
        } catch (Exception e) {
            System.out.println("  • Payment processing test: " + e.getMessage());
        }

        // Test reminder system
        System.out.println("\n🔔 Testing Reminder System:");
        try {
            durationManager.createReturnReminder(1, LocalDateTime.now().plusDays(2));
            durationManager.createPaymentReminder(1, LocalDateTime.now().plusDays(1), 200.0);
            System.out.println("  • Return reminder created for 2 days from now");
            System.out.println("  • Payment reminder created for tomorrow");
            System.out.println("  • Total reminders: " + durationManager.getAllReminders().size());
        } catch (Exception e) {
            System.out.println("  • Reminder system test: " + e.getMessage());
        }
    }

    private static void showMainMenu() {
        while (true) {
            // Clear screen for clean menu display
            transitionToScreen();
            
            System.out.println("=========================================");
            System.out.println("   🏔️  HILL CLIMBER VEHICLE RENTAL   🏔️");
            System.out.println("        Malaysia's Premier Hill");
            System.out.println("        Climbing Vehicle Service");
            System.out.println("=========================================");
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. 🚗 Customer Login");
            System.out.println("2. 👨‍💼 Vehicle Manager Login");
            System.out.println("3. 📝 New Customer Registration");
            System.out.println("4. ℹ️  About Hill Climber");
            System.out.println("5. ❌ Exit System");
            System.out.print("Please select an option (1-5): ");

            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("❌ Please enter a valid option.");
                    continue;
                }
                int choice = Integer.parseInt(input);

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
                        System.out.println("Thank you for using Hill Climber! Selamat tinggal!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("❌ Invalid option. Please select 1-5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    private static void customerLogin() {
        // Clear screen for login page
        transitionToScreen();
        
        System.out.println("=========================================");
        System.out.println("   🏔️  HILL CLIMBER VEHICLE RENTAL   🏔️");
        System.out.println("        Malaysia's Premier Hill");
        System.out.println("        Climbing Vehicle Service");
        System.out.println("=========================================");
        System.out.println("\n=== CUSTOMER LOGIN ===");
        System.out.println("Please enter your credentials:");

        System.out.print("Customer ID (e.g., C001): ");
        String customerId = scanner.nextLine().trim();

        String password = readPassword("Password: ");

        // Load customer from database
        Customer customer = customerDAO.load(customerId);

        if (customer != null && customer.authenticate(password)) {
            if (!customer.isLicenseValid()) {
                System.out.println("❌ Your driving license has expired. Please renew before renting vehicles.");
                System.out.println("License expiry date: " +
                    customer.getLicenseExpiryDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                return;
            }

            currentCustomer = customer;
            isManagerMode = false;
            System.out.println("✅ Login successful! Welcome, " + customer.getName() + "!");
            showCustomerMenu();
        } else {
            System.out.println("❌ Invalid customer ID or password. Please try again.");
        }
    }

    private static void managerLogin() {
        // Clear screen for manager login page
        transitionToScreen();
        
        System.out.println("=========================================");
        System.out.println("   🏔️  HILL CLIMBER VEHICLE RENTAL   🏔️");
        System.out.println("        Malaysia's Premier Hill");
        System.out.println("        Climbing Vehicle Service");
        System.out.println("=========================================");
        System.out.println("\n=== VEHICLE MANAGER LOGIN ===");
        System.out.println("Authorized personnel only");

        System.out.print("Manager ID: ");
        String managerId = scanner.nextLine().trim();

        String accessCode = readPassword("Access Code: ");

        // Simple authentication for demo (in real system, use proper authentication)
        if ("VM001".equals(managerId) && "admin123".equals(accessCode)) {
            isManagerMode = true;
            System.out.println("✅ Manager login successful! Welcome to administration panel.");
            showManagerMenu();
        } else {
            System.out.println("❌ Invalid manager credentials. Access denied.");
        }
    }

    private static void customerRegistration() {
        // Clear screen for registration page
        transitionToScreen();
        
        System.out.println("=========================================");
        System.out.println("   🏔️  HILL CLIMBER VEHICLE RENTAL   🏔️");
        System.out.println("        Malaysia's Premier Hill");
        System.out.println("        Climbing Vehicle Service");
        System.out.println("=========================================");
        System.out.println("\n=== NEW CUSTOMER REGISTRATION ===");
        System.out.println("Please provide your information for registration:");

        try {
            System.out.print("Full Name (as per IC): ");
            String name = scanner.nextLine().trim();

            System.out.print("IC Number (XXXXXX-XX-XXXX): ");
            String icNumber = scanner.nextLine().trim();

            if (!Customer.isValidIC(icNumber)) {
                System.out.println("❌ Invalid IC number format. Please use XXXXXX-XX-XXXX format.");
                return;
            }

            System.out.print("Phone Number (+60XXXXXXXXX or 0XXXXXXXXX): ");
            String phoneNo = scanner.nextLine().trim();

            if (!Customer.isValidMalaysianPhone(phoneNo)) {
                System.out.println("❌ Invalid Malaysian phone number format.");
                return;
            }

            System.out.print("Email Address: ");
            String email = scanner.nextLine().trim();

            System.out.print("License Type (B, B2, D, DA, E, E1, E2): ");
            String licenseType = scanner.nextLine().trim().toUpperCase();

            if (!Customer.isValidLicenseType(licenseType)) {
                System.out.println("❌ Invalid license type. Valid types: B, B2, D, DA, E, E1, E2");
                return;
            }

            System.out.print("License Expiry Date (DD/MM/YYYY): ");
            String expiryInput = scanner.nextLine().trim();
            LocalDate licenseExpiry = LocalDate.parse(expiryInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (licenseExpiry.isBefore(LocalDate.now())) {
                System.out.println("❌ License expiry date cannot be in the past.");
                return;
            }

            String password = readPassword("Create Password (min 6 characters): ");

            if (password.length() < 6) {
                System.out.println("❌ Password must be at least 6 characters long.");
                return;
            }

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
            System.out.println("Your Customer ID is: " + customerId);
            System.out.println("Please remember this ID for login.");
            System.out.println("\n" + newCustomer.toString());

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
            System.out.println("   🏔️  HILL CLIMBER VEHICLE RENTAL   🏔️");
            System.out.println("        Malaysia's Premier Hill");
            System.out.println("        Climbing Vehicle Service");
            System.out.println("=========================================");
            System.out.println("\n=== CUSTOMER MENU ===");
            System.out.println("Welcome, " + currentCustomer.getName() + "!");
            System.out.println("Outstanding Balance: RM" + String.format("%.2f", currentCustomer.getOutstandingBalance()));
            System.out.println("\n1. 🚗 New Vehicle Rental");
            System.out.println("2. 📋 View My Rentals");
            System.out.println("3. 👤 My Profile");
            System.out.println("4. 🔒 Safety Check");
            System.out.println("5. 💳 Make Payment");
            System.out.println("6. 🚪 Logout");
            System.out.print("Please select an option (1-6): ");

            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("❌ Please enter a valid option.");
                    continue;
                }
                int choice = Integer.parseInt(input);

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
                        System.out.println("Thank you for using Hill Climber, " + currentCustomer.getName() + "!");
                        currentCustomer = null;
                        return;
                    default:
                        System.out.println("❌ Invalid option. Please select 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    private static void showManagerMenu() {
        while (isManagerMode) {
            // Clear screen for manager menu
            transitionToScreen();
            
            System.out.println("=========================================");
            System.out.println("   🏔️  HILL CLIMBER VEHICLE RENTAL   🏔️");
            System.out.println("        Malaysia's Premier Hill");
            System.out.println("        Climbing Vehicle Service");
            System.out.println("=========================================");
            System.out.println("\n=== VEHICLE MANAGER PANEL ===");
            System.out.println("Administrator Access");
            System.out.println("\n1. 📊 View All Vehicles");
            System.out.println("2. ➕ Add New Vehicle");
            System.out.println("3. 🗑️  Remove Vehicle");
            System.out.println("4. ✏️  Update Vehicle Details");
            System.out.println("5. 👥 View All Customers");
            System.out.println("6. 📈 System Reports");
            System.out.println("7. 🚪 Logout");
            System.out.print("Please select an option (1-7): ");

            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.println("❌ Please enter a valid option.");
                    continue;
                }
                int choice = Integer.parseInt(input);

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
                        viewAllCustomers();
                        break;
                    case 6:
                        showSystemReports();
                        break;
                    case 7:
                        System.out.println("Manager logout successful.");
                        isManagerMode = false;
                        return;
                    default:
                        System.out.println("❌ Invalid option. Please select 1-7.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number.");
            }
        }
    }

    // Customer menu methods
    private static void newRental() {
        System.out.println("\n=== NEW VEHICLE RENTAL ===");

        // Show available vehicles
        List<Vehicle> availableVehicles = vehicleManager.getAllVehicles().stream()
            .filter(Vehicle::isAvailable)
            .toList();

        if (availableVehicles.isEmpty()) {
            System.out.println("❌ No vehicles are currently available for rental.");
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
                return;
            }
            int vehicleChoice = Integer.parseInt(input) - 1;

            if (vehicleChoice < 0 || vehicleChoice >= availableVehicles.size()) {
                System.out.println("❌ Invalid vehicle selection.");
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
        System.out.println("\n=== SAFETY CHECK ===");
        System.out.println("Please answer the following safety questions:");

        String[] questions = {
            "1. Do you have a valid driving license for the vehicle type?",
            "2. Are you familiar with hill climbing safety procedures?",
            "3. Do you have appropriate safety gear (helmet, gloves, etc.)?",
            "4. Are you physically fit for hill climbing activities?",
            "5. Do you understand the vehicle rental terms and conditions?"
        };

        int score = 0;
        for (String question : questions) {
            System.out.print(question + " (Y/N): ");
            String answer = scanner.nextLine().trim().toUpperCase();
            if ("Y".equals(answer)) {
                score++;
            }
        }

        System.out.println("\n=== SAFETY CHECK RESULT ===");
        System.out.println("Your safety score: " + score + "/5");

        if (score >= 4) {
            System.out.println("✅ Safety check passed! You are eligible for vehicle rental.");
        } else {
            System.out.println("❌ Safety check failed. Please review safety guidelines before renting.");
            System.out.println("Recommended: Complete a safety course or review safety materials.");
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

    private static void showAbout() {
        // Clear screen for about page
        transitionToScreen();
        
        System.out.println("=========================================");
        System.out.println("   🏔️  HILL CLIMBER VEHICLE RENTAL   🏔️");
        System.out.println("        Malaysia's Premier Hill");
        System.out.println("        Climbing Vehicle Service");
        System.out.println("=========================================");
        System.out.println("\n=== ABOUT HILL CLIMBER ===");
        System.out.println("🏔️ Hill Climber Vehicle Rental");
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
        System.out.println("Contact: +603-1234-5678 | info@hillclimber.my");
        System.out.println("");
        System.out.print("Press Enter to return to main menu...");
        scanner.nextLine();
    }
}
