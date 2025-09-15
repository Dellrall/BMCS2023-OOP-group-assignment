/*
 * CreditCardPaymentProcessor class implementing PaymentProcessor interface
 */
package hillclimmer.PaymentModule;

import java.util.Scanner;

/**
 * CreditCardPaymentProcessor class for processing credit card payments
 * Implements PaymentProcessor interface for standardized payment handling
 *
 * @author las
 */
public class CreditCardPaymentProcessor implements PaymentProcessor {
    private static final Scanner scanner = new Scanner(System.in);
    private String paymentStatus = "Not Started";
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    private String cardType;

    @Override
    public PaymentResult processPayment(double amount) {
        if (!validateAmount(amount)) {
            paymentStatus = "Failed";
            return PaymentResult.failure("Invalid payment amount", amount, getPaymentMethodName());
        }

        try {
            System.out.println("\n=== CREDIT CARD PAYMENT ===");
            System.out.println("Amount to pay: RM" + String.format("%.2f", amount));
            System.out.println("💡 Enter '0' at any input to cancel payment");

            // Collect card details
            cardNumber = readCardNumber();
            if (cardNumber.equals("0")) {
                cancelPayment();
                return PaymentResult.failure("Payment cancelled", amount, getPaymentMethodName());
            }

            cardHolderName = readCardHolderName();
            if (cardHolderName.equals("0")) {
                cancelPayment();
                return PaymentResult.failure("Payment cancelled", amount, getPaymentMethodName());
            }

            expiryDate = readExpiryDate();
            if (expiryDate.equals("0")) {
                cancelPayment();
                return PaymentResult.failure("Payment cancelled", amount, getPaymentMethodName());
            }

            cvv = readCVV();
            if (cvv.equals("0")) {
                cancelPayment();
                return PaymentResult.failure("Payment cancelled", amount, getPaymentMethodName());
            }

            // Validate card details
            if (!isValidCardDetails()) {
                paymentStatus = "Failed";
                return PaymentResult.failure("Invalid card details", amount, getPaymentMethodName());
            }

            // Determine card type
            cardType = determineCardType(cardNumber);

            // Simulate payment processing
            System.out.println("\n🔄 Processing credit card payment...");
            System.out.println("Contacting " + cardType + " payment gateway...");

            try {
                Thread.sleep(2000); // Simulate processing time
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Process payment (always successful for demo purposes)
            String transactionId = "CC" + System.currentTimeMillis();
            paymentStatus = "Paid";

            System.out.println("✅ Payment successful!");
            System.out.println("Card ending in " + cardNumber.substring(cardNumber.length() - 4) + " charged RM" + String.format("%.2f", amount));
            System.out.println("Reference: " + transactionId);

            return PaymentResult.success(transactionId, amount, getPaymentMethodName());

        } catch (Exception e) {
            paymentStatus = "Failed";
            return PaymentResult.failure("Payment processing error: " + e.getMessage(), amount, getPaymentMethodName());
        }
    }

    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }

    @Override
    public boolean isInteractive() {
        return true;
    }

    @Override
    public String getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public void cancelPayment() {
        paymentStatus = "Cancelled";
        System.out.println("💳 Credit card payment cancelled.");
    }

    // Helper methods
    private String readCardNumber() {
        System.out.print("Enter card number (16 digits, spaces/dashes allowed) or 0 to cancel: ");
        String input = scanner.nextLine().trim();
        return normalizeCardNumber(input);
    }

    private String readCardHolderName() {
        System.out.print("Enter card holder name or 0 to cancel: ");
        return scanner.nextLine().trim();
    }

    private String readExpiryDate() {
        System.out.print("Enter expiry date (MM/YY or MM-YY or MM YY) or 0 to cancel: ");
        String input = scanner.nextLine().trim();
        return normalizeExpiryDate(input);
    }

    private String readCVV() {
        System.out.print("Enter CVV (3 digits) or 0 to cancel: ");
        return scanner.nextLine().trim();
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

    private String normalizeCardNumber(String cardInput) {
        return cardInput.replaceAll("[\\s-]", "");
    }

    private String normalizeExpiryDate(String expiryInput) {
        // Remove spaces and replace dashes with slashes
        String cleaned = expiryInput.replaceAll("\\s", "").replace("-", "/");

        // If no separator exists and we have 4 digits, add slash between MM and YY
        if (!cleaned.contains("/") && cleaned.length() == 4) {
            cleaned = cleaned.substring(0, 2) + "/" + cleaned.substring(2);
        }

        return cleaned;
    }

    private String determineCardType(String cardNumber) {
        if (cardNumber.startsWith("4")) {
            return "Visa";
        } else if (cardNumber.startsWith("5") || cardNumber.startsWith("2")) {
            return "Mastercard";
        } else if (cardNumber.startsWith("3")) {
            return "American Express";
        } else {
            return "Unknown";
        }
    }
}