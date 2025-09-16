/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.DurationModule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * RentalPeriod class for managing rental duration periods and calculations
 * @author las
 */
public class RentalPeriod {
    private int periodId;
    private int rentalId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdDate;
    private String status; // "ACTIVE", "COMPLETED", "EXTENDED", "CANCELLED"
    private double dailyRate;
    private double totalCost;
    private boolean includesInsurance;
    private String notes;

    // Default constructor
    public RentalPeriod() {
        this.createdDate = LocalDateTime.now();
        this.status = "ACTIVE";
        this.includesInsurance = false;
        this.totalCost = 0.0;
    }

    // Parameterized constructor
    public RentalPeriod(int periodId, int rentalId, LocalDate startDate,
                       LocalDate endDate, double dailyRate, boolean includesInsurance) {
        this();
        this.periodId = periodId;
        this.rentalId = rentalId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dailyRate = dailyRate;
        this.includesInsurance = includesInsurance;
        this.totalCost = calculateTotalCost();
    }

    // Getters
    public int getPeriodId() {
        return periodId;
    }

    public int getRentalId() {
        return rentalId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getStatus() {
        return status;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public boolean isIncludesInsurance() {
        return includesInsurance;
    }

    public String getNotes() {
        return notes;
    }

    // Setters
    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public void setRentalId(int rentalId) {
        this.rentalId = rentalId;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        this.totalCost = calculateTotalCost();
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        this.totalCost = calculateTotalCost();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
        this.totalCost = calculateTotalCost();
    }

    public void setIncludesInsurance(boolean includesInsurance) {
        this.includesInsurance = includesInsurance;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Business methods
    public long getDurationInDays() {
        if (startDate != null && endDate != null) {
            return ChronoUnit.DAYS.between(startDate, endDate) + 1; // Include both start and end dates
        }
        return 0;
    }

    public long getDurationInHours() {
        if (startDate != null && endDate != null) {
            return ChronoUnit.HOURS.between(startDate.atStartOfDay(), endDate.atStartOfDay()) + 24;
        }
        return 0;
    }

    public boolean isActive() {
        return "ACTIVE".equals(status);
    }

    public boolean isOverdue() {
        return LocalDate.now().isAfter(endDate) && isActive();
    }

    public boolean isStartingSoon() {
        LocalDate now = LocalDate.now();
        return startDate.isAfter(now) && startDate.isBefore(now.plusDays(3));
    }

    public boolean isEndingSoon() {
        LocalDate now = LocalDate.now();
        return endDate.isAfter(now) && endDate.isBefore(now.plusDays(3)) && isActive();
    }

    public boolean isEndingWithinOneDay() {
        LocalDate now = LocalDate.now();
        return endDate.isAfter(now) && !endDate.isAfter(now.plusDays(1)) && isActive();
    }

    public double calculateTotalCost() {
        long days = getDurationInDays();
        double baseCost = days * dailyRate;
        double insuranceCost = includesInsurance ? days * (dailyRate * 0.1) : 0.0; // 10% of daily rate for insurance
        return baseCost + insuranceCost;
    }

    public void extendPeriod(int additionalDays) {
        if (endDate != null) {
            this.endDate = endDate.plusDays(additionalDays);
            this.status = "EXTENDED";
            this.totalCost = calculateTotalCost();
        }
    }

    public void extendPeriod(LocalDate newEndDate) {
        if (newEndDate.isAfter(this.endDate)) {
            this.endDate = newEndDate;
            this.status = "EXTENDED";
            this.totalCost = calculateTotalCost();
        }
    }

    public double getRemainingCost() {
        if (!isActive()) return 0.0;

        LocalDate today = LocalDate.now();
        if (today.isBefore(startDate)) {
            return totalCost; // Full period remaining
        } else if (today.isAfter(endDate)) {
            return 0.0; // Period ended
        } else {
            long remainingDays = ChronoUnit.DAYS.between(today, endDate) + 1;
            return remainingDays * (dailyRate + (includesInsurance ? dailyRate * 0.1 : 0.0));
        }
    }

    public void completePeriod() {
        this.status = "COMPLETED";
    }

    public void cancelPeriod() {
        this.status = "CANCELLED";
        // Could add refund calculation logic here
    }

    // Static factory methods
    public static RentalPeriod createBasicRental(int rentalId, LocalDate startDate, LocalDate endDate, double dailyRate) {
        return new RentalPeriod(0, rentalId, startDate, endDate, dailyRate, false);
    }

    public static RentalPeriod createInsuredRental(int rentalId, LocalDate startDate, LocalDate endDate, double dailyRate) {
        return new RentalPeriod(0, rentalId, startDate, endDate, dailyRate, true);
    }

    @Override
    public String toString() {
        return "RentalPeriod{" +
                "periodId=" + periodId +
                ", rentalId=" + rentalId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + getDurationInDays() + " days" +
                ", status='" + status + '\'' +
                ", dailyRate=" + dailyRate +
                ", totalCost=" + totalCost +
                ", includesInsurance=" + includesInsurance +
                '}';
    }
}
