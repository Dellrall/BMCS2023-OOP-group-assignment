/*
 * Test class for PaymentProcessor interface
 */
package hillclimmer;

import hillclimmer.PaymentModule.*;

/**
 * Simple test to verify PaymentProcessor interface works
 */
public class PaymentProcessorTest {
    public static void main(String[] args) {
        System.out.println("🧪 Testing PaymentProcessor Interface");
        System.out.println("====================================");

        // Test Credit Card Payment Processor
        System.out.println("\n1. Testing CreditCardPaymentProcessor...");
        PaymentProcessor creditCard = new CreditCardPaymentProcessor();

        System.out.println("   Payment Method: " + creditCard.getPaymentMethodName());
        System.out.println("   Is Interactive: " + creditCard.isInteractive());
        System.out.println("   Initial Status: " + creditCard.getPaymentStatus());

        // Test amount validation
        System.out.println("   Amount RM100.00 valid: " + creditCard.validateAmount(100.00));
        System.out.println("   Amount RM-50.00 valid: " + creditCard.validateAmount(-50.00));
        System.out.println("   Amount RM15000.00 valid: " + creditCard.validateAmount(15000.00));

        System.out.println("\n✅ PaymentProcessor interface test completed!");
        System.out.println("💡 The interface is ready for use in the main application.");
        System.out.println("💡 Users can now choose between traditional and interface-based payment processing.");
    }
}