/*
 * ManagerDAO class for managing vehicle manager data
 */
package hillclimmer.DatabaseModule;

import java.util.List;

/**
 * ManagerDAO class extending DataAccessObject for Manager data management
 * @author las
 */
public class ManagerDAO extends DataAccessObject<Manager> {

    public ManagerDAO() {
        super("data/managers.csv");
    }

    @Override
    protected String objectToCSV(Manager manager) {
        return manager.getManagerID() + "," +
               manager.getName() + "," +
               "\"" + (manager.getHashedPassword() != null ? manager.getHashedPassword() : "") + "\"" + "," +
               "\"" + (manager.getSalt() != null ? manager.getSalt() : "") + "\"" + "," +
               manager.getAuthorizationLevel();
    }

    @Override
    protected Manager csvToObject(String csvLine) {
        try {
            String[] parts = parseCSVLine(csvLine);
            if (parts.length >= 5) {
                String managerID = parts[0];
                String name = parts[1];
                String hashedPassword = parts[2];
                String salt = parts[3];
                int authorizationLevel = Integer.parseInt(parts[4].trim());

                Manager manager = new Manager(managerID, name, authorizationLevel);
                manager.setHashedPassword(hashedPassword);
                manager.setSalt(salt);

                return manager;
            }
        } catch (Exception e) {
            System.err.println("Error parsing manager CSV line: " + e.getMessage());
        }
        return null;
    }

    /**
     * Parse CSV line handling quoted fields that may contain commas
     */
    private String[] parseCSVLine(String line) {
        java.util.List<String> parts = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                parts.add(current.toString().trim());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }

        // Add the last part
        parts.add(current.toString().trim());

        return parts.toArray(new String[0]);
    }

    @Override
    protected String getId(Manager manager) {
        return manager.getManagerID();
    }

    // Manager-specific methods
    public List<Manager> getAllManagers() {
        return loadAll();
    }

    public Manager findById(String managerID) {
        return load(managerID);
    }

    public Manager authenticate(String managerID, String password) {
        Manager manager = load(managerID);
        if (manager != null && manager.authenticatePassword(password)) {
            return manager;
        }
        return null;
    }
}
