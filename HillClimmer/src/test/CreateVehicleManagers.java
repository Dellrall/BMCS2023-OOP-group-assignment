/*
 * Vehicle Manager Creation Script
 * Creates vehicle managers with project member names and authorization levels
 */
package test;

import hillclimmer.VehicleModule.VehicleManager;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Script to create vehicle managers for testing
 * @author las
 */
public class CreateVehicleManagers {
    public static void main(String[] args) {
        System.out.println("👨‍💼 Creating Vehicle Managers (Project Members)");
        System.out.println("==============================================");

        // Project member names from PDF filename
        String[][] managers = {
            {"VM002", "Chin Wen Wei", "Manager123!", "1"},           // Highest level
            {"VM003", "Lye Wei Lun", "SecurePass456!", "5"},         // Level 5 (Promoted)
            {"VM004", "Neeshwran A/L Veera Chelvan", "Nurburg2025!", "3"}, // Level 3
            {"VM005", "Oscar Lim Zheng You", "OscarRacing!", "4"},   // Level 4
            {"VM006", "Teh Guan Chen", "TehSecure789!", "5"}         // Level 5
        };

        // Create managers.csv file
        try (FileWriter writer = new FileWriter("data/managers.csv", true)) {
            for (String[] manager : managers) {
                String id = manager[0];
                String name = manager[1];
                String password = manager[2];
                int level = Integer.parseInt(manager[3]);

                // Hash the password for security
                String salt = generateSalt();
                String hashedPassword = hashPassword(password, salt);

                // CSV format: managerID,name,hashedPassword,salt,authorizationLevel
                writer.write(id + "," + name + "," + hashedPassword + "," + salt + "," + level + "\n");

                System.out.println("✅ Created Manager: " + name);
                System.out.println("   ID: " + id + ", Password: " + password + ", Level: " + level);
                System.out.println("   Hashed: " + hashedPassword.substring(0, 20) + "...");
                System.out.println();
            }

            System.out.println("✅ All vehicle managers created successfully!");
            System.out.println("📄 Managers saved to data/managers.csv");

        } catch (IOException e) {
            System.err.println("❌ Error creating managers file: " + e.getMessage());
        }
    }

    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
