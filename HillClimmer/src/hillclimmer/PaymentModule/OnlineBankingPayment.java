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
    private String bankName;
    private String accountNumber;
    private String username;
    private String password;
    private String otpCode;

    public OnlineBankingPayment(String paymentID, double totalAmount, String timestamp, String customerID) {
        super(paymentID, totalAmount, "Online Banking", "Pending", timestamp, customerID);
        this.bankName = "";
        this.accountNumber = "";
        this.username = "";
        this.password = "";
        this.otpCode = "";
    }

    @Override
    public void processPayment() {
        System.out.println("\n=== ONLINE BANKING PAYMENT ===");
        System.out.println("Amount to pay: RM" + String.format("%.2f", totalAmount));
        System.out.println("Reference Number: " + referenceNumber);

        // Select bank
        System.out.println("Available Banks:");
        System.out.println("1. Maybank2u");
        System.out.println("2. CIMB Clicks");
        System.out.println("3. Public Bank");
        System.out.println("4. Hong Leong Bank");
        System.out.println("5. RHB Bank");
        System.out.print("Select your bank (1-5): ");

        int bankChoice = Integer.parseInt(getUserInput());
        this.bankName = getBankName(bankChoice);

        // Collect banking details
        System.out.print("Enter your " + bankName + " username: ");
        this.username = getUserInput();

        System.out.print("Enter your password: ");
        this.password = getMaskedInput();

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

        // Request OTP
        System.out.println("📱 OTP sent to your registered mobile number.");
        System.out.print("Enter 6-digit OTP: ");
        this.otpCode = getUserInput();

        if (!verifyOTP()) {
            System.out.println("❌ Invalid OTP.");
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

        // Simulate success/failure (95% success rate)
        if (Math.random() > 0.05) {
            this.paymentStatus = "Paid";
            this.paymentSlip = generatePaymentSlip();
            System.out.println("✅ Payment successful!");
            System.out.println("Amount RM" + String.format("%.2f", totalAmount) + " debited from your " + bankName + " account");
            System.out.println("Reference: " + referenceNumber);
        } else {
            this.paymentStatus = "Failed";
            System.out.println("❌ Payment failed due to insufficient funds or technical error.");
        }
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
        // Simulate OTP verification (95% success rate)
        return otpCode.length() == 6 && Math.random() > 0.05;
    }

    private String getUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextLine().trim();
        }
    }

    private String getMaskedInput() {
        // Simple password masking simulation
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine().trim();
            System.out.println("*".repeat(input.length())); // Show asterisks
            return input;
        }
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
