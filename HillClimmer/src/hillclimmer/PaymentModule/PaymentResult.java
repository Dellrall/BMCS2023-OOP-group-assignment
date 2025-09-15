/*
 * PaymentResult class for standardized payment outcomes
 */
package hillclimmer.PaymentModule;

/**
 * PaymentResult class encapsulates the result of a payment operation
 * Provides consistent way to handle payment success/failure across all payment methods
 *
 * @author las
 */
public class PaymentResult {
    private boolean successful;
    private String errorMessage;
    private String transactionId;
    private double amount;
    private String paymentMethod;

    /**
     * Constructor for successful payment
     */
    public PaymentResult(boolean successful, String errorMessage, String transactionId,
                        double amount, String paymentMethod) {
        this.successful = successful;
        this.errorMessage = errorMessage;
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
    }

    /**
     * Factory method for successful payment
     */
    public static PaymentResult success(String transactionId, double amount, String paymentMethod) {
        return new PaymentResult(true, null, transactionId, amount, paymentMethod);
    }

    /**
     * Factory method for failed payment
     */
    public static PaymentResult failure(String errorMessage, double amount, String paymentMethod) {
        return new PaymentResult(false, errorMessage, null, amount, paymentMethod);
    }

    // Getters
    public boolean isSuccessful() { return successful; }
    public String getErrorMessage() { return errorMessage; }
    public String getTransactionId() { return transactionId; }
    public double getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }

    @Override
    public String toString() {
        if (successful) {
            return String.format("Payment successful: %s - Transaction: %s - Amount: RM%.2f",
                               paymentMethod, transactionId, amount);
        } else {
            return String.format("Payment failed: %s - Error: %s - Amount: RM%.2f",
                               paymentMethod, errorMessage, amount);
        }
    }
}