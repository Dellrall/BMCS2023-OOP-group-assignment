/*
 * CashPayment class for handling cash payments
 */
package hillclimmer.PaymentModule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * CashPayment class for processing cash payments
 * Generates payment slips for customers to pay at counter
 *
 * @author las
 */
public class CashPayment extends Payment {
    private String paymentLocation;
    private String cashierName;
    private LocalDateTime paymentDeadline;
    private boolean isPaid;

    public CashPayment(String paymentID, double totalAmount, String timestamp, String customerID) {
        super(paymentID, totalAmount, "Cash", "Pending", timestamp, customerID);
        this.paymentLocation = "HillClimmer Main Counter";
        this.cashierName = "";
        this.paymentDeadline = LocalDateTime.now().plusDays(7); // 7 days to pay
        this.isPaid = false;
        generatePaymentSlip();
    }

    @Override
    public void processPayment() {
        System.out.println("\n=== CASH PAYMENT ===");
        System.out.println("Amount to pay: RM" + String.format("%.2f", totalAmount));
        System.out.println("Reference Number: " + referenceNumber);

        System.out.println("\n📄 PAYMENT SLIP GENERATED");
        System.out.println("=".repeat(50));
        System.out.println(paymentSlip);
        System.out.println("=".repeat(50));

        System.out.println("\n💰 CASH PAYMENT INSTRUCTIONS:");
        System.out.println("1. Take this payment slip to " + paymentLocation);
        System.out.println("2. Pay the exact amount: RM" + String.format("%.2f", totalAmount));
        System.out.println("3. Show your Reference Number: " + referenceNumber);
        System.out.println("4. Payment deadline: " + paymentDeadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        System.out.println("\n⚠️  IMPORTANT NOTES:");
        System.out.println("• This is a PREBOOKING - your rental is not confirmed until payment is made");
        System.out.println("• Payment must be made within 7 days or booking will be cancelled");
        System.out.println("• Bring this payment slip and valid ID when paying");
        System.out.println("• No refunds for cancellations after payment");

        System.out.println("\n✅ Payment slip generated successfully!");
        System.out.println("Please proceed to the counter to complete your payment.");
        System.out.println("Your booking reference: " + referenceNumber);

        // Keep status as Pending until cash is actually paid at counter
        this.paymentStatus = "Prebooked - Awaiting Cash Payment";
    }

    public void markAsPaid(String cashierName) {
        this.cashierName = cashierName;
        this.isPaid = true;
        this.paymentStatus = "Paid";
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        updatePaymentSlip();
        System.out.println("✅ Cash payment received by " + cashierName);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(paymentDeadline);
    }

    public String getPaymentLocation() {
        return paymentLocation;
    }

    public LocalDateTime getPaymentDeadline() {
        return paymentDeadline;
    }

    public boolean isPaid() {
        return isPaid;
    }

    private void generatePaymentSlip() {
        this.paymentSlip = "=== HillClimmer CASH PAYMENT SLIP ===\n" +
                          "Payment ID: " + paymentID + "\n" +
                          "Reference Number: " + referenceNumber + "\n" +
                          "Customer ID: " + customerID + "\n" +
                          "Amount Due: RM" + String.format("%.2f", totalAmount) + "\n" +
                          "Payment Method: CASH\n" +
                          "Payment Location: " + paymentLocation + "\n" +
                          "Generated: " + timestamp + "\n" +
                          "Deadline: " + paymentDeadline.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + "\n" +
                          "Status: PENDING PAYMENT\n\n" +
                          "INSTRUCTIONS:\n" +
                          "1. Present this slip at the counter\n" +
                          "2. Pay exact amount in cash\n" +
                          "3. Keep receipt for your records\n\n" +
                          "*** THIS IS A PREBOOKING ***\n" +
                          "Payment must be completed within 7 days";
    }

    private void updatePaymentSlip() {
        this.paymentSlip = "=== HillClimmer CASH PAYMENT RECEIPT ===\n" +
                          "Payment ID: " + paymentID + "\n" +
                          "Reference Number: " + referenceNumber + "\n" +
                          "Customer ID: " + customerID + "\n" +
                          "Amount Paid: RM" + String.format("%.2f", totalAmount) + "\n" +
                          "Payment Method: CASH\n" +
                          "Payment Location: " + paymentLocation + "\n" +
                          "Paid Date: " + timestamp + "\n" +
                          "Cashier: " + cashierName + "\n" +
                          "Status: PAID\n\n" +
                          "Thank you for your payment!\n" +
                          "Your booking is now confirmed.";
    }
}
