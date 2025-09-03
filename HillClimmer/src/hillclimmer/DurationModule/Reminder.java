/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.DurationModule;

import java.time.LocalDateTime;

/**
 * Reminder class for managing various types of reminders in the rental system
 * @author las
 */
public class Reminder {
    private int reminderId;
    private String reminderType; // "RETURN", "MAINTENANCE", "PAYMENT", "INSURANCE"
    private String message;
    private LocalDateTime dueDate;
    private LocalDateTime createdDate;
    private String status; // "PENDING", "SENT", "COMPLETED", "OVERDUE"
    private int associatedId; // ID of associated rental, vehicle, or customer
    private String priority; // "LOW", "MEDIUM", "HIGH", "URGENT"

    // Default constructor
    public Reminder() {
        this.createdDate = LocalDateTime.now();
        this.status = "PENDING";
        this.priority = "MEDIUM";
    }

    // Parameterized constructor
    public Reminder(int reminderId, String reminderType, String message,
                   LocalDateTime dueDate, int associatedId, String priority) {
        this();
        this.reminderId = reminderId;
        this.reminderType = reminderType;
        this.message = message;
        this.dueDate = dueDate;
        this.associatedId = associatedId;
        this.priority = priority;
    }

    // Getters
    public int getReminderId() {
        return reminderId;
    }

    public String getReminderType() {
        return reminderType;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public String getStatus() {
        return status;
    }

    public int getAssociatedId() {
        return associatedId;
    }

    public String getPriority() {
        return priority;
    }

    // Setters
    public void setReminderId(int reminderId) {
        this.reminderId = reminderId;
    }

    public void setReminderType(String reminderType) {
        this.reminderType = reminderType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAssociatedId(int associatedId) {
        this.associatedId = associatedId;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    // Business methods
    public boolean isOverdue() {
        return LocalDateTime.now().isAfter(dueDate) && !"COMPLETED".equals(status);
    }

    public boolean isDueSoon() {
        LocalDateTime now = LocalDateTime.now();
        return dueDate.isAfter(now) && dueDate.isBefore(now.plusHours(24));
    }

    public void markAsSent() {
        this.status = "SENT";
    }

    public void markAsCompleted() {
        this.status = "COMPLETED";
    }

    public long getHoursUntilDue() {
        return java.time.Duration.between(LocalDateTime.now(), dueDate).toHours();
    }

    public long getDaysUntilDue() {
        return java.time.Duration.between(LocalDateTime.now(), dueDate).toDays();
    }

    // Static factory methods for common reminder types
    public static Reminder createReturnReminder(int rentalId, LocalDateTime returnDate) {
        return new Reminder(0, "RETURN",
            "Vehicle return due on " + returnDate.toLocalDate(),
            returnDate, rentalId, "HIGH");
    }

    public static Reminder createMaintenanceReminder(int vehicleId, LocalDateTime maintenanceDate, String maintenanceType) {
        return new Reminder(0, "MAINTENANCE",
            maintenanceType + " maintenance due on " + maintenanceDate.toLocalDate(),
            maintenanceDate, vehicleId, "MEDIUM");
    }

    public static Reminder createPaymentReminder(int customerId, LocalDateTime paymentDate, double amount) {
        return new Reminder(0, "PAYMENT",
            "Payment of $" + amount + " due on " + paymentDate.toLocalDate(),
            paymentDate, customerId, "HIGH");
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "reminderId=" + reminderId +
                ", type='" + reminderType + '\'' +
                ", message='" + message + '\'' +
                ", dueDate=" + dueDate +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", associatedId=" + associatedId +
                '}';
    }
}
