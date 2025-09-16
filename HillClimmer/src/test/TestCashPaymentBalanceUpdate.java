package test;

import hillclimmer.CustomerModule.Customer;
import hillclimmer.DatabaseModule.CustomerDAO;
import hillclimmer.RentalModule.Rental;
import hillclimmer.RentalModule.RentalManager;
import hillclimmer.PaymentModule.CashPayment;
import hillclimmer.DatabaseModule.PaymentDAO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestCashPaymentBalanceUpdate {
    public static void main(String[] args) {
        System.out.println("=== TESTING CASH PAYMENT BALANCE UPDATE ===");

        CustomerDAO customerDAO = new CustomerDAO();
        RentalManager rentalManager = new RentalManager();

        // Check initial customer balance
        Customer customer = customerDAO.load("C009");
        System.out.println("Initial customer balance: RM" + customer.getOutstandingBalance());

        // Find the pending rental
        Rental pendingRental = null;
        for (Rental rental : rentalManager.getAllRentals()) {
            if (rental.getRentalId() == 7 && "Pending".equals(rental.getPaymentStatus())) {
                pendingRental = rental;
                break;
            }
        }

        if (pendingRental == null) {
            System.out.println("No pending rental found with ID 7");
            return;
        }

        System.out.println("Found pending rental ID 6, amount: RM" + pendingRental.getTotalCost());

        // Process cash payment
        String paymentID = "CASH" + System.currentTimeMillis();
        String customerId = "C" + String.format("%03d", pendingRental.getCustomerId());
        CashPayment cashPayment = new CashPayment(paymentID, pendingRental.getTotalCost(),
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            customerId);

        // Mark as paid
        cashPayment.markAsPaid("Test Manager");

        // Save payment
        PaymentDAO paymentDAO = new PaymentDAO();
        paymentDAO.save(cashPayment);

        // Update rental status
        pendingRental.setPaymentStatus("Paid");
        rentalManager.updateRental(pendingRental);

        // Update customer balance
        Customer updatedCustomer = customerDAO.load(customerId);
        if (updatedCustomer != null) {
            double currentBalance = updatedCustomer.getOutstandingBalance();
            double newBalance = Math.max(0, currentBalance - pendingRental.getTotalCost());
            updatedCustomer.setOutstandingBalance(newBalance);
            customerDAO.update(updatedCustomer);

            System.out.println("✅ Cash payment processed!");
            System.out.println("💰 Balance updated: RM" + currentBalance + " → RM" + newBalance);

            // Verify the update
            Customer finalCustomer = customerDAO.load(customerId);
            System.out.println("Final customer balance: RM" + finalCustomer.getOutstandingBalance());
        }
    }
}