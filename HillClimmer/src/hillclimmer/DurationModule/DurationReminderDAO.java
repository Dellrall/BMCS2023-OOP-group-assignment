/*
 * ReminderDAO class extending DataAccessObject for Reminder data management.
 */
package hillclimmer.DurationModule;

import hillclimmer.DatabaseModule.DataAccessObject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DurationReminderDAO class extending DataAccessObject for Reminder data management.
 * @author las
 */
public class DurationReminderDAO extends DataAccessObject<Reminder> {

    public DurationReminderDAO() {
        super("reminders.csv");
    }

    @Override
    protected String objectToCSV(Reminder reminder) {
        return reminder.getReminderId() + "," +
               reminder.getReminderType() + "," +
               reminder.getMessage().replace(",", ";") + "," +
               reminder.getDueDate() + "," +
               reminder.getCreatedDate() + "," +
               reminder.getStatus() + "," +
               reminder.getAssociatedId() + "," +
               reminder.getPriority();
    }

    @Override
    protected Reminder csvToObject(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 8) {
            int reminderId = Integer.parseInt(parts[0]);
            String reminderType = parts[1];
            String message = parts[2].replace(";", ",");
            LocalDateTime dueDate = LocalDateTime.parse(parts[3]);
            // Note: createdDate is set automatically in Reminder constructor
            String status = parts[5];
            int associatedId = Integer.parseInt(parts[6]);
            String priority = parts[7];

            Reminder reminder = new Reminder(reminderId, reminderType, message, dueDate, associatedId, priority);
            reminder.setStatus(status);
            // Note: createdDate is set automatically in Reminder constructor
            return reminder;
        }
        return null;
    }

    @Override
    protected String getId(Reminder reminder) {
        return String.valueOf(reminder.getReminderId());
    }

    // Additional query methods
    public List<Reminder> getAll() {
        return loadAll();
    }
    public List<Reminder> getRemindersByType(String type) {
        return loadAll().stream()
                .filter(r -> type.equals(r.getReminderType()))
                .collect(Collectors.toList());
    }

    public List<Reminder> getPendingReminders() {
        return loadAll().stream()
                .filter(r -> "PENDING".equals(r.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Reminder> getOverdueReminders() {
        return loadAll().stream()
                .filter(Reminder::isOverdue)
                .collect(Collectors.toList());
    }

    public List<Reminder> getRemindersByAssociatedId(int associatedId) {
        return loadAll().stream()
                .filter(r -> r.getAssociatedId() == associatedId)
                .collect(Collectors.toList());
    }
}
