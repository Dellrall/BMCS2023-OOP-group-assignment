package test;

import hillclimmer.RentalModule.RentalManager;

/**
 * Simple test to check current rental count and pagination logic
 */
public class SimpleRentalCheckTest {

    public static void main(String[] args) {
        System.out.println("🧪 Simple Rental Check Test");
        System.out.println("===========================");

        try {
            RentalManager rentalManager = new RentalManager();
            int totalRentals = rentalManager.getAllRentals().size();

            System.out.println("Current total rentals: " + totalRentals);

            // Test pagination calculations
            int itemsPerPage = 5;
            int totalPages = (int) Math.ceil((double) totalRentals / itemsPerPage);

            System.out.println("Items per page: " + itemsPerPage);
            System.out.println("Calculated total pages: " + totalPages);

            for (int page = 1; page <= totalPages; page++) {
                int startIndex = (page - 1) * itemsPerPage;
                int endIndex = Math.min(startIndex + itemsPerPage, totalRentals);
                int itemsOnPage = endIndex - startIndex;

                System.out.println("Page " + page + ": rentals " + (startIndex + 1) + "-" + endIndex +
                    " (" + itemsOnPage + " items)");
            }

            System.out.println("\n✅ Test completed successfully");

        } catch (Exception e) {
            System.out.println("❌ Test FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
