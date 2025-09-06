/*
 * RentalPeriodDAO class extending DataAccessObject for RentalPeriod data management.
 */
package hillclimmer.DatabaseModule;

import hillclimmer.DurationModule.RentalPeriod;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RentalPeriodDAO class extending DataAccessObject for RentalPeriod data management.
 * @author las
 */
public class RentalPeriodDAO extends DataAccessObject<RentalPeriod> {

    public RentalPeriodDAO() {
        super("data/rentalperiods.csv");
    }

    @Override
    protected String getId(RentalPeriod period) {
        return String.valueOf(period.getPeriodId());
    }
    
    @Override
    protected RentalPeriod generateNewId(RentalPeriod period, java.util.List<RentalPeriod> existingPeriods) {
        // Generate new period ID based on existing periods
        int maxId = existingPeriods.stream()
                .mapToInt(RentalPeriod::getPeriodId)
                .max()
                .orElse(0);
        
        // Create new rental period with generated ID
        RentalPeriod newPeriod = new RentalPeriod(maxId + 1, period.getRentalId(), 
                                                period.getStartDate(), period.getEndDate(), 
                                                period.getDailyRate(), period.isIncludesInsurance());
        newPeriod.setStatus(period.getStatus());
        if (period.getNotes() != null) {
            newPeriod.setNotes(period.getNotes());
        }
        
        return newPeriod;
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
        try {
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
                period.setNotes(notes);
                return period;
            }
        } catch (Exception e) {
            System.err.println("Warning: Skipping corrupted rental period CSV line. Error: " + e.getMessage());
        }
        return null;
    }

    // Additional methods specific to RentalPeriodDAO
    public List<RentalPeriod> getActivePeriods() {
        return loadAll().stream()
                .filter(period -> "ACTIVE".equals(period.getStatus()))
                .collect(Collectors.toList());
    }

    public List<RentalPeriod> getPeriodsByRentalId(int rentalId) {
        return loadAll().stream()
                .filter(period -> period.getRentalId() == rentalId)
                .collect(Collectors.toList());
    }

    public double getTotalRevenueFromActivePeriods() {
        return loadAll().stream()
                .filter(period -> "ACTIVE".equals(period.getStatus()))
                .mapToDouble(RentalPeriod::getTotalCost)
                .sum();
    }

    public List<RentalPeriod> getOverduePeriods() {
        LocalDate today = LocalDate.now();
        return loadAll().stream()
                .filter(period -> "ACTIVE".equals(period.getStatus()) && period.getEndDate().isBefore(today))
                .collect(Collectors.toList());
    }
}
