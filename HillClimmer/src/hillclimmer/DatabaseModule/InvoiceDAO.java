/*
 * InvoiceDAO class extending DataAccessObject for Invoice data management.
 *
 * @author las
 */
package hillclimmer.DatabaseModule;

import hillclimmer.PaymentModule.Invoice;
import java.util.*;

/**
 * InvoiceDAO class extending DataAccessObject for Invoice data management.
 *
 * @author las
 */
public class InvoiceDAO extends DataAccessObject<Invoice> {

    public InvoiceDAO() {
        super("data/invoices.csv");
    }

    @Override
    protected String objectToCSV(Invoice invoice) {
        String itemListStr = String.join(";", invoice.getItemList());
        return invoice.getInvoiceID() + "," +
               invoice.getVehicleID() + "," +
               invoice.getCustomerID() + "," +
               invoice.getIssueDate() + "," +
               invoice.getDueDate() + "," +
               itemListStr + "," +
               invoice.getDiscount();
    }

    @Override
    protected Invoice csvToObject(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 7) {
            String invoiceID = parts[0];
            String vehicleID = parts[1];
            String customerID = parts[2];
            String issueDate = parts[3];
            String dueDate = parts[4];
            String itemListStr = parts[5];
            double discount = Double.parseDouble(parts[6]);

            Invoice invoice = new Invoice(invoiceID, vehicleID, customerID, issueDate, dueDate);
            if (!itemListStr.isEmpty()) {
                String[] items = itemListStr.split(";");
                for (String item : items) {
                    String[] itemParts = item.split(":");
                    if (itemParts.length == 2) {
                        invoice.addItem(itemParts[0], Double.parseDouble(itemParts[1]));
                    }
                }
            }
            invoice.applyDiscount(discount);
            return invoice;
        }
        return null;
    }

    @Override
    protected String getId(Invoice invoice) {
        return invoice.getInvoiceID();
    }

    // Additional methods
    public List<Invoice> getInvoicesByCustomer(String customerID) {
        List<Invoice> result = new ArrayList<>();
        for (Invoice i : loadAll()) {
            if (customerID.equals(i.getCustomerID())) {
                result.add(i);
            }
        }
        return result;
    }
}
