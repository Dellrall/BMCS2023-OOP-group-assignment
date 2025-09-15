/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.DatabaseModule;

import hillclimmer.PaymentModule.Payment;
import hillclimmer.PaymentModule.CashPayment;
import java.util.List;

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

    /**
     * Search for a payment by reference number
     * @param referenceNumber The reference number to search for
     * @return Payment object if found, null otherwise
     */
    public Payment getByReferenceNumber(String referenceNumber) {
        if (referenceNumber == null || referenceNumber.trim().isEmpty()) {
            return null;
        }

        List<Payment> payments = loadAll();
        for (Payment payment : payments) {
            if (referenceNumber.equals(payment.getReferenceNumber())) {
                return payment;
            }
        }

        // If not found with exact match, try a more robust search
        // This handles cases where CSV parsing might be affected by embedded commas in payment slips
        return searchByReferenceInRawData(referenceNumber);
    }

    /**
     * Fallback search method that searches raw CSV data for reference numbers
     * This handles cases where payment slips contain commas that break normal CSV parsing
     */
    private Payment searchByReferenceInRawData(String referenceNumber) {
        try {
            List<String> lines = java.nio.file.Files.readAllLines(
                java.nio.file.Paths.get(System.getProperty("user.dir") + "/data/payments.csv"));

            for (String line : lines) {
                if (line.contains(referenceNumber)) {
                    // Found a line containing the reference number
                    // Parse it more carefully, assuming the reference number is in the expected position
                    String[] parts = line.split(",", 8); // Split into at most 8 parts
                    if (parts.length >= 7) {
                        String paymentID = parts[0];
                        double totalAmount = Double.parseDouble(parts[1]);
                        String paymentMethod = parts[2];
                        String paymentStatus = parts[3];
                        String timestamp = parts[4];
                        String customerID = parts[5];
                        String refNum = parts[6];

                        // Check if this is the reference number we're looking for
                        if (referenceNumber.equals(refNum)) {
                            Payment payment = Payment.createPayment(paymentMethod, paymentID, totalAmount, timestamp, customerID);
                            payment.updateStatus(paymentStatus);
                            return payment;
                        }
                    }
                }
            }
        } catch (Exception e) {
            // If file reading fails, return null
            return null;
        }

        return null;
    }
}
