import java.time.LocalDate;

public class DateTest {
    public static void main(String[] args) {
        try {
            LocalDate date = LocalDate.of(2025, 99, 56);
            System.out.println("Date created: " + date);
        } catch (Exception e) {
            System.out.println("Exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
        }
    }
}
