/*
 * TransactionManager class for managing payment transactions.
 *
 * @author las
 */
package hillclimmer.PaymentModule;

import hillclimmer.DatabaseModule.PaymentDAO;
import java.util.*;

/**
 *
 * @author las
 */
public class TransactionManager {
    private String transactionManagerID;
    private List<Payment> transactionList;
    private double totalEarnings;
    private PaymentDAO paymentDAO;

    public TransactionManager(String transactionManagerID) {
        this.transactionManagerID = transactionManagerID;
        this.transactionList = new ArrayList<>();
        this.totalEarnings = 0.0;
        this.paymentDAO = new PaymentDAO();
        // Load transactions from DAO
        this.transactionList = paymentDAO.loadAll();
        calculateTotalEarnings();
    }

    public String getTransactionManagerID() {
        return transactionManagerID;
    }

    public List<Payment> getTransactionList() {
        return new ArrayList<>(transactionList);
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    // Record a successful transaction
    public void recordTransaction(Payment payment) {
        if ("Paid".equals(payment.getPaymentStatus())) {
            transactionList.add(payment);
            paymentDAO.save(payment); // Persist
            totalEarnings += payment.getTotalAmount();
            System.out.println("Transaction recorded: " + payment.getPaymentID());
        } else {
            System.out.println("Cannot record unpaid transaction: " + payment.getPaymentID());
        }
    }

    // Get transaction summary
    public String getTransactionSummary() {
        int paid = 0, pending = 0, failed = 0;
        for (Payment p : transactionList) {
            switch (p.getPaymentStatus()) {
                case "Paid": paid++; break;
                case "Pending": pending++; break;
                case "Failed": failed++; break;
            }
        }
        return "Transaction Summary:\n" +
               "Paid: " + paid + "\n" +
               "Pending: " + pending + "\n" +
               "Failed: " + failed + "\n" +
               "Total Earnings: " + totalEarnings;
    }

    // Get transaction by ID
    public Payment getTransactionByID(String paymentID) {
        return paymentDAO.load(paymentID);
    }

    // Calculate total earnings
    private void calculateTotalEarnings() {
        totalEarnings = paymentDAO.getTotalEarnings();
    }
}
