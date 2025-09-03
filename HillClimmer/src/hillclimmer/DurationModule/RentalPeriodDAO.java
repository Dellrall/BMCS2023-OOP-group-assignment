/*
 * RentalPeriodDAO class extending DataAccessObject for RentalPeriod data management.
 */
package hillclimmer.DurationModule;

import hillclimmer.DatabaseModule.DataAccessObject;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RentalPeriodDAO class extending DataAccessObject for RentalPeriod data management.
 * @author las
 */
public class RentalPeriodDAO extends DataAccessObject<RentalPeriod> {

    public RentalPeriodDAO() {
        super("rentalperiods.csv");
    }

    @Override
    protected String objectToCSV(RentalPeriod period) {
        return period.getPeriodId() + "," +
               period.getRentalId() + "," +
               period.getStartDate() + "," +
               period.getEndDate() + "," +
               period.getCreatedDate() + "," +
               period.getStatus() + "," +
               period.getDailyRate() + "," +
               period.getTotalCost() + "," +
               period.isIncludesInsurance() + "," +
               (period.getNotes() != null ? period.getNotes().replace(",", ";") : "");
    }

    @Override
    protected RentalPeriod csvToObject(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 10) {
            int periodId = Integer.parseInt(parts[0]);
            int rentalId = Integer.parseInt(parts[1]);
            LocalDate startDate = LocalDate.parse(parts[2]);
            LocalDate endDate = LocalDate.parse(parts[3]);
            // Skip createdDate and totalCost as they are calculated/set automatically
            String status = parts[5];
            double dailyRate = Double.parseDouble(parts[6]);
            boolean includesInsurance = Boolean.parseBoolean(parts[8]);
            String notes = parts[9].replace(";", ",");

            RentalPeriod period = new RentalPeriod(periodId, rentalId, startDate, endDate, dailyRate, includesInsurance);
            period.setStatus(status);
            if (!notes.isEmpty()) {
                period.setNotes(notes);
            }
            return period;
        }
        return null;
    }

    @Override
    protected String getId(RentalPeriod period) {
        return String.valueOf(period.getPeriodId());
    }

    // Additional query methods
    public List<RentalPeriod> getActiveRentalPeriods() {
        return loadAll().stream()
                .filter(RentalPeriod::isActive)
                .collect(Collectors.toList());
    }

    public List<RentalPeriod> getRentalPeriodsByRentalId(int rentalId) {
        return loadAll().stream()
                .filter(p -> p.getRentalId() == rentalId)
                .collect(Collectors.toList());
    }

    public List<RentalPeriod> getOverdueRentalPeriods() {
        return loadAll().stream()
                .filter(RentalPeriod::isOverdue)
                .collect(Collectors.toList());
    }

    public List<RentalPeriod> getRentalPeriodsByStatus(String status) {
        return loadAll().stream()
                .filter(p -> status.equals(p.getStatus()))
                .collect(Collectors.toList());
    }

    public double getTotalRevenue() {
        return loadAll().stream()
                .filter(RentalPeriod::isActive)
                .mapToDouble(RentalPeriod::getTotalCost)
                .sum();
    }
}
