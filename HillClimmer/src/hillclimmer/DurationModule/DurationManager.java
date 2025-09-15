/*
 * DurationManager class for managing reminders and rental periods
 */
package hillclimmer.DurationModule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DurationManager class for coordinating reminder and rental period operations
 * @author las
 */
public class DurationManager {
    private List<Reminder> reminders;
    private List<RentalPeriod> rentalPeriods;
    private int nextReminderId;
    private int nextPeriodId;
    private final Object reminderLock = new Object();
    private final Object periodLock = new Object();

    public DurationManager() {
        this.reminders = new ArrayList<>();
        this.rentalPeriods = new ArrayList<>();
        this.nextReminderId = 1;
        this.nextPeriodId = 1;
    }

    // Reminder management methods
    public void addReminder(Reminder reminder) {
        synchronized (reminderLock) {
            reminder.setReminderId(nextReminderId++);
            reminders.add(reminder);
        }
    }

    public Reminder createReturnReminder(int rentalId, LocalDateTime returnDate) {
        Reminder reminder = Reminder.createReturnReminder(rentalId, returnDate);
        addReminder(reminder);
        return reminder;
    }

    public Reminder createMaintenanceReminder(int vehicleId, LocalDateTime maintenanceDate, String maintenanceType) {
        Reminder reminder = Reminder.createMaintenanceReminder(vehicleId, maintenanceDate, maintenanceType);
        addReminder(reminder);
        return reminder;
    }

    public Reminder createPaymentReminder(int customerId, LocalDateTime paymentDate, double amount) {
        Reminder reminder = Reminder.createPaymentReminder(customerId, paymentDate, amount);
        addReminder(reminder);
        return reminder;
    }

    public List<Reminder> getAllReminders() {
        synchronized (reminderLock) {
            return new ArrayList<>(reminders);
        }
    }

    public List<Reminder> getPendingReminders() {
        synchronized (reminderLock) {
            return reminders.stream()
                    .filter(r -> "PENDING".equals(r.getStatus()))
                    .collect(Collectors.toList());
        }
    }

    public void markReminderCompleted(int associatedId, String reminderType) {
        synchronized (reminderLock) {
            for (Reminder reminder : reminders) {
                if (reminder.getAssociatedId() == associatedId && 
                    reminderType.equals(reminder.getReminderType())) {
                    reminder.setStatus("COMPLETED");
                    break;
                }
            }
        }
    }

    public List<Reminder> getOverdueReminders() {
        return reminders.stream()
                .filter(Reminder::isOverdue)
                .collect(Collectors.toList());
    }

    public List<Reminder> getRemindersDueSoon() {
        return reminders.stream()
                .filter(Reminder::isDueSoon)
                .collect(Collectors.toList());
    }

    public List<Reminder> getRemindersByType(String type) {
        return reminders.stream()
                .filter(r -> type.equals(r.getReminderType()))
                .collect(Collectors.toList());
    }

    public void markReminderAsSent(int reminderId) {
        reminders.stream()
                .filter(r -> r.getReminderId() == reminderId)
                .findFirst()
                .ifPresent(Reminder::markAsSent);
    }

    public void markReminderAsCompleted(int reminderId) {
        reminders.stream()
                .filter(r -> r.getReminderId() == reminderId)
                .findFirst()
                .ifPresent(Reminder::markAsCompleted);
    }

    // Rental period management methods
    public void addRentalPeriod(RentalPeriod period) {
        synchronized (periodLock) {
            period.setPeriodId(nextPeriodId++);
            rentalPeriods.add(period);
        }
    }

    public RentalPeriod createBasicRentalPeriod(int rentalId, LocalDate startDate, LocalDate endDate, double dailyRate) {
        RentalPeriod period = RentalPeriod.createBasicRental(rentalId, startDate, endDate, dailyRate);
        addRentalPeriod(period);
        return period;
    }

    public RentalPeriod createInsuredRentalPeriod(int rentalId, LocalDate startDate, LocalDate endDate, double dailyRate) {
        RentalPeriod period = RentalPeriod.createInsuredRental(rentalId, startDate, endDate, dailyRate);
        addRentalPeriod(period);
        return period;
    }

    public List<RentalPeriod> getAllRentalPeriods() {
        synchronized (periodLock) {
            return new ArrayList<>(rentalPeriods);
        }
    }

    public List<RentalPeriod> getActiveRentalPeriods() {
        return rentalPeriods.stream()
                .filter(RentalPeriod::isActive)
                .collect(Collectors.toList());
    }

    public List<RentalPeriod> getOverdueRentalPeriods() {
        return rentalPeriods.stream()
                .filter(RentalPeriod::isOverdue)
                .collect(Collectors.toList());
    }

    public List<RentalPeriod> getRentalPeriodsEndingSoon() {
        return rentalPeriods.stream()
                .filter(RentalPeriod::isEndingSoon)
                .collect(Collectors.toList());
    }

    public RentalPeriod getRentalPeriodById(int periodId) {
        return rentalPeriods.stream()
                .filter(p -> p.getPeriodId() == periodId)
                .findFirst()
                .orElse(null);
    }

    public List<RentalPeriod> getRentalPeriodsByRentalId(int rentalId) {
        return rentalPeriods.stream()
                .filter(p -> p.getRentalId() == rentalId)
                .collect(Collectors.toList());
    }

    public void extendRentalPeriod(int periodId, int additionalDays) {
        rentalPeriods.stream()
                .filter(p -> p.getPeriodId() == periodId)
                .findFirst()
                .ifPresent(p -> p.extendPeriod(additionalDays));
    }

    public void completeRentalPeriod(int periodId) {
        rentalPeriods.stream()
                .filter(p -> p.getPeriodId() == periodId)
                .findFirst()
                .ifPresent(RentalPeriod::completePeriod);
    }

    public void cancelRentalPeriod(int periodId) {
        rentalPeriods.stream()
                .filter(p -> p.getPeriodId() == periodId)
                .findFirst()
                .ifPresent(RentalPeriod::cancelPeriod);
    }

    // Utility methods
    public int getTotalActiveReminders() {
        return (int) reminders.stream()
                .filter(r -> "PENDING".equals(r.getStatus()))
                .count();
    }

    public int getTotalActiveRentalPeriods() {
        return (int) rentalPeriods.stream()
                .filter(RentalPeriod::isActive)
                .count();
    }

    public double getTotalRevenueFromActivePeriods() {
        synchronized (periodLock) {
            return rentalPeriods.stream()
                    .filter(RentalPeriod::isActive)
                    .mapToDouble(RentalPeriod::getTotalCost)
                    .sum();
        }
    }

    public void generateReturnRemindersForActivePeriods() {
        rentalPeriods.stream()
                .filter(RentalPeriod::isActive)
                .filter(RentalPeriod::isEndingSoon)
                .forEach(period -> {
                    LocalDateTime returnDate = period.getEndDate().atStartOfDay();
                    createReturnReminder(period.getRentalId(), returnDate);
                });
    }

    @Override
    public String toString() {
        return "DurationManager{" +
                "totalReminders=" + reminders.size() +
                ", activeReminders=" + getTotalActiveReminders() +
                ", totalRentalPeriods=" + rentalPeriods.size() +
                ", activeRentalPeriods=" + getTotalActiveRentalPeriods() +
                ", totalRevenue=" + getTotalRevenueFromActivePeriods() +
                '}';
    }
}
