/*
 * ReminderDAO class extending DataAccessObject for Reminder data management.
 */
package hillclimmer.DatabaseModule;

import hillclimmer.DurationModule.Reminder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ReminderDAO class extending DataAccessObject for Reminder data management.
 * @author las
 */
public class ReminderDAO extends DataAccessObject<Reminder> {

    public ReminderDAO() {
        super("data/reminders.csv");
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
    protected String getId(Reminder reminder) {
        return String.valueOf(reminder.getReminderId());
    }

    @Override
    protected Reminder csvToObject(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 8) {
            int reminderId = Integer.parseInt(parts[0]);
            String reminderType = parts[1];
            String message = parts[2].replace(";", ",");
            LocalDateTime dueDate = LocalDateTime.parse(parts[3]);
            // createdDate is set automatically in Reminder constructor
            String status = parts[5];
            int associatedId = Integer.parseInt(parts[6]);
            String priority = parts[7];

            Reminder reminder = new Reminder(reminderId, reminderType, message, dueDate, associatedId, priority);
            reminder.setStatus(status);
            return reminder;
        }
        return null;
    }

    // Additional methods specific to ReminderDAO
    public List<Reminder> getPendingReminders() {
        return loadAll().stream()
                .filter(reminder -> "PENDING".equals(reminder.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Reminder> getRemindersByType(String type) {
        return loadAll().stream()
                .filter(reminder -> type.equals(reminder.getReminderType()))
                .collect(Collectors.toList());
    }

    public List<Reminder> getRemindersByPriority(String priority) {
        return loadAll().stream()
                .filter(reminder -> priority.equals(reminder.getPriority()))
                .collect(Collectors.toList());
    }
}
