/*
 * Manager class for vehicle managers with authentication and authorization
 */
package hillclimmer.DatabaseModule;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.regex.Pattern;

/**
 * Manager class for vehicle managers with authentication and authorization levels
 * @author las
 */
public class Manager {
    private String managerID;
    private String name;
    private String hashedPassword;
    private String salt;
    private int authorizationLevel;

    // Password validation patterns
    private static final Pattern HEX_PATTERN = Pattern.compile("[0-9a-fA-F]");
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("[!@#$%^&*()_+=\\-\\[\\]{}|;:,.<>?]");

    /**
     * Constructor for creating a new manager
     */
    public Manager(String managerID, String name, int authorizationLevel) {
        this.managerID = managerID;
        this.name = name;
        this.authorizationLevel = authorizationLevel;
    }

    /**
     * Generates a random salt for password hashing
     */
    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 128-bit salt
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hashes a password with the provided salt using SHA-256
     */
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

    /**
     * Sets and hashes a new password with a new salt
     */
    public void setPassword(String plainPassword) {
        if (!isValidPassword(plainPassword)) {
            throw new IllegalArgumentException("Password must be at least 6 characters long and contain both hexadecimal (0-9, a-f, A-F) and symbol (!@#$%^&*() etc.) characters");
        }
        this.salt = generateSalt();
        this.hashedPassword = hashPassword(plainPassword, this.salt);
    }

    /**
     * Validates password requirements: at least 6 characters, contains hex and symbol characters
     * @param password Password to validate
     * @return true if password meets requirements
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }

        boolean hasHex = HEX_PATTERN.matcher(password).find();
        boolean hasSymbol = SYMBOL_PATTERN.matcher(password).find();

        return hasHex && hasSymbol;
    }

    /**
     * Updates password with new hashing and salt (allows changing to same password)
     */
    public void updatePassword(String newPassword) {
        if (!isValidPassword(newPassword)) {
            throw new IllegalArgumentException("Password must be at least 6 characters long and contain both hexadecimal (0-9, a-f, A-F) and symbol (!@#$%^&*() etc.) characters");
        }
        // Always generate new salt for security, even for same password
        this.salt = generateSalt();
        this.hashedPassword = hashPassword(newPassword, this.salt);
    }

    /**
     * Authenticates a password against the stored hash
     */
    public boolean authenticatePassword(String inputPassword) {
        if (this.hashedPassword == null || this.salt == null) {
            return false;
        }

        String inputHash = hashPassword(inputPassword, this.salt);
        return this.hashedPassword.equals(inputHash);
    }

    // Getters and setters
    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getAuthorizationLevel() {
        return authorizationLevel;
    }

    public void setAuthorizationLevel(int authorizationLevel) {
        this.authorizationLevel = authorizationLevel;
    }

    /**
     * Check if manager has permission for a specific action
     */
    public boolean hasPermission(String action) {
        switch (action.toLowerCase()) {
            case "view":
                return authorizationLevel >= 1;
            case "add":
            case "remove":
                return authorizationLevel >= 2;
            case "update":
            case "modify":
                return authorizationLevel >= 3;
            case "admin":
            case "full":
                return authorizationLevel >= 4;
            case "super":
                return authorizationLevel >= 5;
            default:
                return false;
        }
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerID='" + managerID + '\'' +
                ", name='" + name + '\'' +
                ", authorizationLevel=" + authorizationLevel +
                '}';
    }
}
