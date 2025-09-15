/*
 * OnlineBankingPayment class for handling online banking payments
 */
package hillclimmer.PaymentModule;

import java.util.Scanner;

/**
 * OnlineBankingPayment class for processing online banking payments
 * Simulates real online banking payment processing
 *
 * @author las
 */
public class OnlineBankingPayment extends Payment {
    private static final Scanner scanner = new Scanner(System.in);
    private String bankName;
    @SuppressWarnings("unused")
    private String accountNumber;
    @SuppressWarnings("unused")
    private String username;
    @SuppressWarnings("unused")
    private String password;
    private String otpCode;
    private String generatedOTP;

    public OnlineBankingPayment(String paymentID, double totalAmount, String timestamp, String customerID) {
        super(paymentID, totalAmount, "Online Banking", "Pending", timestamp, customerID);
        this.bankName = "";
        this.accountNumber = "";
        this.username = "";
        this.password = "";
        this.otpCode = "";
        this.generatedOTP = "";
    }

    @Override
    public void processPayment() {
        System.out.println("\n=== ONLINE BANKING PAYMENT ===");
        System.out.println("Amount to pay: RM" + String.format("%.2f", totalAmount));
        System.out.println("Reference Number: " + referenceNumber);
        System.out.println("💡 Enter '0' at any input to cancel payment");

        // Select bank
        System.out.println("Available Banks:");
        System.out.println("1. Maybank2u");
        System.out.println("2. CIMB Clicks");
        System.out.println("3. Public Bank");
        System.out.println("4. Hong Leong Bank");
        System.out.println("5. RHB Bank");
        System.out.print("Select your bank (1-5) or 0 to cancel: ");

        try {
            String bankInput = getUserInput();
            if (bankInput.equals("0")) {
                System.out.println("🔙 Payment cancelled.");
                this.paymentStatus = "Cancelled";
                return;
            }

            int bankChoice = Integer.parseInt(bankInput);
            if (bankChoice < 1 || bankChoice > 5) {
                System.out.println("❌ Invalid bank selection.");
                this.paymentStatus = "Failed";
                return;
            }

            this.bankName = getBankName(bankChoice);

            // Collect banking details
            System.out.print("Enter your " + bankName + " username (or 0 to cancel): ");
            String usernameInput = getUserInput();
            if (usernameInput.equals("0")) {
                System.out.println("🔙 Payment cancelled.");
                this.paymentStatus = "Cancelled";
                return;
            }
            this.username = normalizeUsername(usernameInput);

            System.out.print("Enter your password (or 0 to cancel): ");
            String passwordInput = getMaskedInput();
            if (passwordInput.equals("0")) {
                System.out.println("🔙 Payment cancelled.");
                this.paymentStatus = "Cancelled";
                return;
            }
            this.password = passwordInput;

            // Simulate login verification
            System.out.println("\n🔄 Verifying credentials with " + bankName + "...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            if (!verifyCredentials()) {
                System.out.println("❌ Invalid username or password.");
                this.paymentStatus = "Failed";
                return;
            }

            System.out.println("✅ Login successful!");

            // Generate and send OTP
            this.generatedOTP = generateOTP();
            System.out.println("📱 OTP sent to your registered mobile number.");
            System.out.println("🔢 Generated OTP: " + generatedOTP + " (For demo purposes - in real system this would be sent via SMS)");
            System.out.print("Enter 6-digit OTP (or 0 to cancel): ");
            String otpInput = getUserInput();
            if (otpInput.equals("0")) {
                System.out.println("🔙 Payment cancelled.");
                this.paymentStatus = "Cancelled";
                return;
            }
            this.otpCode = otpInput;

            if (!verifyOTP()) {
                System.out.println("❌ Invalid OTP. Payment cancelled.");
                this.paymentStatus = "Failed";
                return;
            }

            // Process payment
            System.out.println("\n🔄 Processing payment through " + bankName + "...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Process payment (always successful for demo purposes)
            this.paymentStatus = "Paid";
            this.paymentSlip = generatePaymentSlip();
            System.out.println("✅ Payment successful!");
            System.out.println("Amount RM" + String.format("%.2f", totalAmount) + " debited from your " + bankName + " account");
            System.out.println("Reference: " + referenceNumber);
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid input. Please enter a valid number.");
            this.paymentStatus = "Failed";
        } catch (Exception e) {
            System.out.println("❌ Payment processing error: " + e.getMessage());
            this.paymentStatus = "Failed";
        }
    }

    /**
     * Normalizes username by trimming whitespace
     * @param usernameInput Username with possible extra spaces
     * @return Cleaned username
     */
    public String normalizeUsername(String usernameInput) {
        return usernameInput.trim();
    }

    private String getBankName(int choice) {
        switch (choice) {
            case 1: return "Maybank2u";
            case 2: return "CIMB Clicks";
            case 3: return "Public Bank";
            case 4: return "Hong Leong Bank";
            case 5: return "RHB Bank";
            default: return "Maybank2u";
        }
    }

    private boolean verifyCredentials() {
        // Simulate credential verification (90% success rate)
        return Math.random() > 0.1;
    }

    private boolean verifyOTP() {
        // Verify OTP against generated code
        return otpCode.equals(generatedOTP);
    }

    private String generateOTP() {
        // Generate a 6-digit OTP
        return String.format("%06d", (int)(Math.random() * 1000000));
    }

    private String getUserInput() {
        return scanner.nextLine().trim();
    }

    private String getMaskedInput() {
        // Simple password masking simulation
        String input = scanner.nextLine().trim();
        System.out.println("*".repeat(input.length())); // Show asterisks
        return input;
    }

    private String generatePaymentSlip() {
        return "=== ONLINE BANKING RECEIPT ===\n" +
               "Payment ID: " + paymentID + "\n" +
               "Reference: " + referenceNumber + "\n" +
               "Amount: RM" + String.format("%.2f", totalAmount) + "\n" +
               "Bank: " + bankName + "\n" +
               "Method: Online Banking\n" +
               "Date: " + timestamp + "\n" +
               "Status: PAID\n" +
               "Transaction completed successfully!";
    }
}
