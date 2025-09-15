/*
 * PaymentProcessor interface for standardized payment processing
 */
package hillclimmer.PaymentModule;

/**
 * PaymentProcessor interface for all payment methods in HillClimber
 * Provides a standardized way to handle different payment types
 *
 * @author las
 */
public interface PaymentProcessor {

    /**
     * Process a payment for the specified amount
     * @param amount The payment amount
     * @return PaymentResult containing status and details
     */
    PaymentResult processPayment(double amount);

    /**
     * Get the payment method name
     * @return Human-readable payment method name
     */
    String getPaymentMethodName();

    /**
     * Check if this payment method requires user interaction
     * @return true if interactive, false if automatic
     */
    boolean isInteractive();

    /**
     * Get the current payment status
     * @return Payment status string
     */
    String getPaymentStatus();

    /**
     * Cancel the current payment process
     */
    void cancelPayment();

    /**
     * Default implementation for common payment validation
     * @param amount The amount to validate
     * @return true if amount is valid
     */
    default boolean validateAmount(double amount) {
        return amount > 0 && amount <= 10000.0; // Max RM10,000 per transaction
    }
}