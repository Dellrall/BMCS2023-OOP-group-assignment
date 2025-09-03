/*
 * CreditCardPayment class for handling credit card payments
 */
package hillclimmer.PaymentModule;

import java.util.Scanner;

/**
 * CreditCardPayment class for processing credit card payments
 * Simulates real credit card payment processing
 *
 * @author las
 */
public class CreditCardPayment extends Payment {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    private String cardType;

    public CreditCardPayment(String paymentID, double totalAmount, String timestamp, String customerID) {
        super(paymentID, totalAmount, "Credit Card", "Pending", timestamp, customerID);
        this.cardNumber = "";
        this.cardHolderName = "";
        this.expiryDate = "";
        this.cvv = "";
        this.cardType = "";
    }

    @Override
    public void processPayment() {
        System.out.println("\n=== CREDIT CARD PAYMENT ===");
        System.out.println("Amount to pay: RM" + String.format("%.2f", totalAmount));
        System.out.println("Reference Number: " + referenceNumber);

        // Collect card details
        System.out.print("Enter card number (16 digits): ");
        this.cardNumber = getUserInput();

        System.out.print("Enter card holder name: ");
        this.cardHolderName = getUserInput();

        System.out.print("Enter expiry date (MM/YY): ");
        this.expiryDate = getUserInput();

        System.out.print("Enter CVV (3 digits): ");
        this.cvv = getUserInput();

        // Validate card details
        if (!isValidCardDetails()) {
            System.out.println("❌ Invalid card details. Payment failed.");
            this.paymentStatus = "Failed";
            return;
        }

        // Determine card type
        this.cardType = determineCardType(cardNumber);

        // Simulate payment processing
        System.out.println("\n🔄 Processing credit card payment...");
        System.out.println("Contacting " + cardType + " payment gateway...");

        try {
            Thread.sleep(2000); // Simulate processing time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Simulate success/failure (90% success rate)
        if (Math.random() > 0.1) {
            this.paymentStatus = "Paid";
            this.paymentSlip = generatePaymentSlip();
            System.out.println("✅ Payment successful!");
            System.out.println("Card ending in " + cardNumber.substring(cardNumber.length() - 4) + " charged RM" + String.format("%.2f", totalAmount));
            System.out.println("Reference: " + referenceNumber);
        } else {
            this.paymentStatus = "Failed";
            System.out.println("❌ Payment declined by card issuer.");
        }
    }

    private boolean isValidCardDetails() {
        // Basic validation
        if (cardNumber.length() != 16 || !cardNumber.matches("\\d+")) {
            return false;
        }
        if (cardHolderName.isEmpty()) {
            return false;
        }
        if (!expiryDate.matches("\\d{2}/\\d{2}")) {
            return false;
        }
        if (cvv.length() != 3 || !cvv.matches("\\d+")) {
            return false;
        }

        // Check expiry date
        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]) + 2000;

        if (month < 1 || month > 12) {
            return false;
        }

        // Check if card is not expired
        java.time.LocalDate expiry = java.time.LocalDate.of(year, month, 1);
        if (expiry.isBefore(java.time.LocalDate.now())) {
            return false;
        }

        return true;
    }

    private String determineCardType(String cardNumber) {
        if (cardNumber.startsWith("4")) {
            return "Visa";
        } else if (cardNumber.startsWith("5") || cardNumber.startsWith("2")) {
            return "MasterCard";
        } else if (cardNumber.startsWith("3")) {
            return "American Express";
        } else {
            return "Unknown Card";
        }
    }

    private String getUserInput() {
        try (Scanner scanner = new Scanner(System.in)) {
            return scanner.nextLine().trim();
        }
    }

    private String generatePaymentSlip() {
        return "=== PAYMENT RECEIPT ===\n" +
               "Payment ID: " + paymentID + "\n" +
               "Reference: " + referenceNumber + "\n" +
               "Amount: RM" + String.format("%.2f", totalAmount) + "\n" +
               "Method: " + cardType + " ****" + cardNumber.substring(cardNumber.length() - 4) + "\n" +
               "Date: " + timestamp + "\n" +
               "Status: PAID\n" +
               "Thank you for your payment!";
    }
}
