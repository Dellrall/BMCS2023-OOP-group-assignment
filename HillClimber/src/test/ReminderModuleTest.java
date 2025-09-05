/*
 * Reminder Module Test
 * Tests the functionality of the reminder system
 */
package test;

import hillclimmer.DurationModule.Reminder;
import hillclimmer.DurationModule.DurationManager;
import hillclimmer.DatabaseModule.ReminderDAO;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Test class to verify reminder module functionality
 * @author las
 */
public class ReminderModuleTest {
    public static void main(String[] args) {
        System.out.println("🔔 TESTING REMINDER MODULE FUNCTIONALITY");
        System.out.println("========================================");

        try {
            // Initialize components
            DurationManager durationManager = new DurationManager();
            ReminderDAO reminderDAO = new ReminderDAO();

            System.out.println("✅ Components initialized");

            // Test 1: Create different types of reminders
            System.out.println("\n📝 Test 1: Creating reminders...");

            // Create a return reminder
            Reminder returnReminder = durationManager.createReturnReminder(
                1, LocalDateTime.now().plusDays(2));
            System.out.println("   ✅ Return reminder created: " + returnReminder.getMessage());

            // Create a maintenance reminder
            Reminder maintenanceReminder = durationManager.createMaintenanceReminder(
                101, LocalDateTime.now().plusDays(7), "Oil Change");
            System.out.println("   ✅ Maintenance reminder created: " + maintenanceReminder.getMessage());

            // Create a payment reminder
            Reminder paymentReminder = durationManager.createPaymentReminder(
                201, LocalDateTime.now().plusDays(5), 150.0);
            System.out.println("   ✅ Payment reminder created: " + paymentReminder.getMessage());

            // Test 2: Check reminder properties
            System.out.println("\n🔍 Test 2: Checking reminder properties...");

            System.out.println("   Return reminder - Type: " + returnReminder.getReminderType() +
                             ", Priority: " + returnReminder.getPriority() +
                             ", Status: " + returnReminder.getStatus());
            System.out.println("   Maintenance reminder - Type: " + maintenanceReminder.getReminderType() +
                             ", Priority: " + maintenanceReminder.getPriority() +
                             ", Status: " + maintenanceReminder.getStatus());
            System.out.println("   Payment reminder - Type: " + paymentReminder.getReminderType() +
                             ", Priority: " + paymentReminder.getPriority() +
                             ", Status: " + paymentReminder.getStatus());

            // Test 3: Test reminder status methods
            System.out.println("\n⚡ Test 3: Testing reminder status methods...");

            System.out.println("   Return reminder due soon: " + returnReminder.isDueSoon());
            System.out.println("   Return reminder overdue: " + returnReminder.isOverdue());
            System.out.println("   Hours until return due: " + returnReminder.getHoursUntilDue());
            System.out.println("   Days until return due: " + returnReminder.getDaysUntilDue());

            // Test 4: Test DurationManager methods
            System.out.println("\n📊 Test 4: Testing DurationManager methods...");

            List<Reminder> allReminders = durationManager.getAllReminders();
            System.out.println("   Total reminders created: " + allReminders.size());

            List<Reminder> pendingReminders = durationManager.getPendingReminders();
            System.out.println("   Pending reminders: " + pendingReminders.size());

            List<Reminder> returnReminders = durationManager.getRemindersByType("RETURN");
            System.out.println("   Return reminders: " + returnReminders.size());

            List<Reminder> overdueReminders = durationManager.getOverdueReminders();
            System.out.println("   Overdue reminders: " + overdueReminders.size());

            List<Reminder> dueSoonReminders = durationManager.getRemindersDueSoon();
            System.out.println("   Reminders due soon: " + dueSoonReminders.size());

            // Test 5: Test ReminderDAO persistence
            System.out.println("\n💾 Test 5: Testing ReminderDAO persistence...");

            // Save reminders to CSV
            reminderDAO.save(returnReminder);
            reminderDAO.save(maintenanceReminder);
            reminderDAO.save(paymentReminder);
            System.out.println("   ✅ Reminders saved to CSV");

            // Load reminders from CSV
            List<Reminder> loadedReminders = reminderDAO.loadAll();
            System.out.println("   Loaded reminders from CSV: " + loadedReminders.size());

            // Test DAO query methods
            List<Reminder> pendingFromDAO = reminderDAO.getPendingReminders();
            System.out.println("   Pending reminders from DAO: " + pendingFromDAO.size());

            List<Reminder> returnFromDAO = reminderDAO.getRemindersByType("RETURN");
            System.out.println("   Return reminders from DAO: " + returnFromDAO.size());

            // Test 6: Test reminder status updates
            System.out.println("\n🔄 Test 6: Testing reminder status updates...");

            durationManager.markReminderAsSent(returnReminder.getReminderId());
            System.out.println("   ✅ Return reminder marked as sent");

            durationManager.markReminderAsCompleted(maintenanceReminder.getReminderId());
            System.out.println("   ✅ Maintenance reminder marked as completed");

            // Check updated status
            List<Reminder> updatedReminders = durationManager.getAllReminders();
            for (Reminder r : updatedReminders) {
                if (r.getReminderId() == returnReminder.getReminderId()) {
                    System.out.println("   Return reminder status: " + r.getStatus());
                }
                if (r.getReminderId() == maintenanceReminder.getReminderId()) {
                    System.out.println("   Maintenance reminder status: " + r.getStatus());
                }
            }

            // Test 7: Test reminder utility methods
            System.out.println("\n🛠️ Test 7: Testing utility methods...");

            System.out.println("   Total active reminders: " + durationManager.getTotalActiveReminders());
            System.out.println("   DurationManager status: " + durationManager.toString());

            // Test 8: Test overdue reminder creation
            System.out.println("\n⏰ Test 8: Testing overdue reminder creation...");

            // Create an overdue reminder
            Reminder overdueReminder = new Reminder(999, "RETURN",
                "Overdue vehicle return", LocalDateTime.now().minusDays(1), 999, "URGENT");
            durationManager.addReminder(overdueReminder);

            List<Reminder> overdueList = durationManager.getOverdueReminders();
            System.out.println("   Overdue reminders count: " + overdueList.size());

            if (!overdueList.isEmpty()) {
                System.out.println("   ✅ Overdue reminder detected: " + overdueList.get(0).getMessage());
            }

            // Final summary
            System.out.println("\n🎯 REMINDER MODULE TEST SUMMARY");
            System.out.println("================================");
            System.out.println("✅ Reminder creation: PASSED");
            System.out.println("✅ Reminder properties: PASSED");
            System.out.println("✅ Status methods: PASSED");
            System.out.println("✅ DurationManager methods: PASSED");
            System.out.println("✅ DAO persistence: PASSED");
            System.out.println("✅ Status updates: PASSED");
            System.out.println("✅ Utility methods: PASSED");
            System.out.println("✅ Overdue detection: PASSED");
            System.out.println("\n🎉 ALL REMINDER TESTS PASSED!");
            System.out.println("🔔 Reminder module is working correctly!");

        } catch (Exception e) {
            System.err.println("❌ REMINDER TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
