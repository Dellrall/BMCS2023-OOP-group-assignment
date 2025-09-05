/*
 * SafetyCheckDAO class extending DataAccessObject for SafetyCheck data management.
 *
 * @author las
 */
package hillclimmer.DatabaseModule;

import hillclimmer.CustomerModule.SafetyCheck;

/**
 * SafetyCheckDAO class extending DataAccessObject for SafetyCheck data management.
 *
 * @author las
 */
public class SafetyCheckDAO extends DataAccessObject<SafetyCheck> {

    public SafetyCheckDAO() {
        super("data/safetychecks.csv");
    }

    @Override
    protected String objectToCSV(SafetyCheck safetyCheck) {
        return safetyCheck.getQuizID() + "," +
               safetyCheck.getMinPassScore();
    }

    @Override
    protected SafetyCheck csvToObject(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length >= 2) {
            String quizID = parts[0];
            int minPassScore = Integer.parseInt(parts[1]);

            return new SafetyCheck(quizID, minPassScore);
        }
        return null;
    }

    @Override
    protected String getId(SafetyCheck safetyCheck) {
        return safetyCheck.getQuizID();
    }
}
