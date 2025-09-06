/*
 * SafetyCheckDAO class extending DataAccessObject for SafetyCheck data management.
 *
 * @author las
 */
package hillclimmer.DatabaseModule;

import hillclimmer.CustomerModule.SafetyCheck;
import java.util.Date;

/**
 * SafetyCheckDAO class extending DataAccessObject for SafetyCheck data management.
 *
 * @author las
 */
public class SafetyCheckDAO extends DataAccessObject<SafetyCheck> {

    public SafetyCheckDAO() {
        super(System.getProperty("user.dir") + "/data/safetychecks.csv");
    }

    @Override
    protected String objectToCSV(SafetyCheck safetyCheck) {
        return safetyCheck.getCheckID() + "," +
               safetyCheck.getCustomerID() + "," +
               safetyCheck.getScore() + "," +
               safetyCheck.getTotalQuestions() + "," +
               safetyCheck.isPassed() + "," +
               (safetyCheck.getCompletedDate() != null ? safetyCheck.getCompletedDate().getTime() : "") + "," +
               String.join(";", safetyCheck.getWrongAnswers());
    }

    @Override
    protected SafetyCheck csvToObject(String csvLine) {
        try {
            String[] parts = csvLine.split(",");
            if (parts.length >= 7) {
                String checkID = parts[0];
                String customerID = parts[1];
                int score = Integer.parseInt(parts[2]);
                int totalQuestions = Integer.parseInt(parts[3]);
                boolean passed = Boolean.parseBoolean(parts[4]);
                Date completedDate = parts[5].isEmpty() ? null : new Date(Long.parseLong(parts[5]));
                String[] wrongAnswersArray = parts[6].split(";");
                java.util.List<String> wrongAnswers = java.util.Arrays.asList(wrongAnswersArray);

                // Create SafetyCheck object and set all properties
                SafetyCheck safetyCheck = new SafetyCheck(customerID);
                safetyCheck.setCheckID(checkID);
                safetyCheck.setScore(score);
                safetyCheck.setTotalQuestions(totalQuestions);
                safetyCheck.setPassed(passed);
                safetyCheck.setCompletedDate(completedDate);
                safetyCheck.setWrongAnswers(wrongAnswers);

                return safetyCheck;
            }
        } catch (Exception e) {
            System.err.println("Error parsing safety check CSV line: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected String getId(SafetyCheck safetyCheck) {
        return safetyCheck.getCheckID();
    }
    
    @Override
    protected SafetyCheck generateNewId(SafetyCheck safetyCheck, java.util.List<SafetyCheck> existingChecks) {
        // Generate new safety check ID based on existing checks
        int maxId = existingChecks.stream()
                .mapToInt(check -> {
                    try {
                        return Integer.parseInt(check.getCheckID().substring(2)); // Remove "SC" prefix
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);
        
        String newCheckId = "SC" + String.format("%03d", maxId + 1);
        
        // Create new safety check with generated ID
        SafetyCheck newCheck = new SafetyCheck(safetyCheck.getCustomerID());
        newCheck.setCheckID(newCheckId);
        newCheck.setScore(safetyCheck.getScore());
        newCheck.setTotalQuestions(safetyCheck.getTotalQuestions());
        newCheck.setPassed(safetyCheck.isPassed());
        newCheck.setCompletedDate(safetyCheck.getCompletedDate());
        newCheck.setWrongAnswers(safetyCheck.getWrongAnswers());
        
        return newCheck;
    }
}
