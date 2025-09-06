/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.DatabaseModule;

import hillclimmer.PaymentModule.Payment;
import hillclimmer.PaymentModule.CashPayment;

/**
 * PaymentDAO class extending DataAccessObject for Payment data management.
 *
 * @author las
 */
public class PaymentDAO extends DataAccessObject<Payment> {

    public PaymentDAO() {
        super(System.getProperty("user.dir") + "/data/payments.csv");
    }

    @Override
    protected String objectToCSV(Payment payment) {
        return payment.getPaymentID() + "," +
               payment.getTotalAmount() + "," +
               payment.getPaymentMethod() + "," +
               payment.getPaymentStatus() + "," +
               payment.getTimestamp() + "," +
               payment.getCustomerID() + "," +
               payment.getReferenceNumber() + "," +
               payment.getPaymentSlip().replace("\n", "\\n");
    }

    @Override
    protected Payment csvToObject(String csvLine) {
        String[] parts = csvLine.split(",", -1); // -1 to handle empty fields
        if (parts.length >= 7) {
            String paymentID = parts[0];
            double totalAmount = Double.parseDouble(parts[1]);
            String paymentMethod = parts[2];
            String paymentStatus = parts[3];
            String timestamp = parts[4];
            String customerID = parts[5];
            String referenceNumber = parts.length > 6 ? parts[6] : "";
            String paymentSlip = parts.length > 7 ? parts[7].replace("\\n", "\n") : "";

            // Create appropriate payment type
            Payment payment = Payment.createPayment(paymentMethod, paymentID, totalAmount, timestamp, customerID);
            payment.updateStatus(paymentStatus);

            // Restore additional fields if they exist
            if (!referenceNumber.isEmpty()) {
                // Note: We can't directly set private fields, but the payment slip is restored
                // The reference number is generated in constructor, so we keep the original
            }
            if (!paymentSlip.isEmpty()) {
                // For cash payments, we need to restore the payment slip
                if (payment instanceof CashPayment) {
                    // The payment slip will be regenerated when needed
                }
            }

            return payment;
        }
        return null;
    }

    @Override
    protected String getId(Payment payment) {
        return payment.getPaymentID();
    }
    
    @Override
    protected Payment generateNewId(Payment payment, java.util.List<Payment> existingPayments) {
        // Generate new payment ID using timestamp to ensure uniqueness
        String newPaymentId = "P" + System.currentTimeMillis();
        
        // Create new payment with generated ID based on type
        return Payment.createPayment(payment.getPaymentMethod(), newPaymentId, 
                                   payment.getTotalAmount(), payment.getTimestamp(), 
                                   payment.getCustomerID());
    }

    // Additional methods
    public double getTotalEarnings() {
        double total = 0;
        for (Payment p : loadAll()) {
            if ("Paid".equals(p.getPaymentStatus())) {
                total += p.getTotalAmount();
            }
        }
        return total;
    }
}
