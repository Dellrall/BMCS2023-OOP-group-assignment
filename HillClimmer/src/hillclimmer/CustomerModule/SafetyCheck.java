/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hillclimmer.CustomerModule;

/**
 *
 * @author las
 */
public class SafetyCheck {
    private String quizID;
    private int minPassScore;
    
    public SafetyCheck() {
    }
    
    public SafetyCheck(String quizID, int minPassScore) {
        this.quizID = quizID;
        this.minPassScore = minPassScore;
    }
    
    // Getters
    public String getQuizID() {
        return quizID;
    }
    
    public int getMinPassScore() {
        return minPassScore;
    }
    
    // Setters
    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }
    
    public void setMinPassScore(int minPassScore) {
        this.minPassScore = minPassScore;
    }
    
    @Override
    public String toString() {
        return "SafetyCheck{" +
                "quizID='" + quizID + '\'' +
                ", minPassScore=" + minPassScore +
                '}';
    }
}
