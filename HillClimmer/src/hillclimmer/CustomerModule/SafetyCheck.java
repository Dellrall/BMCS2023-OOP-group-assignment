/*
 * SafetyCheck class for implementing vehicle rental safety assessment
 * Includes traffic rules, penalties, vehicle usage, and sanity checks
 */
package hillclimmer.CustomerModule;

import java.util.*;

/**
 * SafetyCheck class for conducting comprehensive safety assessments
 * before allowing customers to book vehicles
 *
 * @author las
 */
public class SafetyCheck {
    private String checkID;
    private String customerID;
    private int score;
    private int totalQuestions;
    private boolean passed;
    private Date completedDate;
    private List<String> wrongAnswers;

    // Safety questions database
    private static final String[][] TRAFFIC_QUESTIONS = {
        {"What is the maximum speed limit in Malaysian residential areas?",
         "A) 60 km/h", "B) 50 km/h", "C) 40 km/h", "D) 30 km/h", "B"},
        {"What should you do when approaching a school zone during school hours?",
         "A) Speed up to pass quickly", "B) Maintain normal speed", "C) Slow down and watch for children", "D) Honk to alert children", "C"},
        {"When can you make a U-turn in Malaysia?",
         "A) Anywhere if no oncoming traffic", "B) Only at traffic lights with U-turn signs", "C) On highways", "D) Against one-way traffic", "B"},
        {"What is the minimum age to obtain a Malaysian driving license?",
         "A) 16 years", "B) 17 years", "C) 18 years", "D) 21 years", "C"},
        {"What does a red traffic light mean?",
         "A) Slow down", "B) Stop completely", "C) Proceed with caution", "D) Speed up", "B"}
    };

    private static final String[][] PENALTY_QUESTIONS = {
        {"What is the penalty for driving without a valid license in Malaysia?",
         "A) Warning only", "B) RM300 fine", "C) RM300- RM1,000 fine + court", "D) Vehicle confiscation", "C"},
        {"What happens if you exceed speed limit by more than 60 km/h?",
         "A) RM300 fine", "B) License suspension + RM1,000 fine", "C) Warning only", "D) Points on license", "B"},
        {"Penalty for using mobile phone while driving?",
         "A) RM300 fine", "B) RM500 fine + 10 points", "C) Warning only", "D) License suspension", "B"},
        {"What is the penalty for drunk driving in Malaysia?",
         "A) RM500 fine", "B) RM5,000- RM20,000 fine + jail", "C) License suspension only", "D) Warning", "B"}
    };

    private static final String[][] USAGE_QUESTIONS = {
        {"Before starting a hill climbing vehicle, what should you check first?",
         "A) Fuel level", "B) Tire pressure and brakes", "C) Radio station", "D) Seat comfort", "B"},
        {"What gear should you use when descending steep hills?",
         "A) High gear", "B) Low gear", "C) Neutral", "D) Reverse", "B"},
        {"When parking uphill, which way should you turn your wheels?",
         "A) Towards the curb", "B) Away from the curb", "C) Straight", "D) Doesn't matter", "B"},
        {"What should you do if your vehicle starts to skid?",
         "A) Slam brakes", "B) Accelerate", "C) Steer into skid + ease off accelerator", "D) Turn sharply", "C"}
    };

    private static final String[][] SANITY_QUESTIONS = {
        {"How many wheels does a standard car have?",
         "A) 2", "B) 3", "C) 4", "D) 6", "C"},
        {"What color is a stop sign?",
         "A) Green", "B) Yellow", "C) Red", "D) Blue", "C"},
        {"Should you drive after drinking alcohol?",
         "A) Yes, if you feel okay", "B) No, never", "C) Only if you're not too drunk", "D) Yes, it's fun", "B"},
        {"What should you do at a green traffic light?",
         "A) Stop", "B) Proceed with caution", "C) Speed up", "D) Wait for other cars", "B"}
    };

    public SafetyCheck(String customerID) {
        this.customerID = customerID;
        this.score = 0;
        this.totalQuestions = 15; // 5 from each category
        this.passed = false;
        this.wrongAnswers = new ArrayList<>();
        this.checkID = "SC" + System.currentTimeMillis();
    }

