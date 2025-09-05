/*
 * Export User Credentials Script
 * Creates a comprehensive text file with all user credentials
 */
package test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Script to export all user credentials to a text file
 * @author las
 */
public class ExportCredentials {
    public static void main(String[] args) {
        String homeDir = System.getProperty("user.home");
        String downloadsPath = homeDir + "/Downloads";
        String outputFile = downloadsPath + "/HillClimmer_User_Credentials.txt";

        // Create Downloads directory if it doesn't exist
        try {
            Path downloadsDir = Paths.get(downloadsPath);
            if (!Files.exists(downloadsDir)) {
                Files.createDirectories(downloadsDir);
                System.out.println("Created Downloads directory: " + downloadsPath);
            }
        } catch (IOException e) {
            System.err.println("Error creating Downloads directory: " + e.getMessage());
            return;
        }

        // Export credentials
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("=========================================\n");
            writer.write("   🏔️  HillClimmer VEHICLE RENTAL   🏔️\n");
            writer.write("        User Credentials Export\n");
            writer.write("=========================================\n\n");

            writer.write("📅 Export Date: September 5, 2025\n");
            writer.write("🔐 Security: All passwords are hashed with unique salts\n\n");

            // Customer Users
            writer.write("👥 CUSTOMER USERS (Top Gear Personalities)\n");
            writer.write("==========================================\n\n");

            String[][] customers = {
                {"C001", "Muhammad Ali", "password123", "Original sample user"},
                {"C002", "Jeremy Clarkson", "TopGear2025!", "Top Gear Presenter"},
                {"C003", "Richard Hammond", "HammondRacing!", "Top Gear Enthusiast"},
                {"C004", "James May", "CaptainSlow!", "Top Gear Engineer"},
                {"C005", "Sabine Schmitz", "QueenOfNurburgring!", "Top Gear Racing Driver"},
                {"C006", "Chris Evans", "TopGearHost!", "Top Gear Host"}
            };

            for (String[] customer : customers) {
                writer.write("Username: " + customer[0] + "\n");
                writer.write("Name: " + customer[1] + "\n");
                writer.write("Password: " + customer[2] + "\n");
                writer.write("Notes: " + customer[3] + "\n");
                writer.write("─".repeat(40) + "\n\n");
            }

            // Vehicle Manager Users
            writer.write("👨‍💼 VEHICLE MANAGER USERS (Project Members)\n");
            writer.write("==========================================\n\n");

            String[][] managers = {
                {"VM002", "Chin Wen Wei", "Manager123!", "Level 1 (Highest)"},
                {"VM003", "Lye Wei Lun", "SecurePass456!", "Level 2"},
                {"VM004", "Neeshwran A/L Veera Chelvan", "Nurburg2025!", "Level 3"},
                {"VM005", "Oscar Lim Zheng You", "OscarRacing!", "Level 4"},
                {"VM006", "Teh Guan Chen", "TehSecure789!", "Level 5"}
            };

            for (String[] manager : managers) {
                writer.write("Manager ID: " + manager[0] + "\n");
                writer.write("Name: " + manager[1] + "\n");
                writer.write("Password: " + manager[2] + "\n");
                writer.write("Authorization Level: " + manager[3] + "\n");
                writer.write("─".repeat(40) + "\n\n");
            }

            // Security Information
            writer.write("🔒 SECURITY INFORMATION\n");
            writer.write("======================\n\n");
            writer.write("• All customer passwords are hashed using SHA-256\n");
            writer.write("• Each user has a unique 128-bit salt\n");
            writer.write("• Passwords are never stored in plain text\n");
            writer.write("• Vehicle manager passwords are also hashed\n");
            writer.write("• Authorization levels determine access permissions\n");
            writer.write("• Level 1 = Highest access, Level 5 = Lowest access\n\n");

            // Usage Instructions
            writer.write("📋 USAGE INSTRUCTIONS\n");
            writer.write("=====================\n\n");
            writer.write("1. Start the HillClimmer application\n");
            writer.write("2. Choose 'Customer Login' or 'Vehicle Manager Login'\n");
            writer.write("3. Enter the username/ID and password from above\n");
            writer.write("4. Enjoy testing the system!\n\n");

            writer.write("=========================================\n");
            writer.write("   End of Credentials Export\n");
            writer.write("=========================================\n");

            System.out.println("✅ User credentials exported successfully!");
            System.out.println("📄 File saved to: " + outputFile);
            System.out.println("📊 Total Users: " + (customers.length + managers.length));

        } catch (IOException e) {
            System.err.println("❌ Error exporting credentials: " + e.getMessage());
        }
    }
}
