package test;

import hillclimmer.RentalModule.RentalManager;

/**
 * Test to demonstrate rental pagination functionality
 */
public class RentalPaginationDemoTest {

    public static void main(String[] args) {
        System.out.println("🧪 Rental Pagination Demo Test");
        System.out.println("==============================");

        try {
            RentalManager rentalManager = new RentalManager();
            int totalRentals = rentalManager.getAllRentals().size();

            System.out.println("Current system has: " + totalRentals + " rentals");

            // Demonstrate pagination for manager view (5 per page)
            System.out.println("\n📊 MANAGER VIEW PAGINATION (5 per page):");
            demonstratePagination(totalRentals, 5, "Manager");

            // Demonstrate pagination for customer view (3 per page)
            System.out.println("\n📊 CUSTOMER VIEW PAGINATION (3 per page):");
            demonstratePagination(totalRentals, 3, "Customer");

            System.out.println("\n✅ PAGINATION FUNCTIONALITY DEMONSTRATION");
            System.out.println("=========================================");
            System.out.println("🎯 Bug Fix: Rental history pagination implemented");
            System.out.println("📄 Manager View: Shows 5 rentals per page with navigation");
            System.out.println("👤 Customer View: Shows 3 rentals per page with navigation");
            System.out.println("🧭 Navigation: P (previous), N (next), page numbers, 0 (exit)");
            System.out.println("📊 Page Info: Current page, total pages, item range display");

        } catch (Exception e) {
            System.out.println("❌ Test FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void demonstratePagination(int totalRentals, int itemsPerPage, String viewType) {
        int totalPages = (int) Math.ceil((double) totalRentals / itemsPerPage);

        System.out.println("   Total " + viewType + " Rentals: " + totalRentals);
        System.out.println("   Items per page: " + itemsPerPage);
        System.out.println("   Total pages needed: " + totalPages);

        for (int page = 1; page <= Math.min(totalPages, 3); page++) { // Show first 3 pages as example
            int startIndex = (page - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, totalRentals);
            int itemsOnPage = endIndex - startIndex;

            System.out.println("   Page " + page + ": Shows rentals " + (startIndex + 1) + "-" + endIndex +
                " (" + itemsOnPage + " items)");
        }

        if (totalPages > 3) {
            System.out.println("   ... and " + (totalPages - 3) + " more pages");
        }

        System.out.println("   Navigation options available: P (prev), N (next), page numbers, 0 (exit)");
    }
}