    /**
     * Conducts the complete safety check assessment
     * @return true if passed, false if failed
     */
    public boolean conductSafetyCheck() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🛡️  VEHICLE RENTAL SAFETY ASSESSMENT");
        System.out.println("=".repeat(60));
        System.out.println("You must pass this safety check before booking any vehicle.");
        System.out.println("Minimum passing score: 80% (" + (int)(totalQuestions * 0.8) + "/" + totalQuestions + " correct)");
        System.out.println("=".repeat(60));

        // Traffic Rules Section
        System.out.println("\n🚦 TRAFFIC RULES SECTION");
        conductQuestionSet(scanner, TRAFFIC_QUESTIONS, "Traffic Rules");

        // Penalties Section
        System.out.println("\n⚖️  PENALTIES & REGULATIONS SECTION");
        conductQuestionSet(scanner, PENALTY_QUESTIONS, "Penalties");

        // Vehicle Usage Section
        System.out.println("\n🚗 VEHICLE USAGE & SAFETY SECTION");
        conductQuestionSet(scanner, USAGE_QUESTIONS, "Vehicle Usage");

        // Sanity Check Section
        System.out.println("\n🧠 BASIC SAFETY AWARENESS SECTION");
        conductQuestionSet(scanner, SANITY_QUESTIONS, "Basic Safety");

        // Calculate results
        this.completedDate = new Date();
        int passingScore = (int)(totalQuestions * 0.8);
        this.passed = score >= passingScore;

        displayResults();

        return passed;
    }

    private void conductQuestionSet(Scanner scanner, String[][] questions, String sectionName) {
        System.out.println("\n--- " + sectionName.toUpperCase() + " ---");

        for (int i = 0; i < Math.min(5, questions.length); i++) {
            System.out.println("\nQuestion " + (i + 1) + ":");
            System.out.println(questions[i][0]);
            for (int j = 1; j <= 4; j++) {
                System.out.println(questions[i][j]);
            }

            System.out.print("Your answer (A/B/C/D): ");
            String answer = scanner.nextLine().toUpperCase().trim();

            if (answer.equals(questions[i][5])) {
                System.out.println("✅ Correct!");
                score++;
            } else {
                System.out.println("❌ Incorrect. Correct answer: " + questions[i][5]);
                wrongAnswers.add(sectionName + " Q" + (i + 1) + ": " + questions[i][0]);
            }
        }
    }

    private void displayResults() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📊 SAFETY CHECK RESULTS");
        System.out.println("=".repeat(60));
        System.out.println("Customer ID: " + customerID);
        System.out.println("Check ID: " + checkID);
        System.out.println("Score: " + score + "/" + totalQuestions);
        System.out.println("Percentage: " + String.format("%.1f", (score * 100.0) / totalQuestions) + "%");

        if (passed) {
            System.out.println("✅ STATUS: PASSED - You can now book vehicles!");
            System.out.println("🎉 Congratulations! You have successfully completed the safety assessment.");
        } else {
            System.out.println("❌ STATUS: FAILED - Safety check must be retaken.");
            System.out.println("📚 Please review the following topics and try again:");
            for (String wrong : wrongAnswers) {
                System.out.println("  • " + wrong);
            }
        }

        System.out.println("Completed: " + completedDate);
        System.out.println("=".repeat(60));
    }

    // Getters
    public String getCheckID() { return checkID; }
    public String getCustomerID() { return customerID; }
    public int getScore() { return score; }
    public int getTotalQuestions() { return totalQuestions; }
    public boolean isPassed() { return passed; }
    public Date getCompletedDate() { return completedDate; }
    public List<String> getWrongAnswers() { return wrongAnswers; }

    // Setters for DAO reconstruction
    public void setCheckID(String checkID) { this.checkID = checkID; }
    public void setScore(int score) { this.score = score; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }
    public void setPassed(boolean passed) { this.passed = passed; }
    public void setCompletedDate(Date completedDate) { this.completedDate = completedDate; }
    public void setWrongAnswers(List<String> wrongAnswers) { this.wrongAnswers = wrongAnswers; }

    /**
     * Quick validation method for existing customers
     */
    public static boolean validateSafetyCheckStatus(boolean safetyCheckPassed) {
        return safetyCheckPassed;
    }
}
